package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.bouncygame.bricks.Grid;
import com.example.bouncygame.cameras.TranslateCamera;
import com.example.bouncygame.hitboxes.LineHitbox;
import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private static final String CLASS_NAME = "Game";
    private static final int NUM_BLOCKS_IN_WIDTH = 12;
    private static final int NUM_BLOCKS_IN_HEIGHT = 12;
    private static final int GAME_SPACE_Y_START = 120;
    private static final int GRID_Y = 470;

    private static Game instance;//singleton

    private GameView gameView;

    private MainBall mainBall;

    private Grid grid;

    private GameLists gameLists;
    private List<CollisionDetectionFree> checkForOutOfBoundsList;
    private List<GameObject> deleteOnWinList;


    private TranslateCamera camera;

    private Paddle paddle;
    private LaserCannon laserCannon;
    private GameObject[] weapons;
    private int weaponIndex;
    private SwitchButton switchButton;

    private OutOfBoundsInformer outOfBoundsInformer;
    private GameBounds gameBounds;
    private ObjectPercentMover cameraMover;
    private ObjectPercentMover paddleMover;
    private ObjectPercentMover laserCannonMover;
    private ObjectPercentMover outOfBoundsInformerMover;
    private ObjectPercentMover gameBoundsMover;


    private ScrollingBackground scrollingBackground;



    //interfaces
    private ScoreDisplay scoreDisplay;
    private DepthDisplay depthDisplay;
    private TimerDisplay timerDisplay;
    private LevelDisplay levelDisplay;




    //game coordinates (may be different from screen coordinates because of the camera)
    private float mouseGameX;
    private float mouseGameY;




    public enum GameEvent{MAIN_BALL_LOST,OBJECT_OUT_OF_BOUNDS,WIN,ADD_TO_SCORE,GENERATE_LEVEL,UPDATE_CAMERA, ROW_CLEARED,SWITCH_WEAPON}

    private Timer delayActionTimer;

    private TestImage testImage;

    private Game(GameView gameView){
        this.gameView = gameView;
        this.gameLists = new GameLists();
        this.checkForOutOfBoundsList = new ArrayList<>();
        this.deleteOnWinList = new ArrayList<>();
        delayActionTimer = new Timer(true);//isDaemon: does not prolong life of application. this is just for maintenance activities
    }



    public static void initilizeInstance(GameView gameView){
        instance = new Game(gameView);
    }

    //call immidiatley after initilizeInstance
    public void initilizeGame(GameView gameView) {
        gameLists.addInterfaceObject(new TopInterfaceBackground((int) getWidth(),GAME_SPACE_Y_START));
        scoreDisplay = new ScoreDisplay();
        gameLists.addInterfaceObject(scoreDisplay);
        depthDisplay = new DepthDisplay((int) getWidth());
        gameLists.addInterfaceObject(depthDisplay);
        timerDisplay = new TimerDisplay(gameView.getFramePeriod(),(int) getWidth());
        gameLists.addInterfaceObject(timerDisplay);
        levelDisplay = new LevelDisplay((int) getWidth());
        gameLists.addInterfaceObject(levelDisplay);
        this.switchButton = new SwitchButton(0,0,100,GAME_SPACE_Y_START);
        add(switchButton);

        this.grid = makeGrid(0);
        add(grid);

        this.camera = new TranslateCamera(0f,0f,getWidth(),getHeight());
        this.paddle = new Paddle(gameView.getWidth()/2,GAME_SPACE_Y_START,800,50,30);
        this.laserCannon = new LaserCannon(gameView.getWidth()/2,GAME_SPACE_Y_START+60,200,100,(float) Math.PI/2);
        this.gameBounds = new GameBounds(0,0,gameView.getWidth(),gameView.getHeight(),false,true,true,true);
        this.outOfBoundsInformer = new OutOfBoundsInformer(0,GAME_SPACE_Y_START,gameView.getWidth(),gameView.getHeight());
        this.cameraMover = new ObjectPercentMover(camera,0.1f,2,false,true);
        this.paddleMover = new ObjectPercentMover(paddle,0.1f,2,false,true);
        this.laserCannonMover = new ObjectPercentMover(laserCannon,0.1f,2,false,true);
        this.outOfBoundsInformerMover = new ObjectPercentMover(outOfBoundsInformer,0.1f,2,false,true);
        this.gameBoundsMover = new ObjectPercentMover(gameBounds,0.1f,2,false,true);

        this.weapons = new GameObject[]{paddle,laserCannon};
        this.weaponIndex = 0;

        add(camera);
        add(paddle);
//        add(laserCannon);
        add(gameBounds);
        add(outOfBoundsInformer);
        add(cameraMover);
        add(paddleMover);
        add(laserCannonMover);
        add(outOfBoundsInformerMover);
        add(gameBoundsMover);

        spawnMainBall();

//        add(new Finger());






        this.scrollingBackground = new ScrollingBackground(camera,(int) getWidth(),(int) getHeight());
        scrollingBackground.addBackgroundtoGrid(ImageCache.backgroundScrollArray[0],0);
        scrollingBackground.addBackgroundtoGrid(ImageCache.backgroundScrollArray[1],1);
        scrollingBackground.addBackgroundtoGrid(ImageCache.backgroundScrollArray[2],2);
        add(scrollingBackground);

        recieveEvent(Game.GameEvent.UPDATE_CAMERA,null);


        setBoundsForPictures(gameView.getWidth(),gameView.getHeight());
        ImageCache.setDimsBasedOnScreenSize(gameView.getWidth(),gameView.getHeight());

//        add(new DrawOnlyObject(ImageCache.bedrock,DrawManager.FOREGROUND,new RectHitbox(20,559,89,89)));
//        testImage = new TestImage(this);
//        add(testImage);

    }
    //call after initilizeInstance
    public static Game getInstance(){
        return instance;
    }

    public List<CollisionDetectionObject> getAllCollisionObjectList(){
        return gameLists.getAllCollisionObjectList();
    }

    public DrawManager getDrawManager(){
        return gameLists.getDrawManager();
    }

    public Grid getGrid() {
        return grid;
    }

    public void mouseActionDown(float screenX, float screenY) {
        setGameCoordinates(screenX,screenY);
    }

    public void mouseActionUp() {

    }

    public void mouseActionMove(float screenX, float screenY) {
        setGameCoordinates(screenX,screenY);
    }

    private void setGameCoordinates(float screenX, float screenY) {
        this.mouseGameX = camera.screenToGameX(screenX);
        this.mouseGameY = camera.screenToGameY(screenY);
    }

    public float getMouseGameX(){
        return mouseGameX;
    }
    public float getMouseGameY(){
        return mouseGameY;
    }

    private void setBoundsForPictures(int width, int height) {
        Item.setWidth((int) grid.getWidthOfBlocks());
        Item.setHeight((int) grid.getHeightOfBlocks());
        ImageCache.diamond.setBounds(new Rect(0,0,Item.getWidth(),Item.getHeight()));
        ImageCache.iron_ingot.setBounds(new Rect(0,0,Item.getWidth(),Item.getHeight()));
    }

    private Grid makeGrid(int level) {
        return new Grid(NUM_BLOCKS_IN_HEIGHT,NUM_BLOCKS_IN_WIDTH,0,GRID_Y,(int) getWidth(),(int) ((getHeight()-GRID_Y))/NUM_BLOCKS_IN_HEIGHT,level);
    }

    private void spawnMainBall(){
        this.mainBall = new MainBall(gameView.getWidth()/2, paddle.getCenterY()+200,20, Math.PI/2);
        add(mainBall);
    }

    public float getWidth(){
        return gameView.getWidth();
    }

    public float getHeight(){
        return gameView.getHeight();
    }

    public List<CollisionDetectionFree> getCheckForOutOfBoundsList() {
        return checkForOutOfBoundsList;
    }

    public void addToCheckForOutOfBoundsList(CollisionDetectionFree freeBody){
        checkForOutOfBoundsList.add(freeBody);
    }
    public void addToDeleteOnWinList(GameObject gameObject){
        this.deleteOnWinList.add(gameObject);
    }

    public void removeFromToCheckForOutOfBoundsList(CollisionDetectionFree freeBody){
        checkForOutOfBoundsList.remove(freeBody);
    }
    public void removeFromDeleteOnWinList(GameObject gameObject){
        this.deleteOnWinList.remove(gameObject);
    }

    //objects are actually added at the end of each game tick
    public void add(GameObject gameObject){
        if(gameObject!=null){
            gameLists.addToAddList(gameObject);
            Log.d(CLASS_NAME,"adding " + gameObject + " to toAddList");
        }
    }
    //objects are actually deleted at the end of each game tick
    public void delete(GameObject gameObject){
        if(gameObject!=null){
            gameLists.addToDeleteList(gameObject);
            Log.d(CLASS_NAME,"adding " + gameObject + " to toDeleteList");
        }

        if(gameObject instanceof CollisionDetectionFree){
            checkForOutOfBoundsList.remove(gameObject);//might not be here, but won't hurt
        }
        deleteOnWinList.remove(gameObject);/////////////o(n)///don't need?   //might not be here, but won't hurt
    }



    public void draw(GameView gameView, Paint paint, Canvas canvas){
        gameLists.getDrawManager().draw(gameView,paint,canvas,camera);
    }

    public void gameTick(InputStatus inputStatus){
        gameLists.gameTick(inputStatus);


        //hack for debugging
//        Random rand = new Random();
//        if(rand.nextDouble()<.07){
//            add(new TextParticle(Float.toString(laserCannon.getDirection()),20,1100,60));
//        }
    }

    public CollisionDetectionObject rayCast(LineHitbox rayCast, RayCastFilter rayCastFilter, double[] collisionCoordinates) {
        return gameLists.rayCast(rayCast,rayCastFilter,collisionCoordinates);
    }




    public void recieveEventWithDelay(final GameEvent event, final Object info,int delay){
        TimerTask timerTask = new TimerTask(){
            @Override
            public void run() {
                recieveEvent(event,info);
            }
        };
        delayActionTimer.schedule(timerTask,delay);
    }

    public void recieveEvent(GameEvent event, Object info){
        switch(event){
            case MAIN_BALL_LOST:

                TimerTask timerTask = new TimerTask(){
                    @Override
                    public void run() {
                        spawnMainBall();
                    }
                };
                delayActionTimer.schedule(timerTask,2000);


                break;
            case WIN:

                levelDisplay.setLevel(levelDisplay.getLevel()+1);
                depthDisplay.setDepth(0);
                timerDisplay.reset();

                for(int i=0;i<deleteOnWinList.size();i++){
                    deleteOnWinList.get(i).delete();//will be deleted later in tick
                }
                deleteOnWinList.clear();

                delete(grid);
                this.grid = makeGrid(levelDisplay.getLevel());
                add(grid);

                //move mainBall to Bottom and set velocity  preserve its damage boosts //////////repeat code from spawn ball
                mainBall.setCenterX(gameView.getWidth()/2);
                mainBall.setCenterY(200);
                mainBall.setXVelocity(0f);
                mainBall.setYVelocity(-20f);





                moveScreenInstantly(0);


                break;
            case ADD_TO_SCORE:
                scoreDisplay.addToScore((Integer) info);
                break;
            case UPDATE_CAMERA:
                float topOfCamera;
                Log.d(CLASS_NAME, "grid.getDeepestRowGenerated(): " + grid.getDeepestRowGenerated());
                Log.d(CLASS_NAME, "grid.getDepthOfBedrock(): " + grid.getDepthOfBedrock());
                Log.d(CLASS_NAME, "grid.getVisibleRows(): " + grid.getVisibleRows());
                if(grid.getDeepestRowGenerated()==grid.getDepthOfBedrock()){//camera is at lowest point in level
                    Log.d(CLASS_NAME, "gridTRUE");
                    topOfCamera = grid.rowToY(grid.getDepthOfBedrock()+1-grid.getVisibleRows())-grid.getTop();
                }else{//camera is showing as much of the grid as it can
                    Log.d(CLASS_NAME, "gridFALSE");
                    topOfCamera = grid.rowToY(grid.getHighestRowWithBricks())-grid.getTop();
                }

                Log.d(CLASS_NAME, "top of game: " + topOfCamera);
                float toatlMoveX = 0;
                float toatlMoveY = 0;
                int totalTicksToMove = 40;
//                add(new ObjectMover(paddle,toatlMoveX,toatlMoveY,totalTicksToMove));
//                add(new ObjectMover(gameBounds,toatlMoveX,toatlMoveY,totalTicksToMove));
//                add(new ObjectMover(outOfBoundsInformer.getHitbox(),toatlMoveX,toatlMoveY,totalTicksToMove));
                moveScreenSmoothly(topOfCamera);

                break;
            case ROW_CLEARED:
                depthDisplay.setDepth(grid.getHighestRowWithBricks());
                recieveEvent(Game.GameEvent.UPDATE_CAMERA,null);
                break;
            case SWITCH_WEAPON:
                weapons[weaponIndex].delete();
                weaponIndex = (weaponIndex+1)%weapons.length;
                add(weapons[weaponIndex]);

                if(weaponIndex==0){
                    switchButton.setColor(Color.rgb(255,0,0));
                }else{
                    switchButton.setColor(Color.rgb(0,0,0));
                }
                break;
            default:
                Log.d(CLASS_NAME, "unknown event!  Info: " + info);
        }
    }



    private void moveScreenSmoothly(float topOfCamera){
        cameraMover.setGoalTop(topOfCamera);
        paddleMover.setGoalTop(topOfCamera+GAME_SPACE_Y_START);
        laserCannonMover.setGoalCenterY(topOfCamera+GAME_SPACE_Y_START+60);
        gameBoundsMover.setGoalTop(topOfCamera-GameBounds.EXTRA_SPACE);
        outOfBoundsInformerMover.setGoalTop(topOfCamera);
    }
    private void moveScreenInstantly(float whereTopOfCameraShouldBe){
        cameraMover.setTopInstantly(whereTopOfCameraShouldBe);
        paddleMover.setTopInstantly(whereTopOfCameraShouldBe+GAME_SPACE_Y_START);
        laserCannonMover.setTopInstantly(whereTopOfCameraShouldBe+GAME_SPACE_Y_START+60);
        gameBoundsMover.setTopInstantly(whereTopOfCameraShouldBe-GameBounds.EXTRA_SPACE);
        outOfBoundsInformerMover.setTopInstantly(whereTopOfCameraShouldBe);
    }

//    private void writeBallsLost(int ballsLost){
//        try {
//            BufferedWriter fos = new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+"File.txt"));
//            fos.write(Integer.toString(ballsLost));
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        try {
////            File root = android.os.Environment.getExternalStorageDirectory();
////            File root = getExternalFilesDir();
////            PrintWriter printWriter = new PrintWriter(root);
////            printWriter.println(Integer.toString(ballsLost));
////            printWriter.close();
////            Log.d(CLASS_NAME, "wrote balls");
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////            Log.d(CLASS_NAME, "can't find path 1");
////        }
//    }
//
//    private int readBallsLost() {
//        return -1;
//    }


}

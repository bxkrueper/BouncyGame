package com.example.bouncygame.bricks;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.bouncygame.game.CollisionDetectionObject;
import com.example.bouncygame.game.CollisionDetectionStatic;
import com.example.bouncygame.game.CollisionMediator;
import com.example.bouncygame.game.DrawManager;
import com.example.bouncygame.game.DrawableObject;
import com.example.bouncygame.game.Game;
import com.example.bouncygame.game.GameObject;
import com.example.bouncygame.hitboxes.GridHitbox;
import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.RectHitbox;
import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.views.GameView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Grid extends GameObject implements CollisionDetectionStatic, DrawableObject {
    private static final String CLASS_NAME = "Grid";

    private final static int[] levelDepthArray = {16,20,25};//how many breakable layers there are (does not count the bedrock layer)

    private Brick[][] brickArray;
    private GridHitbox gridHitbox;

    private final int level;

    private final int visibleRows;
    private int highestRowWithBricks;//lowest row value
    private int deepestRowGenerated;


    public Grid(int visibleRows, int columns, int left, int top, int width,int heightOfBlocks,int level){
        this.level = level;
        this.visibleRows = visibleRows;
        this.gridHitbox = new GridHitbox(left,top,width,heightOfBlocks*(getDepthOfBedrock()+1),getDepthOfBedrock()+1,columns);
        this.brickArray = new Brick[getDepthOfBedrock()+1][columns];


        //for drawing efficiency
        Rect reletiveDimsRect = new Rect(0, 0, (int) getWidthOfBlocks(), (int) getHeightOfBlocks());
        setPictureBounds(reletiveDimsRect);
        Brick.setDamageTextSize((int) getWidthOfBlocks()/2);

        this.highestRowWithBricks = 0;
        this.deepestRowGenerated = -1;
        populateGrid();
    }

    private void setPictureBounds(Rect reletiveDimsRect) {
        ImageCache.bedrock.setBounds(reletiveDimsRect);
        ImageCache.obsidian.setBounds(reletiveDimsRect);
        ImageCache.dirt.setBounds(reletiveDimsRect);
        ImageCache.stone.setBounds(reletiveDimsRect);
        ImageCache.crackImage.setBounds(reletiveDimsRect);
        ImageCache.iron_ore.setBounds(reletiveDimsRect);
        ImageCache.diamond_ore.setBounds(reletiveDimsRect);
        ImageCache.glass.setBounds(reletiveDimsRect);
        ImageCache.tnt.setBounds(reletiveDimsRect);
        ImageCache.gunpowder.setBounds(reletiveDimsRect);
    }

    private void populateGrid() {
        for(int depth=0;depth<visibleRows;depth++){
            generateNextRow();
        }

    }

    private void generateNextRow() {
        deepestRowGenerated++;
        populateRow(deepestRowGenerated,level);
    }

    private void populateRow(int row, int level) {
        boolean maxDepthReached = row>=getDepthOfBedrock();

        Random rand = new Random();
        for(int column=0;column<getColumns();column++){
            Brick brick;
            if(maxDepthReached){
                brick = new Bedrock(row,column,this);
            }else{
                if(rand.nextDouble()<.15){
                    brick = new Obsidian(row,column,this);
                }else if(rand.nextDouble()<.3){
                    brick = new Dirt(row,column,this);
                }else if (rand.nextDouble()<.45){
                    brick = new IronOre(row,column,this);
                }else if (rand.nextDouble()<.6){
                    brick = new DiamondOre(row,column,this);
                }else if (rand.nextDouble()<.75){
                    brick = new Glass(row,column,this);
                }else if (rand.nextDouble()<.9){
                    brick = new TNT(row,column,this);
                }
                else{
                    brick = new Stone(row,column,this);
                }
            }


            Log.d(CLASS_NAME,"@@@ Made brick at row: " + row + " column: " + column + " x: " + row*getWidthOfBlocks() + " y: " + column*getHeightOfBlocks());
            setBlock(brick,row,column);
        }

    }

    public int getLeft() {
        return (int) gridHitbox.getLeft();
    }


    public int getTop() {
        return (int) gridHitbox.getTop();
    }

    public int getWidth() {
        return (int) gridHitbox.getRectDiameterX();
    }


    public int getHeight() {
        return (int) gridHitbox.getRectDiameterY();
    }


    public float getWidthOfBlocks() {
        return gridHitbox.getWidthOfBlocks();
    }


    public float getHeightOfBlocks() {
        return gridHitbox.getHeightOfBlocks();
    }

    public int getHighestRowWithBricks() {
        return highestRowWithBricks;
    }

    @Override
    public GridHitbox getHitbox() {
        return gridHitbox;
    }



    private Brick getBrick(int row, int column) {
        return brickArray[row][column];
    }

    public void setBlock(Brick brick,int row, int column){
        brickArray[row][column] = brick;
        if(brick==null){
            gridHitbox.deleteGridSegment(row,column);
        }else{
            gridHitbox.activateGridSegment(row,column);
        }
    }
    public void deleteBrick(int row, int column) {
        brickArray[row][column] = null;
        gridHitbox.deleteGridSegment(row,column);


        while(rowIsClear(highestRowWithBricks)){
            highestRowWithBricks++;
            Game.getInstance().recieveEvent(Game.GameEvent.ROW_CLEARED,null);
            if(deepestRowGenerated<getDepthOfBedrock()){
                generateNextRow();
            }

        }

        if(isClear()){
            tellGame(Game.GameEvent.WIN);
        }
    }

    private boolean rowIsClear(int row) {
        if(row>=brickArray.length || row<0){
            return false;
        }

        Brick[] brickRow = brickArray[row];
        Log.d(CLASS_NAME,"brickRow: " + row + "  " + Arrays.toString(brickRow));
        for(int column=0;column<brickRow.length;column++){
            Log.d(CLASS_NAME,"column: " + column + "  " + brickArray[column]);
            if(brickRow[column]!=null){
                return false;
            }
        }
        return true;
    }

    public int getDeepestRowGenerated() {
        return deepestRowGenerated;
    }

    public int getDepthOfBedrock() {
        return levelDepthArray[level];
    }

    public boolean isClear() {
        return highestRowWithBricks==getDepthOfBedrock();
    }

    public int getVisibleRows() {
        return visibleRows;
    }

    public int getColumns(){
        return gridHitbox.getColumns();
    }

    //
//    public int xToColumn(float x){
//        return (int) ((x-left)/widthOfBlocks);
//    }
//    public int yToRow(float y){
//        return (int) ((y-top)/heightOfBlocks);
//    }
//
    public float rowToY(int row) {
        return gridHitbox.rowToY(row);
    }

    public RectHitbox getBrickHitbox(int row, int column) {
        return brickArray[row][column].getHitbox();
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        for(int row=getHighestRowWithBricks();row<=getDeepestRowGenerated();row++){
            for(int column=0;column<getColumns();column++){
                Brick brick = getBrick(row,column);
                if(brick==null){
                    continue;
                }

                canvas.save();
                canvas.translate(brick.getHitbox().getLeft(),brick.getHitbox().getTop());
                brick.getPicture().draw(canvas);
                if(brick.getHitPoints()<brick.getMaxHitPoints()){
                    ImageCache.setOpacity(ImageCache.crackImage,1-brick.getHitPoints()/(double) brick.getMaxHitPoints());
                    ImageCache.crackImage.draw(canvas);
                }
                canvas.restore();
            }
        }
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.MIDDLEGROUND;
    }

    public List<Brick> getAllBricksCollidingWith(Hitbox otherHitbox) {
        List<Brick> list = new ArrayList<>();
        int startRow = gridHitbox.yToRow(otherHitbox.getTop());
        int endRow = gridHitbox.yToRow(otherHitbox.getBottom());;
        int startColumn = gridHitbox.xToColumn(otherHitbox.getLeft());
        int endColumn = gridHitbox.xToColumn(otherHitbox.getRight());

        if(startRow<0) startRow = 0;
        if(startColumn<0) startColumn = 0;
        if(endRow>=gridHitbox.getRows()) endRow = gridHitbox.getRows()-1;
        if(endColumn>=gridHitbox.getColumns()) endColumn = gridHitbox.getColumns()-1;

        for(int row=startRow;row<=endRow;row++){
            for(int column = startColumn;column<=endColumn;column++){
                Brick brick = brickArray[row][column];
                if(brick==null){
                    continue;
                }
                RectHitbox rectHitbox = brick.getHitbox();

                if(rectHitbox.intersects(otherHitbox)){
                    list.add(brickArray[row][column]);
                }

            }
        }

        return list;
    }


    @Override
    public void handleCollision(CollisionDetectionObject secondObject) {
        CollisionMediator.handleCollision(this,secondObject);
    }

}

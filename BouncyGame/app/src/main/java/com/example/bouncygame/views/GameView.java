package com.example.bouncygame.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.bouncygame.R;
import com.example.bouncygame.game.Game;
import com.example.bouncygame.images.ImageCache;

import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View {
    private static final String CLASS_NAME = "GameView";
    private static final int FRAME_PERIOD = 30;

    private Paint paint;
    private Timer timer;
    private InputStatus inputStatus;



    public GameView(Context context) {
        super(context);

        init(context,null);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context,attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context,attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context,attrs);
    }


    private void init(Context context,@Nullable AttributeSet set){
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        inputStatus = new InputStatus();

//        stone = BitmapFactory.decodeResource(getResources(), R.drawable.stone);



        //from stackoverflow: start game is called after the width and height are known
        post(new Runnable() {
            @Override
            public void run() {
                startGame(); //width and height is ready
            }
        });
        //
    }


    private void startGame(){
        Game.initilizeInstance(this);
        Game.getInstance().initilizeGame(this);
        Log.d(CLASS_NAME, "Game made");

        this.timer = new Timer();

        TimerTask gameTickTimerTask = new TimerTask(){
            @Override
            public void run() {
                Log.d(CLASS_NAME, "New Tick");
                Game.getInstance().gameTick(inputStatus);
                Log.d(CLASS_NAME, "New Draw");
                postInvalidate();//redraw: calls onDraw(canvas)
            }
        };

//        TimerTask redrawTimerTask = new TimerTask(){
//
//            @Override
//            public void run() {
//
//            }
//        };
        timer.scheduleAtFixedRate(gameTickTimerTask,500,FRAME_PERIOD);
//        timer.scheduleAtFixedRate(redrawTimerTask,500,40);


    }

//    private void startGame(){
//        this.game = new Game(this);
//
//        this.timer = new Timer();
//        TimerTask timerTask = new TimerTask(){
//
//            @Override
//            public void run() {
//                Log.d(CLASS_NAME, "New Tick");
//                game.gameTick(inputStatus);
//                postInvalidate();//redraw: calls onDraw(canvas)
//            }
//        };
//        timer.scheduleAtFixedRate(timerTask,500,30);
//
//
//    }

    @Override
    protected void onDraw(Canvas canvas){
        Game game = Game.getInstance();
        if (game==null) {
            Log.d(CLASS_NAME,"tried to draw when game null");
        }else{
            game.draw(this,paint,canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean value = super.onTouchEvent(event);

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                Log.d(CLASS_NAME,"action down");
                inputStatus.setTouching(true);
                inputStatus.setCurrentMouseX(event.getX());
                inputStatus.setCurrentMouseY(event.getY());
                Game.getInstance().mouseActionDown(event.getX(),event.getY());
                return true;
            }
            case MotionEvent.ACTION_UP: {
                Log.d(CLASS_NAME,"action up");
                inputStatus.setTouching(false);
                Game.getInstance().mouseActionUp();
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d(CLASS_NAME,"action move");
                inputStatus.setCurrentMouseX(event.getX());
                inputStatus.setCurrentMouseY(event.getY());
                Game.getInstance().mouseActionMove(event.getX(),event.getY());

                return true;
            }
        }

        return value;
    }

    public int getFramePeriod() {
        return FRAME_PERIOD;
    }
}

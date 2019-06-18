package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.views.GameView;

public class MainBall extends BouncyBall{
    private static final String CLASS_NAME = "MainBall";

    public static final int DAMAGE = 25;
    public static final float RADIUS = 30;



    public MainBall(float xStart, float yStart, float speed, double initialDirection){
        super(xStart,yStart,speed,initialDirection,RADIUS,DAMAGE);
        ImageCache.spikeBall.setBounds( (int) -RADIUS-3,(int) -RADIUS-3,(int) RADIUS+3,(int) RADIUS+3);//////////seting every time it spawns
    }


    @Override
    public void outOfBoundsHandle(){
        super.outOfBoundsHandle();
        tellGame(Game.GameEvent.MAIN_BALL_LOST);
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        canvas.save();
        canvas.translate(getHitbox().getCenterX(), getHitbox().getCenterY());
        canvas.rotate(getRotation());
        ImageCache.spikeBall.draw(canvas);
        canvas.restore();
//        paint.setColor(Color.BLUE);
//        canvas.drawCircle(getXPosition(),getYPosition(),getSize(),paint);
    }



}

package com.example.bouncygame.bricks;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.bouncygame.game.DrawManager;
import com.example.bouncygame.game.DrawableObject;
import com.example.bouncygame.game.GameObject;
import com.example.bouncygame.game.TickObject;
import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

import java.util.Random;

public class BrickParticles extends GameObject implements DrawableObject, TickObject {

    private static final int SIDE_NUM = 3;//SIDE_NUM squared is the number of paricals
    private static final int LIFE_TIME = 40;//number of ticks the particles last
    static final Random rand = new Random();//package accsess for perfomance with inner class

    private BrickParticle[] particles;
    private int lifeLeft;//in ticks


    public BrickParticles(Drawable picture, float xBrick, float yBrick,float width,float height,float averageXVelocity, float averageYVelocity){
        this.lifeLeft = LIFE_TIME;

        int widthOfParticle = (int) (width/SIDE_NUM);
        int heightOfParticle = (int) (height/SIDE_NUM);

        particles = new BrickParticle[SIDE_NUM*SIDE_NUM];
        int particalArrayIndex = 0;
        for(int i=0;i<SIDE_NUM;i++){
            for(int j=0;j<SIDE_NUM;j++){
                int relLeft = widthOfParticle*i;
                int relTop = heightOfParticle*j;
                particles[particalArrayIndex] = new BrickParticle(picture,xBrick,yBrick,new Rect(relLeft,relTop,relLeft+widthOfParticle,relTop+heightOfParticle),averageXVelocity,averageYVelocity);
                particalArrayIndex++;
            }
        }
    }
    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        for(int i=0;i<particles.length;i++){
            particles[i].draw(view,paint,canvas);
        }
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.MIDDLEGROUND;
    }


    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        lifeLeft--;
        for(int i=0;i<particles.length;i++){
            particles[i].moveTick();
        }
        if(lifeLeft<0){
            this.delete();
        }
    }







    //not a game object.
    public class BrickParticle implements DrawableObject{

        private static final float GRAVITY = 2f;
        private static final float MIN_X_VELOCITY = 1f;
        private static final float MAX_X_VELOCITY = 10f;
        private static final float MIN_Y_VELOCITY = 1f;
        private static final float MAX_Y_VELOCITY = 10f;

        private Drawable picture;
        private float xPosition;//of picture origin, not corner of particle
        private float yPosition;
        private float xVelocity;
        private float yVelocity;

        private Rect clippingRect;//for drawing  relitive coordinates to top left of picture

        public BrickParticle(Drawable picture, float xStart, float yStart,Rect clippingRect, float averageXVelocity, float averateYVelocity) {
            this.picture = picture;
            this.xPosition = xStart;
            this.yPosition = yStart;
            this.clippingRect = clippingRect;

            this.xVelocity = getRandomXVelocity() + averageXVelocity;
            this.yVelocity = getRandomYVelocity() + averateYVelocity;
        }


        private float getRandomXVelocity() {
            float velocity = rand.nextFloat()*(MAX_X_VELOCITY-MIN_X_VELOCITY)+MIN_X_VELOCITY;
            //50%chance of being negative
            if(rand.nextFloat()<0.5f){
                velocity*=-1;
            }
            return velocity;
        }
        private float getRandomYVelocity() {
            float velocity = rand.nextFloat()*(MAX_Y_VELOCITY-MIN_Y_VELOCITY)+MIN_Y_VELOCITY;
            return -velocity;//negative to make it go up initially
        }

        @Override
        public void draw(GameView view, Paint paint, Canvas canvas) {
            canvas.save();//////only save once?
            canvas.translate(xPosition,yPosition);
            canvas.clipRect(clippingRect);
            picture.draw(canvas);
            canvas.restore();
        }
        @Override
        public int getDrawLayerToAddTo() {
            return DrawManager.MIDDLEGROUND;
        }

        public void moveTick() {
            //change position
            xPosition+=xVelocity;
            yPosition+=yVelocity;
            //apply gravity
            yVelocity+=GRAVITY;
        }
    }
}

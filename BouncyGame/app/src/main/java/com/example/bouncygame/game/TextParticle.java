package com.example.bouncygame.game;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

//this text floats up for a fixed amount of time, then dissapears
public class TextParticle extends GameObject implements  DrawableObject, TickObject{

    private static final float VELOCITY = 2f;
    private static final int LIFE_TIME = 40;//number of ticks the particles last

    private int lifeLeft;//in ticks

    private String text;
    private float xPosition;
    private float yPosition;
    private int textSize;

    private ObjectAnimator growAnimator;

    public TextParticle(String text, float xStart, float yStart, int textSize) {
        this.text = text;
        this.xPosition = xStart;
        this.yPosition = yStart;
        this.textSize = textSize;
        this.lifeLeft = LIFE_TIME;
    }

    public void setYPosition(float newPosition){
        this.yPosition = newPosition;
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        paint.setTextSize(textSize);
        paint.setColor(Color.BLACK);
        canvas.drawText(text,xPosition,yPosition,paint);
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.MIDDLEGROUND;
    }

    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        yPosition-=VELOCITY;//subtract to move up on screen
        lifeLeft--;
        if(lifeLeft<0){
            this.delete();
        }
    }
}

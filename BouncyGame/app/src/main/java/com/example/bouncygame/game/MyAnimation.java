package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class MyAnimation {

    private Drawable[] pictures;
    private int ticksPerFrame;
    private boolean repeats;
    private AnimationCycleListener listener;

    private int ticksUntilNextFrame;
    private int frameNumber;

    public MyAnimation(Drawable[] pictures, int ticksPerFrame,boolean repeats,AnimationCycleListener listener) {
        this.pictures = pictures;
        this.ticksPerFrame = ticksPerFrame;
        this.repeats = repeats;
        this.listener = listener;
        this.ticksUntilNextFrame = ticksPerFrame;
        this.frameNumber = 0;
    }

    public void draw(Canvas canvas) {
        pictures[frameNumber].draw(canvas);
    }

    public void advanceTick() {
        ticksUntilNextFrame--;
        if(ticksUntilNextFrame<=0){
            advanceFrame();
            ticksUntilNextFrame = ticksPerFrame;
        }
    }

    private void advanceFrame() {
        frameNumber++;
        if(repeats){
            if(frameNumber>=pictures.length){
                frameNumber = 0;
                listener.animationFinishedCycle();
            }
        }else{
            if(frameNumber>=pictures.length){
                frameNumber--;
                listener.animationFinishedCycle();
            }
        }
    }
}

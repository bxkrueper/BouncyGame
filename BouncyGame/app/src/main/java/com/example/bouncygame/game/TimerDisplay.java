package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

public class TimerDisplay implements InterfaceObject, TickObject{


    private int ticks;
    private String timeString;
    private int rightSideOfScreen;
    private int start;//x position where it starts to draw
    private final int framePeriod;//miliseconds per frame

    public TimerDisplay(int framePeriod,int rightSideOfScreen){
        this.ticks = 0;
        this.timeString = "0";
        this.rightSideOfScreen = rightSideOfScreen;
        this.start = rightSideOfScreen-150;
        this.framePeriod = framePeriod;
    }

    public int getTicks() {
        return ticks;
    }
    public int getSeconds() {
        return (ticks*framePeriod)/1000;
    }

    public void reset() {
        this.ticks = 0;
        this.timeString = "0";
    }


    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        paint.setTextSize(80);
        paint.setColor(Color.BLACK);
        canvas.drawText(timeString,start,90,paint);
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.INTERFACE;
    }


    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        ticks++;
        setTimeString(ticks);
    }

    private void setTimeString(int ticks) {
        timeString = String.format("%d",getSeconds());
    }
}

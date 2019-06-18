package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

public class ScoreDisplay implements InterfaceObject , TickObject{

    private final int ANIMATION_TIME = 20;//in ticks
    private final int DEFAULT_SIZE = 80;
    private final int MAX_SIZE = 130;
    private final int TICKS_TO_MAX_SIZE = 5;

    private final int SLOPE1 = (MAX_SIZE-DEFAULT_SIZE)/TICKS_TO_MAX_SIZE;//for valueTextSize calculation
    private final int SLOPE2 = (DEFAULT_SIZE-MAX_SIZE)/(ANIMATION_TIME-TICKS_TO_MAX_SIZE);//for valueTextSize calculation
    private final int B2 = -SLOPE2*ANIMATION_TIME+DEFAULT_SIZE;//for valueTextSize calculation

    private int score;
    private String scoreString;
    private int valueTextSize;
    private int ticksSinceLastUpdate;//resets to -1 when it has been at least ANIMATION_TIME since last update. There would be no meaning for values past ANIMATION_TIME, so -1 is used instead

    public ScoreDisplay(){
        this.score = 0;
        this.scoreString = "0";
        this.valueTextSize = DEFAULT_SIZE;
        this.ticksSinceLastUpdate = -1;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        this.scoreString = String.format("%,d",score);
        this.ticksSinceLastUpdate = 0;
    }

    public void addToScore(int toAdd){
        setScore(score+toAdd);
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        paint.setTextSize(60);
        paint.setColor(Color.BLACK);
        canvas.drawText("Score: ",100,90,paint);
        paint.setTextSize(valueTextSize);
        canvas.drawText(scoreString,293,90,paint);
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.INTERFACE;
    }


    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        valueTextSize = ticksSinceLastUpdateToSize(ticksSinceLastUpdate);
        if(ticksSinceLastUpdate!=-1){
            ticksSinceLastUpdate++;
        }
        if(ticksSinceLastUpdate>ANIMATION_TIME){
            ticksSinceLastUpdate = -1;
        }
    }

    private int ticksSinceLastUpdateToSize(int ticksSinceLastUpdate) {
        if(ticksSinceLastUpdate==-1){
            return DEFAULT_SIZE;
        }

        if(ticksSinceLastUpdate<5){
            return SLOPE1*ticksSinceLastUpdate+DEFAULT_SIZE;
        }else{
            return SLOPE2*ticksSinceLastUpdate+B2;
        }
    }
}

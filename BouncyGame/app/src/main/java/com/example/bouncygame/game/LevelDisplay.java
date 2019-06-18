package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bouncygame.views.GameView;

public class LevelDisplay implements InterfaceObject{


    private int level;
    private String levelString;
    private int start;//x position where it starts to draw

    public LevelDisplay(int rightSideOfScreen){
        this.level = 0;
        this.levelString = "0";
        this.start = rightSideOfScreen-500;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int newLevel) {
        this.level = newLevel;
        this.levelString = String.format("%,d", level);
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        paint.setTextSize(40);
        paint.setColor(Color.BLACK);
        canvas.drawText("level: ",start,90,paint);
        paint.setTextSize(40);
        canvas.drawText(levelString,start+100,90,paint);
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.INTERFACE;
    }


}

package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bouncygame.views.GameView;

public class DepthDisplay implements InterfaceObject{


    private int depth;
    private String depthString;
    private int start;//x position where it starts to draw

    public DepthDisplay(int rightSideOfScreen){
        this.depth = 0;
        this.depthString = "0";
        this.start = rightSideOfScreen-350;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int newLevel) {
        this.depth = newLevel;
        this.depthString = String.format("%,d", depth);
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        paint.setTextSize(40);
        paint.setColor(Color.BLACK);
        canvas.drawText("depth: ",start,90,paint);
        paint.setTextSize(40);
        canvas.drawText(depthString,start+120,90,paint);
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.INTERFACE;
    }


}

package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bouncygame.views.GameView;

public class TopInterfaceBackground implements InterfaceObject {

    private int width;
    private int height;

    public TopInterfaceBackground(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,width,height,paint);
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.INTERFACE;
    }
}

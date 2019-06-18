package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.bouncygame.views.GameView;

public interface DrawableObject {
    void draw(GameView view, Paint paint, Canvas canvas);
    int getDrawLayerToAddTo();//numbers in DrawManager
}

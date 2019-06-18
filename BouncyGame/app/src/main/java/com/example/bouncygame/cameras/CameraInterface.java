package com.example.bouncygame.cameras;

import android.graphics.Canvas;

public interface CameraInterface {
    void editCanvasPreDraw(Canvas canvas);//call before drawing a frame. this method makes the appropriate translation and scale calculations to the canvas

    float screenToGameX(float screenX);
    float screenToGameY(float screenY);

    float getLeft();
    void setLeft(float xLeft);
    float getTop();
    void setTop(float yTop);
    float getRight();
    float getBottom();
    void moveX(float dx);
    void moveY(float dy);
}

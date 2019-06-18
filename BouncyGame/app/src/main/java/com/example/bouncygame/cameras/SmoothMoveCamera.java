package com.example.bouncygame.cameras;

import android.graphics.Canvas;

import com.example.bouncygame.game.Game;
import com.example.bouncygame.game.GameObject;
import com.example.bouncygame.game.TickObject;
import com.example.bouncygame.views.InputStatus;

public class SmoothMoveCamera extends GameObject implements CameraInterface, TickObject {

    private TranslateCamera camera;
    private float percentPerTick;//how far to move the camera toward its goal per tick
    private float distanceToJump;//how close the camera has to be to its goal for it to just jump there on the next tick

    private float cameraGoalX;
    private float cameraGoalY;


    public SmoothMoveCamera(TranslateCamera camera,float percentPerTick,float distanceToJump){
        this.camera = camera;
        this.cameraGoalX = camera.getLeft();
        this.cameraGoalY = camera.getTop();
        this.percentPerTick = percentPerTick;
        this.distanceToJump = distanceToJump;
    }


    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        moveX();
        moveY();
    }

    private void moveX() {
        if(cameraGoalX!=camera.getLeft()){
            if(Math.abs(cameraGoalX-camera.getLeft())<distanceToJump){
                camera.setLeft(cameraGoalX);
            }else{
                camera.setLeft(interpolate(camera.getLeft(),cameraGoalX,percentPerTick));
            }
        }
    }

    private void moveY() {
        if(cameraGoalY!=camera.getTop()){
            if(Math.abs(cameraGoalY-camera.getTop())<distanceToJump){
                camera.setTop(cameraGoalY);
            }else{
                camera.setTop(interpolate(camera.getTop(),cameraGoalY,percentPerTick));
            }
        }
    }

    private float interpolate(float start, float end, float percentOfTheWay){
        return start+(end-start)*percentOfTheWay;
    }

    @Override
    public void editCanvasPreDraw(Canvas canvas) {
        camera.editCanvasPreDraw(canvas);
    }

    @Override
    public float screenToGameX(float screenX) {
        return camera.screenToGameX(screenX);
    }

    @Override
    public float screenToGameY(float screenY) {
        return camera.screenToGameY(screenY);
    }

    @Override
    public float getLeft() {
        return camera.getLeft();
    }

    @Override
    public void setLeft(float xLeft) {
        cameraGoalX=xLeft;
    }

    @Override
    public float getTop() {
        return camera.getTop();
    }

    @Override
    public void setTop(float yTop) {
        cameraGoalY=yTop;
    }

    @Override
    public float getRight() {
        return camera.getRight();
    }

    @Override
    public float getBottom() {
        return camera.getBottom();
    }

    @Override
    public void moveX(float dx) {
        cameraGoalX+=dx;
    }

    @Override
    public void moveY(float dy) {
        cameraGoalY+=dy;
    }

    //bypasses this class's smooth moves
    public void setLeftInstantly(float xLeft) {
        cameraGoalX=xLeft;
        camera.setLeft(xLeft);
    }
    public void setTopInstantly(float yTop) {
        cameraGoalY=yTop;
        camera.setTop(yTop);
    }
}

package com.example.bouncygame.game;

import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.views.InputStatus;

public class ObjectPercentMover extends GameObject implements TickObject {

    private SpatialObject objectToMove;
    private Hitbox hitbox;
    private float percentPerTick;//how far to move the object toward its goal per tick
    private float distanceToJump;//how close the object has to be to its goal for it to just jump there on the next tick

    private float goalX;//left
    private float goalY;//top
    private boolean canDoHorizontalManipulation;
    private boolean canDoVerticalManipulation;


    public ObjectPercentMover(SpatialObject objectToMove,float percentPerTick,float distanceToJump,boolean horizontal,boolean vertical){
        this.objectToMove = objectToMove;
        this.hitbox = objectToMove.getHitbox();
        this.goalX = objectToMove.getHitbox().getLeft();
        this.goalY = objectToMove.getHitbox().getTop();
        this.percentPerTick = percentPerTick;
        this.distanceToJump = distanceToJump;
        this.canDoHorizontalManipulation = horizontal;
        this.canDoVerticalManipulation = vertical;
    }

    public boolean canDoHorizontalManipulation() {
        return canDoHorizontalManipulation;
    }

    public void setHorizontalManipulation(boolean canDoHorizontalManipulation) {
        this.canDoHorizontalManipulation = canDoHorizontalManipulation;
    }

    public boolean canDoVerticalManipulation() {
        return canDoVerticalManipulation;
    }

    public void setVerticalManipulation(boolean canDoVerticalManipulation) {
        this.canDoVerticalManipulation = canDoVerticalManipulation;
    }

    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        if(canDoHorizontalManipulation){
            moveX();
        }
        if(canDoVerticalManipulation){
            moveY();
        }
    }

    private void moveX() {
        if(goalX!=hitbox.getLeft()){
            if(Math.abs(goalX-hitbox.getLeft())<distanceToJump){
                hitbox.setLeft(goalX);
            }else{
                hitbox.setLeft(interpolate(hitbox.getLeft(),goalX,percentPerTick));
            }
        }
    }

    private void moveY() {
        if(goalY!=hitbox.getTop()){
            if(Math.abs(goalY-hitbox.getTop())<distanceToJump){
                hitbox.setTop(goalY);
            }else{
                hitbox.setTop(interpolate(hitbox.getTop(),goalY,percentPerTick));
            }
        }
    }

    private float interpolate(float start, float end, float percentOfTheWay){
        return start+(end-start)*percentOfTheWay;
    }

    public void setGoalLeft(float xLeft) {
        goalX=xLeft;
    }

    public void setGoalTop(float yTop) {
        goalY=yTop;
    }

    public void setGoalCenterX(float centerX){
        goalX = centerX-hitbox.getRectRadiusX();
    }
    public void setGoalCenterY(float centerY){
        goalY = centerY-hitbox.getRectRadiusY();
    }

    //bypasses this class's smooth moves
    public void setLeftInstantly(float xLeft) {
        goalX=xLeft;
        hitbox.setLeft(xLeft);
    }
    public void setTopInstantly(float yTop) {
        goalY=yTop;
        hitbox.setTop(yTop);
    }


}
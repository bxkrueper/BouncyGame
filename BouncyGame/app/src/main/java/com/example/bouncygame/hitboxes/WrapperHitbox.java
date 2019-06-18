package com.example.bouncygame.hitboxes;

import java.util.HashSet;
import java.util.Set;

public abstract class WrapperHitbox implements Hitbox{

    private Hitbox innerHitbox;

    public WrapperHitbox(Hitbox hitbox){
        this.innerHitbox = hitbox;
    }


    public Hitbox getInnerHitbox(){
        return innerHitbox;
    }



    //need to override intersects and rayCast





    //below methods. just call innerHitbox methods directly

    @Override
    public boolean containsPoint(float x, float y) {
        return innerHitbox.containsPoint(x,y);
    }

    @Override
    public float getCenterX() {
        return innerHitbox.getCenterX();
    }

    @Override
    public float getCenterY() {
        return innerHitbox.getCenterY();
    }

    @Override
    public float getRectDiameterX() {
        return innerHitbox.getRectDiameterX();
    }

    @Override
    public float getRectDiameterY() {
        return innerHitbox.getRectDiameterY();
    }

    @Override
    public float getRectRadiusX() {
        return innerHitbox.getRectRadiusX();
    }

    @Override
    public float getRectRadiusY() {
        return innerHitbox.getRectRadiusY();
    }

    @Override
    public float getLeft() {
        return innerHitbox.getLeft();
    }

    @Override
    public float getRight() {
        return innerHitbox.getRight();
    }

    @Override
    public float getTop() {
        return innerHitbox.getTop();
    }

    @Override
    public float getBottom() {
        return innerHitbox.getBottom();
    }


    @Override
    public void moveXBy(float dX) {
        innerHitbox.moveXBy(dX);
    }

    @Override
    public void moveYBy(float dY) {
        innerHitbox.moveYBy(dY);
    }

    @Override
    public void setCenterX(float x){
        innerHitbox.setCenterX(x);
    }

    @Override
    public void setCenterY(float y){
        innerHitbox.setCenterY(y);
    }

    @Override
    public void setLeft(float newLeft) {
        innerHitbox.setLeft(newLeft);
    }

    @Override
    public void setTop(float newTop) {
        innerHitbox.setTop(newTop);
    }

    @Override
    public void setRight(float newRight) {
        innerHitbox.setRight(newRight);
    }

    @Override
    public void setBottom(float newBottom) {
        innerHitbox.setBottom(newBottom);
    }

}


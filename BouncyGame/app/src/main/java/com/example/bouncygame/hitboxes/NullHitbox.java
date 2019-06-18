package com.example.bouncygame.hitboxes;

import com.example.bouncygame.game.SpatialObject;

import java.util.Set;

public class NullHitbox implements Hitbox{

    private static NullHitbox instance;

    private NullHitbox(){
    }

    public static NullHitbox getInstance(){
        if(instance==null){
            instance = new NullHitbox();
        }
        return instance;
    }

    @Override
    public boolean containsPoint(float x, float y) {
        return false;
    }

    @Override
    public boolean intersects(Hitbox otherHitbox) {
        return false;
    }

    @Override
    public boolean rayCast(LineHitbox ray, double[] collisionCoordinates) {
        return false;
    }

    @Override
    public float getCenterX() {
        return 0;
    }

    @Override
    public float getCenterY() {
        return 0;
    }

    @Override
    public float getRectDiameterX() {
        return 0;
    }

    @Override
    public float getRectDiameterY() {
        return 0;
    }

    @Override
    public float getRectRadiusX() {
        return 0;
    }

    @Override
    public float getRectRadiusY() {
        return 0;
    }

    @Override
    public float getLeft() {
        return 0;
    }

    @Override
    public float getRight() {
        return 0;
    }

    @Override
    public float getTop() {
        return 0;
    }

    @Override
    public float getBottom() {
        return 0;
    }

    @Override
    public void moveXBy(float dX) {
    }

    @Override
    public void moveYBy(float dY) {
    }

    @Override
    public void setCenterX(float x){
    }

    @Override
    public void setCenterY(float y){
    }

    @Override
    public void setLeft(float newLeft) {

    }

    @Override
    public void setTop(float newTop) {

    }

    @Override
    public void setRight(float newRight) {

    }

    @Override
    public void setBottom(float newBottom) {

    }

}

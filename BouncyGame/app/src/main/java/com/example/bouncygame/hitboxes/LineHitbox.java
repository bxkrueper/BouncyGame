package com.example.bouncygame.hitboxes;

public class LineHitbox implements Hitbox{

    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public LineHitbox(float x1,float y1, float x2, float y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    @Override
    public boolean containsPoint(float x, float y) {
        return false;////////////////////////////////////////
    }

    @Override
    public boolean intersects(Hitbox otherHitbox) {
        return HitboxMediator.intersects(this,otherHitbox);
    }

    @Override
    public boolean rayCast(LineHitbox ray, double[] collisionCoordinates) {
        return RayCastMediator.rayCast(this,ray,collisionCoordinates);
    }

    @Override
    public float getCenterX() {
        return (x2+x1)/2;
    }

    @Override
    public float getCenterY() {
        return (y2+y1/2);
    }

    @Override
    public float getRectDiameterX() {
        return Math.abs(x2-x1);
    }

    @Override
    public float getRectDiameterY() {
        return Math.abs(y2-y1);
    }

    @Override
    public float getRectRadiusX() {
        return getRectDiameterX()/2;
    }

    @Override
    public float getRectRadiusY() {
        return getRectDiameterY()/2;
    }

    @Override
    public float getLeft() {
        return Math.min(x1,x2);
    }

    @Override
    public float getRight() {
        return Math.max(x1,x2);
    }

    @Override
    public float getTop() {
        return Math.min(y1,y2);
    }

    @Override
    public float getBottom() {
        return Math.max(y1,y2);
    }

    @Override
    public void setCenterX(float x){
        moveXBy(x-getCenterX());
    }

    @Override
    public void setCenterY(float y){
        moveYBy(y-getCenterY());
    }

    @Override
    public void setLeft(float newLeft) {
        moveXBy(newLeft- getLeft());
    }

    @Override
    public void setTop(float newTop) {
        moveYBy(newTop- getTop());
    }

    @Override
    public void setRight(float newRight) {
        moveXBy(newRight- getRight());
    }

    @Override
    public void setBottom(float newBottom) {
        moveYBy(newBottom- getBottom());
    }

    @Override
    public void moveXBy(float dX) {
        x1+=dX;
        x2+=dX;
    }

    @Override
    public void moveYBy(float dY) {
        y1+=dY;
        y2+=dY;
    }



}

package com.example.bouncygame.hitboxes;

import com.example.bouncygame.game.SpatialObject;

import java.util.HashSet;
import java.util.Set;

public class CircleHitbox implements Hitbox{
    private static final String CLASS_NAME = "CircleHitbox";

    private float xPosition;
    private float yPosition;
    private float radius;



    public CircleHitbox(float xPosition, float yPosition, float radius){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.radius = radius;


    }

    @Override
    public boolean containsPoint(float x, float y) {
        return Math.hypot(x-xPosition,y-yPosition)<radius;
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
        return getxPosition();
    }

    @Override
    public void setCenterX(float x){
        this.xPosition = x;
    }

    @Override
    public void setCenterY(float y){
        this.yPosition = y;
    }

    @Override
    public void setLeft(float newLeft) {
        xPosition = newLeft+radius;
    }

    @Override
    public void setTop(float newTop) {
        yPosition = newTop+radius;
    }

    @Override
    public void setRight(float newRight) {
        xPosition = newRight-radius;
    }

    @Override
    public void setBottom(float newBottom) {
        yPosition = newBottom-radius;
    }

    @Override
    public void moveXBy(float dX) {
        xPosition+=dX;
    }

    @Override
    public void moveYBy(float dY) {
        yPosition+=dY;
    }

    @Override
    public float getCenterY() {
        return getyPosition();
    }

    @Override
    public float getRectDiameterX() {
        return radius*2;
    }

    @Override
    public float getRectDiameterY() {
        return radius*2;
    }

    @Override
    public float getRectRadiusX() {
        return radius;
    }

    @Override
    public float getRectRadiusY() {
        return radius;
    }

    @Override
    public float getLeft() {
        return xPosition-radius;
    }

    @Override
    public float getRight() {
        return xPosition+radius;
    }

    @Override
    public float getTop() {
        return yPosition-radius;
    }

    @Override
    public float getBottom() {
        return yPosition+radius;
    }

    public float getxPosition() {
        return xPosition;
    }

    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getDiameter() {
        return radius*2;
    }

    @Override
    public String toString(){
        return "xPosition: " + xPosition + " yPosition: " + yPosition + " radius: " + radius;
    }
}


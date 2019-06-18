package com.example.bouncygame.hitboxes;

import android.graphics.Rect;

import com.example.bouncygame.game.SpatialObject;

import java.util.HashSet;
import java.util.Set;

public class RectHitbox implements Hitbox{
    private static final String CLASS_NAME = "RectHitbox";

    private float left;
    private float top;
    private float width;
    private float height;

    private double angleToTopRight;
    private double angleToTopLeft;
    private double angleToBottomRight;
    private double angleToBottomLeft;

    public RectHitbox(float left, float top, float width, float height){
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;

        setAngleToBottomLeft();
        setAngleToBottomRight();
        setAngleToTopLeft();
        setAngleToTopRight();
    }



    @Override
    public boolean containsPoint(float x, float y) {
        return x> left && x< left +width && y> top && y< top +height;
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
    public void setLeft(float left) {
        this.left = left;
    }

    @Override
    public void setTop(float top) {
        this.top = top;
    }

    @Override
    public void setRight(float newRight) {
        this.left = newRight-width;
    }

    @Override
    public void setBottom(float newBottom) {
        this.top = newBottom-height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
        setAngleToBottomLeft();
        setAngleToBottomRight();
        setAngleToTopLeft();
        setAngleToTopRight();
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
        setAngleToBottomLeft();
        setAngleToBottomRight();
        setAngleToTopLeft();
        setAngleToTopRight();
    }

    @Override
    public float getCenterX(){
        return left+width/2;
    }


    public float getCenterY(){
        return top+height/2;
    }

    @Override
    public float getRectDiameterX() {
        return getWidth();
    }

    @Override
    public float getRectDiameterY() {
        return getHeight();
    }

    @Override
    public float getRectRadiusX() {
        return getWidth()/2;
    }

    @Override
    public float getRectRadiusY() {
        return getHeight()/2;
    }

    @Override
    public float getLeft() {
        return left;
    }

    @Override
    public float getRight() {
        return left + width;
    }

    @Override
    public float getTop() {
        return top;
    }

    @Override
    public float getBottom() {
        return top + height;
    }

    public double getAngleToTopRight() {
        return angleToTopRight;
    }

    private void setAngleToTopRight() {
        this.angleToTopRight = HitboxMediator.getDirection(getCenterX(),getCenterY(),getRight(),getTop());
    }

    public double getAngleToTopLeft() {
        return angleToTopLeft;
    }

    private void setAngleToTopLeft() {
        this.angleToTopLeft = HitboxMediator.getDirection(getCenterX(),getCenterY(),getLeft(),getTop());
    }

    public double getAngleToBottomRight() {
        return angleToBottomRight;
    }

    private void setAngleToBottomRight() {
        this.angleToBottomRight = HitboxMediator.getDirection(getCenterX(),getCenterY(),getRight(),getBottom());
    }

    public double getAngleToBottomLeft() {
        return angleToBottomLeft;
    }

    private void setAngleToBottomLeft() {
        this.angleToBottomLeft = HitboxMediator.getDirection(getCenterX(),getCenterY(),getLeft(),getBottom());
    }

    @Override
    public String toString(){
        return "Left: " + left + " top: " + top + " width: " + width + " height: " + height;
    }



    @Override
    public void moveXBy(float dX) {
        left+=dX;
    }

    @Override
    public void moveYBy(float dY) {
        top+=dY;
    }

    @Override
    public void setCenterX(float x){
        moveXBy(x-getCenterX());
    }

    @Override
    public void setCenterY(float y){
        moveYBy(y-getCenterY());
    }

}

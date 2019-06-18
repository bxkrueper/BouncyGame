package com.example.bouncygame.hitboxes;

import com.example.bouncygame.game.SpatialObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompositeHitbox implements Hitbox{
    private static final String CLASS_NAME = "CompositeHitbox";

    private List<Hitbox> hitboxList;


    public CompositeHitbox(List<Hitbox> hitboxList){
        this.hitboxList = hitboxList;
    }
    public CompositeHitbox(Hitbox ... hitboxArray){
        this.hitboxList = new ArrayList<>();
        for(int i=0;i<hitboxArray.length;i++){
            Hitbox hitbox = hitboxArray[i];
            if(hitbox!=null){
                hitboxList.add(hitbox);
            }
        }

    }

    public List<Hitbox> getHitboxList() {
        return hitboxList;
    }

    public void addHitbox(Hitbox hitbox){
        hitboxList.add(hitbox);
    }

    @Override
    public boolean containsPoint(float x, float y) {
        for(int i=0;i<hitboxList.size();i++){
            if(hitboxList.get(i).containsPoint(x,y)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean intersects(Hitbox otherHitbox) {
        for(int i=0;i<hitboxList.size();i++){
            if(hitboxList.get(i).intersects(otherHitbox)){
                return true;
            }
        }

        return false;
    }

    //if there is at least one intersection, fill collisionCoordinates with the closest one
    @Override
    public boolean rayCast(LineHitbox ray, double[] collisionCoordinates) {
        double closestDistance = Double.MAX_VALUE;
        double closestX = Float.MAX_VALUE;
        double closestY = Float.MAX_VALUE;
        for(int i=0;i<hitboxList.size();i++){
            Hitbox hitboxToTest = hitboxList.get(i);
            if(hitboxToTest.rayCast(ray,collisionCoordinates)){
                //collisionCoordinates now has hitboxToTest's collision point if it hit, or it kept its old value if there was no collision
                double distanceToCurrentIntersection = Math.hypot(ray.getX1()-collisionCoordinates[0],ray.getY1()-collisionCoordinates[1]);
                if(distanceToCurrentIntersection<closestDistance){//update closest values
                    closestDistance = distanceToCurrentIntersection;
                    closestX = collisionCoordinates[0];
                    closestY = collisionCoordinates[1];
                }
            }
        }

        if(closestDistance==Double.MAX_VALUE){//there was no collision
            return false;
        }else{
            //put closest values in collisionCoordinates to be used by the caller
            collisionCoordinates[0] = closestX;
            collisionCoordinates[1] = closestY;
            return true;
        }
    }

    @Override
    public float getCenterX() {
        return (getLeft()+ getRight())/2;
    }

    @Override
    public float getCenterY() {
        return (getTop()+ getBottom())/2;
    }

    @Override
    public float getRectDiameterX() {
        return getRight()- getLeft();
    }

    @Override
    public float getRectDiameterY() {
        return getBottom()- getTop();
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
        float mostLeft = Float.MAX_VALUE;
        for(int i=0;i<hitboxList.size();i++){
            float newLeft = hitboxList.get(i).getLeft();
            if(newLeft<mostLeft){
                mostLeft = newLeft;
            }
        }
        return mostLeft;
    }

    @Override
    public float getRight() {
        float mostRight = Float.MIN_VALUE;
        for(int i=0;i<hitboxList.size();i++){
            float newRight = hitboxList.get(i).getRight();
            if(newRight>mostRight){
                mostRight = newRight;
            }
        }
        return mostRight;
    }

    @Override
    public float getTop() {
        float mostTop = Float.MAX_VALUE;
        for(int i=0;i<hitboxList.size();i++){
            float newTop = hitboxList.get(i).getTop();
            if(newTop<mostTop){
                mostTop = newTop;
            }
        }
        return mostTop;
    }

    @Override
    public float getBottom() {
        float mostBottom = Float.MIN_VALUE;
        for(int i=0;i<hitboxList.size();i++){
            float newBottom = hitboxList.get(i).getBottom();
            if(newBottom>mostBottom){
                mostBottom = newBottom;
            }
        }
        return mostBottom;
    }



    @Override
    public void moveXBy(float dX) {
        for(int i=0;i<hitboxList.size();i++){
            hitboxList.get(i).moveXBy(dX);
        }
    }

    @Override
    public void moveYBy(float dY) {
        for(int i=0;i<hitboxList.size();i++){
            hitboxList.get(i).moveYBy(dY);
        }
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

}

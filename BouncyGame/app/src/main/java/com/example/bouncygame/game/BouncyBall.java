package com.example.bouncygame.game;

import android.util.Log;

import com.example.bouncygame.hitboxes.CircleHitbox;
import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.HitboxMediator;
import com.example.bouncygame.views.InputStatus;

public abstract class BouncyBall extends GameObject implements Projectile, DrawableObject, TickObject, CollisionDetectionFree,DoSomethingOnAdd{
    private static final String CLASS_NAME = "BouncyBall";
    private static final float MAX_ROTATION_SPEED = 20f;
    private static final float MIN_Y_SPEED = 5f;

    private CircleHitbox hitbox;
    private float xVelocity;
    private float yVelocity;
    private int damage;
    private float rotation;
    private float rotationSpeed;


    public BouncyBall(float xStart, float yStart, float speed, double initialDirection, float radius, int damage){
        this(new CircleHitbox(xStart,yStart,radius),speed,initialDirection,damage);
    }
    public BouncyBall(CircleHitbox hitbox, float speed, double initialDirection, int damage){
        this.hitbox = hitbox;
        this.xVelocity = (float) (speed*Math.cos(initialDirection));
        this.yVelocity = (float) (speed*Math.sin(initialDirection));
        this.damage = damage;
        this.rotation = 0f;
        rotationSpeed = 0f;
    }



    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }


    public void setRotationSpeed(float newRotationSpeed) {
        //enforses the max rotation speed (both positive and negative limits)
        if(newRotationSpeed>MAX_ROTATION_SPEED){
            rotationSpeed=MAX_ROTATION_SPEED;
        }else if(newRotationSpeed<-MAX_ROTATION_SPEED){
            rotationSpeed=-MAX_ROTATION_SPEED;
        }else{
            this.rotationSpeed = newRotationSpeed;
        }
    }

    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        doVelocity();
        rotation+=rotationSpeed;
    }

    @Override
    public void doOnAdd() {
        Game.getInstance().addToCheckForOutOfBoundsList(this);
    }

    public void outOfBoundsHandle(){
        delete();
    }

    public void recieveMessage(Game.GameEvent event) {
        if(event == Game.GameEvent.OBJECT_OUT_OF_BOUNDS){
            outOfBoundsHandle();
        }else{
            Log.d(CLASS_NAME,"don't recognize event: " + event);
        }
    }

    public void doVelocity(){
        hitbox.setxPosition(hitbox.getxPosition()+xVelocity);
        hitbox.setyPosition(hitbox.getyPosition()+yVelocity);
    }

    @Override
    public void moveForward(float distance) {
        hitbox.moveXBy((float) (distance*Math.cos(calculateDirection())));
        hitbox.moveYBy((float) (distance*Math.sin(calculateDirection())));
    }

//    public void normalizeSpeed(){
//        double direction = calculateDirection();
//        setXVelocity((float) (speed*Math.cos(direction)));
//        setYVelocity((float) (speed*Math.sin(direction)));
//    }

    public double calculateDirection(){
        return HitboxMediator.getDirection(0,0,xVelocity,yVelocity);
    }

    @Override
    public float getXVelocity() {
        return xVelocity;
    }

    @Override
    public float getYVelocity() {
        return yVelocity;
    }

    public void setXVelocity(float newXVelocity){
        this.xVelocity = newXVelocity;
    }
    public void setYVelocity(float newYVelocity){
        this.yVelocity = newYVelocity;

        //enforces mininum y velocity so ball doesn't bounce between the walls infinitly without hitting the paddle
//        if(newYVelocity<MIN_Y_SPEED&&newYVelocity>=0){
//            this.yVelocity = MIN_Y_SPEED;
//        }else if(newYVelocity>-MIN_Y_SPEED&&newYVelocity<0){
//            this.yVelocity = -MIN_Y_SPEED;
//        }else{
//            this.yVelocity = newYVelocity;
//        }

    }

    @Override
    public void setSpeed(float newSpeed) {
        double direction = getDirection();
        setXVelocity((float) (newSpeed*Math.cos(direction)));
        setYVelocity((float) (newSpeed*Math.sin(direction)));
    }

    @Override
    public void setDirection(double newDirection) {
        float speed = getSpeed();
        setXVelocity((float) (speed*Math.cos(newDirection)));
        setYVelocity((float) (speed*Math.sin(newDirection)));
    }


    public float getXPosition() {
        return hitbox.getxPosition();
    }


    public float getYPosition() {
        return hitbox.getyPosition();
    }

    public float getRadius(){
        return hitbox.getRadius();
    }

    @Override
    public float getSpeed() {
        return (float) Math.hypot(xVelocity,yVelocity);
    }

    @Override
    public double getDirection() {
        return HitboxMediator.getDirection(0f,0f,xVelocity,yVelocity);
    }


    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public void handleCollision(CollisionDetectionObject secondObject) {
        CollisionMediator.handleCollision(this,secondObject);
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.MIDDLEGROUND;
    }

    public void setCenterX(float newX){
        hitbox.setCenterX(newX);
    }
    public void setCenterY(float newY){
        hitbox.setCenterY(newY);
    }


}

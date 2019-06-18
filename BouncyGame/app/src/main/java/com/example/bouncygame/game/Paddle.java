package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.RectHitbox;
import com.example.bouncygame.sounds.SoundCache;
import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

public class Paddle extends GameObject implements CollisionDetectionFree, DrawableObject, TickObject{
    private static final String CLASS_NAME = "Paddle";

    private RectHitbox hitbox;
    private float maxSpeed;
    public static final float X_VEL_CONSTANT = 0.03f;

    public Paddle(float xPosition,float yPosition,float width,float height,float maxSpeed){
        this.hitbox = new RectHitbox(xPosition,yPosition,width,height);
        this.maxSpeed = maxSpeed;
    }


    public float getMaxSpeed() {
        return maxSpeed;
    }


    public float getLeft() {
        return hitbox.getLeft();
    }
    public float getTop() {
        return hitbox.getTop();
    }
    public float getRight() {
        return hitbox.getRight();
    }
    public float getBottom() {
        return hitbox.getBottom();
    }
    public float getCenterX(){
        return hitbox.getCenterX();
    }
    public float getCenterY(){
        return hitbox.getCenterY();
    }
    public float getWidth(){
        return hitbox.getWidth();
    }
    public float getHeight(){
        return hitbox.getHeight();
    }
    public double getAngleToTopRight() {
        return hitbox.getAngleToTopRight();
    }
    public double getAngleToTopLeft() {
        return hitbox.getAngleToTopLeft();
    }
    public double getAngleToBottomRight() {
        return hitbox.getAngleToBottomRight();
    }
    public double getAngleToBottomLeft() {
        return hitbox.getAngleToBottomLeft();
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        paint.setColor(Color.BLACK);
        canvas.drawRect(getLeft(),getTop(),getLeft()+getWidth(),getTop()+getHeight(),paint);
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.MIDDLEGROUND;
    }


    public void move(float mouseX){
        float dx = mouseX-getCenterX();//distance from center to mouseX- (can be negative)
        if(Math.abs(dx)<maxSpeed){
            hitbox.setCenterX(mouseX);
        }else{
            if(dx<0){//move left at max speed
                hitbox.moveXBy(-maxSpeed);
            }else{//move right at max speed
                hitbox.moveXBy(maxSpeed);
            }
        }
    }

    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        if(inputStatus.isTouching()){
            move(Game.getInstance().getMouseGameX());
        }
    }


    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }



    public void gotHit(int damage) {
        SoundCache.play(SoundCache.paddleBounce);
    }

    @Override
    public void handleCollision(CollisionDetectionObject secondObject) {
        CollisionMediator.handleCollision(this,secondObject);
    }

}

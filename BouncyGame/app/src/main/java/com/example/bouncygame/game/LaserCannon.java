package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.HitboxMediator;
import com.example.bouncygame.hitboxes.RectHitbox;
import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

public class LaserCannon extends GameObject implements DrawableObject,TickObject,SpatialObject{
    private static final float LASER_DIAMETER = 5;
    private RectHitbox hitbox;
    private double direction;//in degrees
    private Laser laser;
    private boolean on;

    public LaserCannon(float xCenter,float yCenter,float width,float height,double direction){
        hitbox = new RectHitbox(xCenter-width/2,yCenter-height/2,width,height);
        this.direction = direction;
        this.laser = new Laser(xCenter,yCenter,LASER_DIAMETER,0f);
        this.on = false;
    }

    public double getDirection() {
        return direction;
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        if(on){
            laser.drawMe(view, paint, canvas);
        }
        canvas.save();
        canvas.translate(hitbox.getCenterX(),hitbox.getCenterY());
        canvas.rotate((float) direction);
        paint.setColor(Color.BLACK);
        canvas.drawRect(-hitbox.getRectRadiusX(),-hitbox.getRectRadiusY(),hitbox.getRectRadiusX(),hitbox.getRectRadiusY(),paint);
        canvas.restore();
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.MIDDLEGROUND;
    }

    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        if(inputStatus.isTouching()){
            if(!on){
                turnOn();
            }
            Game game = Game.getInstance();
            double directionRads = (float) (HitboxMediator.getDirection(hitbox.getCenterX(),hitbox.getCenterY(),game.getMouseGameX(),game.getMouseGameY()));
            direction = directionRads*180/Math.PI;
            laser.setOriginDirection(directionRads);
            laser.setOrigin(hitbox.getCenterX(),hitbox.getCenterY());

            laser.doThisOnGameTick(inputStatus);
        }else{
            if(on){
                turnOff();
            }
        }

    }

    private void turnOff() {
        this.on = false;
    }

    private void turnOn() {
        this.on = true;
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }


}

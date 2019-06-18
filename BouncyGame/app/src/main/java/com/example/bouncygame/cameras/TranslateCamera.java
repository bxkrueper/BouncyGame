package com.example.bouncygame.cameras;

import android.graphics.Canvas;

import com.example.bouncygame.game.CollisionMediator;
import com.example.bouncygame.game.GameObject;
import com.example.bouncygame.game.SpatialObject;
import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.RectHitbox;


public class TranslateCamera extends GameObject implements CameraInterface, SpatialObject {
    private RectHitbox hitbox;

    public TranslateCamera(float left, float yTop, float width, float height){
        hitbox = new RectHitbox(left,yTop,width,height);
    }

    @Override
    public float getLeft() {
        return hitbox.getLeft();
    }
    @Override
    public void setLeft(float xLeft) {
        hitbox.setLeft(xLeft);
    }
    @Override
    public float getTop() {
        return hitbox.getTop();
    }
    @Override
    public void setTop(float yTop) {
        hitbox.setTop(yTop);
    }

    @Override
    public float getRight() {
        return hitbox.getRight();
    }

    @Override
    public float getBottom() {
        return hitbox.getBottom();
    }

    @Override
    public void moveX(float dx){
        hitbox.moveXBy(dx);
    }
    @Override
    public void moveY(float dy){
        hitbox.moveYBy(dy);
    }

    @Override
    public void editCanvasPreDraw(Canvas canvas) {
        canvas.translate(-getLeft(),-getTop());
    }

    @Override
    public float screenToGameX(float screenX) {
        return screenX+ getLeft();
    }

    @Override
    public float screenToGameY(float screenY) {
        return screenY+ getTop();
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }


}

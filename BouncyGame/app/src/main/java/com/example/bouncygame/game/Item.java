package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.example.bouncygame.hitboxes.CircleHitbox;
import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.views.GameView;

public abstract class Item extends GameObject implements CollisionDetectionStatic, DrawableObject,DoSomethingOnAdd{

    protected CircleHitbox hitbox;
    protected Drawable picture;
    private static int width;
    private static int height;


    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Item.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Item.height = height;
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        canvas.save();//////only save once?
        canvas.translate(hitbox.getLeft(),hitbox.getTop());
        picture.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.MIDDLEGROUND;
    }


    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public void doOnAdd() {
        Game.getInstance().addToDeleteOnWinList(this);
    }



    public abstract void collected(BouncyBall ball);

    public void setCenterX(float centerX) {
        hitbox.setxPosition(centerX);
    }

    public void setCenterY(float centerY){
        hitbox.setyPosition(centerY);
    }

    @Override
    public void handleCollision(CollisionDetectionObject secondObject) {
        CollisionMediator.handleCollision(this,secondObject);
    }

}

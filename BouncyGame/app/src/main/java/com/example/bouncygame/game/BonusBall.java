package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.bouncygame.hitboxes.CircleHitbox;
import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.views.GameView;

public class BonusBall extends BouncyBall{
    private static final String CLASS_NAME = "MainBall";

    public static final int DAMAGE = 15;
    public static final float RADIUS = 20;

    public BonusBall(float xStart, float yStart, float speed, double initialDirection){
        super(xStart,yStart,speed,initialDirection,RADIUS,DAMAGE);
        ImageCache.bonusSpikeBall.setBounds( (int) -RADIUS-2,(int) -RADIUS-2,(int) RADIUS+2,(int) RADIUS+2);//////////seting every time it spawns
    }
    public BonusBall(CircleHitbox hitbox, float speed, double initialDirection){
        super(hitbox,speed,initialDirection,DAMAGE);
        ImageCache.bonusSpikeBall.setBounds( (int) -RADIUS-2,(int) -RADIUS-2,(int) RADIUS+2,(int) RADIUS+2);//////////seting every time it spawns
    }

    @Override
    public void doOnAdd() {
        super.doOnAdd();
        Game.getInstance().addToDeleteOnWinList(this);
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        canvas.save();
        canvas.translate(getHitbox().getCenterX(), getHitbox().getCenterY());
        canvas.rotate(getRotation());
        ImageCache.bonusSpikeBall.draw(canvas);
        canvas.restore();
    }



}

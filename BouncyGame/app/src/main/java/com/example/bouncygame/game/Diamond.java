package com.example.bouncygame.game;

import com.example.bouncygame.hitboxes.CircleHitbox;
import com.example.bouncygame.images.ImageCache;

public class Diamond extends Item {

    private static final double ANGLE = Math.PI/3;

    public Diamond(float xCenter, float yCenter) {
        picture = ImageCache.diamond;
        this.hitbox = new CircleHitbox(xCenter,yCenter,Item.getWidth()/2);
    }

    @Override
    public void collected(BouncyBall ball) {
        spawnBonusBalls(ball);
        delete();
    }

    private void spawnBonusBalls(BouncyBall ball) {
        double direction = ball.calculateDirection();
        double distance = ball.getRadius() + BonusBall.RADIUS + 3;
        Game.getInstance().add(new BonusBall((float)(ball.getXPosition() + distance*Math.cos(direction+ANGLE)),(float)(ball.getYPosition() + distance*Math.sin(direction+ANGLE)),ball.getSpeed(),ball.getDirection()+ANGLE));
        Game.getInstance().add(new BonusBall((float)(ball.getXPosition() + distance*Math.cos(direction-ANGLE)),(float)(ball.getYPosition() + distance*Math.sin(direction-ANGLE)),ball.getSpeed(),ball.getDirection()-ANGLE));
    }



}

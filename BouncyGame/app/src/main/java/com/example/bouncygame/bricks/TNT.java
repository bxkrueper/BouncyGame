package com.example.bouncygame.bricks;

import android.graphics.drawable.Drawable;

import com.example.bouncygame.game.Explosion;
import com.example.bouncygame.game.Game;
import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.sounds.SoundCache;

public class TNT extends Brick {

    private static int blockRadius = 1;//0: small explosion on itself. 1: covers 3X3 block area  2:5X5...
    private static int explosionDamage = 60;

    public TNT(int row, int column, Grid grid) {
        super(row, column, grid);
    }

    @Override
    public int getMaxHitPoints() {
        return 15;
    }

    @Override
    public Drawable getPicture() {
        return ImageCache.tnt;
    }

    @Override
    public int getScoreForBreaking() {
        return 15;
    }

    @Override
    public int getSoundOnHit() {
        return SoundCache.dirtBreak;
    }

    @Override
    public int getSoundOnBreak() {
        return SoundCache.explosion;
    }

    @Override
    public void gotHit(int damage, float xVelocity, float yVelocity) {
        recieveDamage(damage,xVelocity,yVelocity);
    }

    @Override
    protected void doOnDeath() {
        float width = (1+(2*blockRadius))* getHitbox().getWidth();
        float height = (1+(2*blockRadius))* getHitbox().getHeight();
        Game.getInstance().add(new Explosion(width,height,explosionDamage, getHitbox().getCenterX(), getHitbox().getCenterY()));
    }


}

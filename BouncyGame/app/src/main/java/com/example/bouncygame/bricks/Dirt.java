package com.example.bouncygame.bricks;

import android.graphics.drawable.Drawable;

import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.sounds.SoundCache;

public class Dirt extends Brick {
    public Dirt(int row, int column, Grid grid) {
        super(row, column, grid);
    }

    @Override
    public int getMaxHitPoints() {
        return 20;
    }

    @Override
    public Drawable getPicture() {
        return ImageCache.dirt;
    }

    @Override
    public int getScoreForBreaking() {
        return 20;
    }

    @Override
    public int getSoundOnHit() {
        return SoundCache.dirtBreak;
    }

    @Override
    public int getSoundOnBreak() {
        return SoundCache.dirtBreak;
    }

    @Override
    public void gotHit(int damage, float xVelocity, float yVelocity) {
        recieveDamage(damage,xVelocity,yVelocity);
    }

    @Override
    protected void doOnDeath() {
        spawnBrickParticals();
    }
}

package com.example.bouncygame.bricks;

import android.graphics.drawable.Drawable;

import com.example.bouncygame.game.Game;
import com.example.bouncygame.game.IronIngot;
import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.sounds.SoundCache;

public class Glass extends Brick {
    public Glass(int row, int column, Grid grid) {
        super(row, column, grid);
    }

    @Override
    public int getMaxHitPoints() {
        return 10;
    }

    @Override
    public Drawable getPicture() {
        return ImageCache.glass;
    }

    @Override
    public int getScoreForBreaking() {
        return 10;
    }

    @Override
    public int getSoundOnHit() {
        return SoundCache.glassBreak;
    }

    @Override
    public int getSoundOnBreak() {
        return SoundCache.glassBreak;
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

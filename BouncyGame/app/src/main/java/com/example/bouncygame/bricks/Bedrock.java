package com.example.bouncygame.bricks;

import android.graphics.drawable.Drawable;

import com.example.bouncygame.game.CollisionDetectionObject;
import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.sounds.SoundCache;

public class Bedrock extends Brick{


    public Bedrock(int row, int column, Grid grid) {
        super(row, column, grid);
    }

    @Override
    public int getMaxHitPoints() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Drawable getPicture() {
        return ImageCache.bedrock;
    }

    @Override
    public int getScoreForBreaking() {
        return 0;
    }

    @Override
    public int getSoundOnHit() {
        return SoundCache.stoneBreak;
    }

    @Override
    public int getSoundOnBreak() {
        return SoundCache.stoneBreak;
    }

    @Override
    public void gotHit(int damage, float xVelocity, float yVelocity) {
        playGotHitSound();
    }

    @Override
    protected void doOnDeath() {
        //this brick is not supposed to be destroyed
    }



}

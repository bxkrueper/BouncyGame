package com.example.bouncygame.bricks;

import android.graphics.drawable.Drawable;

import com.example.bouncygame.game.Diamond;
import com.example.bouncygame.game.Game;
import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.sounds.SoundCache;

public class DiamondOre extends Brick {
    public DiamondOre(int row, int column, Grid grid) {
        super(row, column, grid);
    }

    @Override
    public int getMaxHitPoints() {
        return 150;
    }

    @Override
    public Drawable getPicture() {
        return ImageCache.diamond_ore;
    }

    @Override
    public int getScoreForBreaking() {
        return 150;
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
        recieveDamage(damage,xVelocity,yVelocity);
    }

    @Override
    protected void doOnDeath() {
        Game.getInstance().add(new Diamond(getHitbox().getCenterX(), getHitbox().getCenterY()));
        spawnBrickParticals();
    }
}

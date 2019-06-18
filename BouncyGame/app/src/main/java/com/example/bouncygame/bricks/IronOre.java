package com.example.bouncygame.bricks;

import android.graphics.drawable.Drawable;

import com.example.bouncygame.game.Game;
import com.example.bouncygame.game.IronIngot;
import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.sounds.SoundCache;

public class IronOre extends Brick {
    public IronOre(int row, int column, Grid grid) {
        super(row, column, grid);
    }

    @Override
    public int getMaxHitPoints() {
        return 120;
    }

    @Override
    public Drawable getPicture() {
        return ImageCache.iron_ore;
    }

    @Override
    public int getScoreForBreaking() {
        return 120;
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
        Game.getInstance().add(new IronIngot(getHitbox().getCenterX(), getHitbox().getCenterY()));
        spawnBrickParticals();
    }
}

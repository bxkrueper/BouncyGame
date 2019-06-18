package com.example.bouncygame.bricks;

import android.graphics.drawable.Drawable;

import com.example.bouncygame.game.CollisionDetectionObject;
import com.example.bouncygame.game.CollisionMediator;
import com.example.bouncygame.game.Game;
import com.example.bouncygame.game.TextParticle;
import com.example.bouncygame.hitboxes.RectHitbox;
import com.example.bouncygame.sounds.SoundCache;



public abstract class Brick implements CollisionDetectionObject {
    private static final String CLASS_NAME = "Brick";

    private static int damageTextSize;

    private int hitPoints;
    private float recentBallHitXVelocity;
    private float recentBallHitYVelocity;
    private final Grid grid;
    private final int row;
    private final int column;
    private final RectHitbox hitbox;

    public Brick(int row, int column, Grid grid) {
        this.grid = grid;
        this.hitPoints = getMaxHitPoints();
        this.row = row;
        this.column = column;
        this.hitbox = new RectHitbox(grid.getHitbox().columnToX(column),grid.getHitbox().rowToY(row),grid.getHitbox().getWidthOfBlocks(),grid.getHitbox().getHeightOfBlocks());
    }

    public static void setDamageTextSize(int damageTextSize) {
        Brick.damageTextSize = damageTextSize;
    }

    public RectHitbox getHitbox(){
        return hitbox;
    }

    //blocks are not part of CollisionDetection's methods, so it calls a collision between its grid and the object instead
    @Override
    public final void handleCollision(CollisionDetectionObject secondObject) {
        CollisionMediator.handleCollision(grid,secondObject);
    }



    public float getRecentBallHitXVelocity() {
        return recentBallHitXVelocity;
    }

    public void setRecentBallHitXVelocity(float recentBallHitXVelocity) {
        this.recentBallHitXVelocity = recentBallHitXVelocity;
    }

    public float getRecentBallHitYVelocity() {
        return recentBallHitYVelocity;
    }

    public void setRecentBallHitYVelocity(float recentBallHitYVelocity) {
        this.recentBallHitYVelocity = recentBallHitYVelocity;
    }



    public abstract int getMaxHitPoints();
    public abstract Drawable getPicture();
    public abstract int getScoreForBreaking();
    public abstract int getSoundOnHit();
    public abstract int getSoundOnBreak();
    public abstract void gotHit(int damage,float xVelocity,float yVelocity);
    protected abstract void doOnDeath();


    public int getHitPoints() {
        return hitPoints;
    }


    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    //returns how many hp was actually taken
    private int subtractHitPoints(int damage){
        if(damage<hitPoints){
            hitPoints-=damage;
            return damage;
        }else{
            int damageDelt = hitPoints;
            hitPoints = 0;
            return damageDelt;
        }
    }

    public boolean isDead(){
        return hitPoints <= 0;
    }




    //returns whether it was destroyed during this method
    protected boolean recieveDamage(int damage,float xVelocity,float yVelocity) {//apply damage
        if(getHitPoints()==0){//if it is already dead, do nothing
            return false;
        }

        setRecentBallHitXVelocity(xVelocity);
        setRecentBallHitYVelocity(yVelocity);


        int actualDamage = subtractHitPoints(damage);
        spawnDamageParticle(actualDamage);
        if(getHitPoints()==0){//if it died to this damage
            destroy();
            return true;
        }else{
            playGotHitSound();
            return false;
        }
    }

    public void spawnDamageParticle(int damageToShow){
        Game.getInstance().add(new TextParticle(Integer.toString(damageToShow),hitbox.getCenterX()-hitbox.getRectDiameterX()/4,hitbox.getCenterY(),damageTextSize));
    }

    public void spawnBrickParticals(){
        Game.getInstance().add(new BrickParticles(getPicture(), getHitbox().getLeft(), getHitbox().getTop(), getHitbox().getWidth(), getHitbox().getHeight(),getRecentBallHitXVelocity()*0.75f,getRecentBallHitYVelocity()*0.75f));
    }

    //called when the brick is destroyed from in game activies (runs out of health)
    public void destroy() {
        Game.getInstance().recieveEvent(Game.GameEvent.ADD_TO_SCORE,getScoreForBreaking());
        grid.deleteBrick(row,column);
        playBreakSound();
        doOnDeath();
    }


    public void playGotHitSound(){
        SoundCache.play(getSoundOnHit(),.3f);
    }
    public void playBreakSound(){
        SoundCache.play(getSoundOnBreak());
    }
}

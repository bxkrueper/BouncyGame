package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bouncygame.hitboxes.CircleHitbox;
import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.HitboxMediator;
import com.example.bouncygame.hitboxes.NullHitbox;
import com.example.bouncygame.hitboxes.ToggleHitbox;
import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

public class Finger extends GameObject implements DrawableObject,CollisionDetectionFree,TickObject{

    private ToggleHitbox fingerPositionHitbox;
    private BonusBall phantomBall;//for calculating collisions with bouncy balls. this object is never added to the game and shares its hitbox with fingerPositionHitbox
    private float prevX,prevY;

    public Finger(){
        this.fingerPositionHitbox = new ToggleHitbox(new CircleHitbox(0,0,60));
        this.phantomBall = new BonusBall((CircleHitbox) fingerPositionHitbox.getInnerHitbox(),0f,0f);
    }

    public BouncyBall getPhantomBall(){
        return phantomBall;
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        if(fingerPositionHitbox.isActive()){
            paint.setColor(Color.DKGRAY);
            canvas.drawCircle(fingerPositionHitbox.getCenterX(), fingerPositionHitbox.getCenterY(), fingerPositionHitbox.getRectRadiusX(),paint);
        }
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.FOREGROUND;
    }

    @Override
    public Hitbox getHitbox() {
        return fingerPositionHitbox;
    }

    @Override
    public void handleCollision(CollisionDetectionObject secondObject) {
        CollisionMediator.handleCollision(this,secondObject);
    }

    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        Game game = Game.getInstance();
        if(inputStatus.isTouching()){//if finger is touching
            if(fingerPositionHitbox.isActive()){//update position
                setCenter(game.getMouseGameX(),game.getMouseGameY());
                phantomBall.setXVelocity(fingerPositionHitbox.getCenterX()-prevX);
                phantomBall.setYVelocity(fingerPositionHitbox.getCenterY()-prevY);
            }else{
                //need to get back in the game. update hitbox
                setDown(true);
                setCenter(game.getMouseGameX(),game.getMouseGameY());
                phantomBall.setXVelocity(0f);
                phantomBall.setYVelocity(0f);
            }
        }else{//finger is not touching
            if(fingerPositionHitbox.isActive()){//it is supposed to be up, so the hitbox is out of the playing pane and can't be interacted with
                setDown(false);
            }else{
                //no action required. object is just waiting for finger to get back on
            }
        }
    }

    public boolean isDown() {
        return fingerPositionHitbox.isActive();
    }

    public void setDown(boolean down) {
        fingerPositionHitbox.setActive(down);
    }
    public void setCenter(float x,float y){
        prevX = fingerPositionHitbox.getCenterX();
        prevY = fingerPositionHitbox.getCenterY();
        fingerPositionHitbox.setCenterX(x);
        fingerPositionHitbox.setCenterY(y);
    }
}

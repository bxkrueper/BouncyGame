package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

import com.example.bouncygame.R;
import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.RectHitbox;
import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

public class SwitchButton extends GameObject implements DrawableObject,SpatialObject,TickObject{

    private Hitbox hitbox;
    private boolean isBeingTouched;
    private int color;

    public SwitchButton(float left, float top, float width, float height){
        this.hitbox = new RectHitbox(left,top,width,height);
        isBeingTouched = false;
        this.color = Color.rgb(255,0,0);
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        paint.setColor(color);
        canvas.drawRect(hitbox.getLeft(),hitbox.getTop(),hitbox.getRight(),hitbox.getBottom(),paint);
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.INTERFACE;
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    //uses screen coordinates, not game coordinates
    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        if(isBeingTouched){
            if(!(inputStatus.isTouching()&&hitbox.containsPoint(inputStatus.getCurrentMouseX(),inputStatus.getCurrentMouseY()))){//if it is actually not being touched anymore
                isBeingTouched = false;
            }
        }else{//not touched last tick
            if(inputStatus.isTouching()&&hitbox.containsPoint(inputStatus.getCurrentMouseX(),inputStatus.getCurrentMouseY())){//it is touching as of this tick
                isBeingTouched = true;
                Game.getInstance().recieveEvent(Game.GameEvent.SWITCH_WEAPON,null);
            }
        }
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

package com.example.bouncygame.game;

import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.NonHitBox;
import com.example.bouncygame.hitboxes.RectHitbox;
import com.example.bouncygame.views.InputStatus;

import java.util.List;

//checks the free objects that have requested to be informed that they are out of bounds every tick
public class OutOfBoundsInformer extends GameObject implements TickObject, SpatialObject{
    private static final String CLASS_NAME = "OutOfBoundsInformer";
    public static final int TOP = 0;
    public static final int LEFT = 1;
    public static final int BOTTOM = 2;
    public static final int RIGHT = 3;

    private NonHitBox nonHitBox;

    public OutOfBoundsInformer(float left, float top, float width, float height){
        this.nonHitBox = new NonHitBox(new RectHitbox(left,top,width,height));
    }

    @Override
    public void doOnGameTick(InputStatus inputStatus) {
        List<CollisionDetectionFree> list = Game.getInstance().getCheckForOutOfBoundsList();
        for(int i=0;i<list.size();i++){
            CollisionDetectionFree toCheck = list.get(i);
            if(toCheck.getHitbox().intersects(nonHitBox)){
                ((GameObject) toCheck).recieveMessage(Game.GameEvent.OBJECT_OUT_OF_BOUNDS);
            }
        }
    }


    @Override
    public Hitbox getHitbox() {
        return nonHitBox;
    }
}

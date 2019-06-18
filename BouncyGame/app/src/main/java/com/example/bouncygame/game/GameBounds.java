package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bouncygame.hitboxes.CompositeHitbox;
import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.RectHitbox;
import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

public class GameBounds extends GameObject implements DoSomethingOnAdd, SpatialObject{
    private static final String CLASS_NAME = "GameBounds";
    public static final float EXTRA_SPACE = 300;//how much each hitbox extends out

    private GameSide leftSide;
    private GameSide topSide;
    private GameSide rightSide;
    private GameSide bottomSide;

    private Hitbox compositeHitBox;


    //left,top is coordinates of top left of the screen
    public GameBounds(float left, float top,float right, float bottom, boolean includeTop, boolean includeLeft, boolean includeBottom, boolean includeRight){
        float width = right-left;
        float height = bottom-top;


        if(includeTop){
            topSide = new GameSide(new RectHitbox(left-EXTRA_SPACE,top-EXTRA_SPACE,width+EXTRA_SPACE*2,EXTRA_SPACE));//top
        }

        if(includeLeft){
            leftSide = new GameSide(new RectHitbox(left-EXTRA_SPACE,top-EXTRA_SPACE,EXTRA_SPACE,height+EXTRA_SPACE*2));//left
        }

        if(includeBottom){
            bottomSide = new GameSide(new RectHitbox(left-EXTRA_SPACE,bottom,width+EXTRA_SPACE*2,EXTRA_SPACE));//bottom
        }

        if(includeRight){
            rightSide = new GameSide(new RectHitbox(right,top-EXTRA_SPACE,EXTRA_SPACE,height+EXTRA_SPACE*2));//right
        }

        Hitbox[] hitboxes = new Hitbox[4];
        if(leftSide!=null) hitboxes[0] = leftSide.getHitbox();
        if(topSide!=null) hitboxes[1] = topSide.getHitbox();
        if(rightSide!=null) hitboxes[2] = rightSide.getHitbox();
        if(bottomSide!=null) hitboxes[3] = bottomSide.getHitbox();
        this.compositeHitBox = new CompositeHitbox(hitboxes);
    }


    @Override
    public void doOnAdd() {
        if(leftSide!=null) addGameObject(leftSide);
        if(topSide!=null) addGameObject(topSide);
        if(rightSide!=null) addGameObject(rightSide);
        if(bottomSide!=null) addGameObject(bottomSide);
    }

    public void destroy(){
        if(leftSide!=null) leftSide.delete();
        if(topSide!=null) topSide.delete();
        if(rightSide!=null) rightSide.delete();
        if(bottomSide!=null) bottomSide.delete();
    }

    @Override
    public Hitbox getHitbox() {
        return compositeHitBox;
    }
}

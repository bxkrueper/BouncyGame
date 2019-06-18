package com.example.bouncygame.game;

import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.RectHitbox;

public class GameSide extends GameObject implements CollisionDetectionStatic{
    private static final String CLASS_NAME = "GameSide";

    private RectHitbox hitbox;


    public GameSide(RectHitbox hitbox){
        this.hitbox = hitbox;
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public void handleCollision(CollisionDetectionObject secondObject) {
        CollisionMediator.handleCollision(this,secondObject);
    }

}

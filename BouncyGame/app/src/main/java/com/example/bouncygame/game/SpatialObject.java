package com.example.bouncygame.game;

import com.example.bouncygame.hitboxes.Hitbox;

//game objects that implement this and not free body are considered static and are not checked for collision with other static objects
public interface SpatialObject {
    Hitbox getHitbox();
}

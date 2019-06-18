package com.example.bouncygame.game;

//methods calling ray cast should pass an instance of this so they can specify which collision objects they care about when casting their ray
public interface RayCastFilter {
    boolean checkCollisionWithObject(CollisionDetectionObject collisionDetectionObject);
}

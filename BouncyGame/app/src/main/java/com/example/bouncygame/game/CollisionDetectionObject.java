package com.example.bouncygame.game;

//the game checks their hitboxes for collision and then tells them to do handle the collision with any other objects if they collide
public interface CollisionDetectionObject extends SpatialObject{
    void handleCollision(CollisionDetectionObject secondObject);
}

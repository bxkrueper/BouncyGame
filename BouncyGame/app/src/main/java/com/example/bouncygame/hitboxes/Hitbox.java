package com.example.bouncygame.hitboxes;

import com.example.bouncygame.game.SpatialObject;

public interface Hitbox{
    boolean containsPoint(float x, float y);
    boolean intersects(Hitbox otherHitbox);
    //used for ray casting
    //puts the closest collision coordinates in the given array if there is a collision. returns whether there was a collision
    boolean rayCast(LineHitbox ray, double[] collisionCoordinates);

    float getCenterX();//returns the center of the enclosing rectangle
    float getCenterY();

    float getRectDiameterX();//returns the distance between the left most and right most point
    float getRectDiameterY();//returns the distance between the top most and bottom most point
    float getRectRadiusX();//returns the distance between the center and the right or left most point
    float getRectRadiusY();//returns the distance between the center and the top and bottom most points

    float getLeft();//returns the left most point on the hitbox
    float getRight();
    float getTop();
    float getBottom();


    //sets the true position so that the center is at the new position
    void setCenterX(float newX);
    void setCenterY(float newY);
    void setLeft(float newLeft);
    void setTop(float newTop);
    void setRight(float newRight);
    void setBottom(float newBottom);

    void moveXBy(float dX);
    void moveYBy(float dY);



}

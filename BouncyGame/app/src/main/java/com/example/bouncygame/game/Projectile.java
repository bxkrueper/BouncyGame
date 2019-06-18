package com.example.bouncygame.game;

public interface Projectile extends SpatialObject{
    float getXVelocity();
    float getYVelocity();
    void setXVelocity(float newXVelocity);
    void setYVelocity(float newYVelocity);

    float getSpeed();
    double getDirection();
    void setSpeed(float newSpeed);
    void setDirection(double newDirection);

    void doVelocity();
    void moveForward(float distance);//distance can be negative
}

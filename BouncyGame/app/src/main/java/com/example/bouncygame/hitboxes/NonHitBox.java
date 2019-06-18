package com.example.bouncygame.hitboxes;

import com.example.bouncygame.game.SpatialObject;

import java.util.HashSet;
import java.util.Set;

//wrapper that checks for an object NOT hitting the assosiated hitbox
public class NonHitBox extends WrapperHitbox{


    public NonHitBox(Hitbox hitbox){
        super(hitbox);
    }

    @Override
    public boolean containsPoint(float x, float y) {
        return !getInnerHitbox().containsPoint(x,y);
    }

    @Override
    public boolean intersects(Hitbox otherHitbox) {
        return !getInnerHitbox().intersects(otherHitbox);
    }

    @Override
    public boolean rayCast(LineHitbox ray, double[] collisionCoordinates) {
        //if the source of the ray is not in the inner hitbox, then the ray is immediatly hitting the non hitbox. put point 1's coordinates in te array
        if(containsPoint(ray.getX1(),ray.getY1())){
            collisionCoordinates[0] = ray.getX1();
            collisionCoordinates[1] = ray.getY1();
            return true;
        }
        return RayCastMediator.rayCast(getInnerHitbox(),ray,collisionCoordinates);/////////???
    }
}

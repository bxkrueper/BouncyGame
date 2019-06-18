package com.example.bouncygame.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollisionPairTracker {
    private static final String CLASS_NAME = "CollisionPairTracker";

    private Map<CollisionDetectionObject,Set<CollisionDetectionObject>> collisionSet;
    private Map<CollisionDetectionObject,Set<CollisionDetectionObject>> collisionSetLastTick;

    public CollisionPairTracker(){
        collisionSet = new HashMap<>();
        collisionSetLastTick = new HashMap<>();
    }

    //only adds one pair. collidedLastTick checks both ways
    public void addCollisionPairThisTick(CollisionDetectionObject firstObject, CollisionDetectionObject secondObject) {
        Set<CollisionDetectionObject> firstObjectsSet = collisionSet.get(firstObject);
        if(firstObjectsSet==null){
            firstObjectsSet = new HashSet<>();
            collisionSet.put(firstObject,firstObjectsSet);
        }

        firstObjectsSet.add(secondObject);
    }

    public boolean collidedLastTick(CollisionDetectionObject o1, CollisionDetectionObject o2){
        Set<CollisionDetectionObject> o1sCollisionSet = collisionSetLastTick.get(o1);
        if(o1sCollisionSet==null){

        }else{
            if(o1sCollisionSet.contains(o2)){
                return true;
            }
        }

        //try other combination
        Set<CollisionDetectionObject> o2sCollisionSet = collisionSetLastTick.get(o2);
        if(o2sCollisionSet==null){
            return false;
        }else{
            return o2sCollisionSet.contains(o1);
        }
    }


    public void nextCollisionTick(){
        collisionSetLastTick.clear();

        Map<CollisionDetectionObject,Set<CollisionDetectionObject>> temp = collisionSet;
        collisionSet = collisionSetLastTick;
        collisionSetLastTick = temp;
    }
}

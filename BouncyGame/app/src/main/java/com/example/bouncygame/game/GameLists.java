package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.bouncygame.hitboxes.LineHitbox;
import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

import java.util.ArrayList;
import java.util.List;

public class GameLists {
    private static final String CLASS_NAME = "GameLists";
    private List<CollisionDetectionObject> allCollisionObjectList;
    private List<CollisionDetectionFree> freeBodyObjectList;
    private List<CollisionDetectionStatic> staticBodyObjectList;

    //objects are actually added and deleted at the end of each game tick
    private List<GameObject> toAddList;
    private List<GameObject> toDeleteList;

    private DrawManager drawManager;
    private List<TickObject> tickObjectList;
    private List<InterfaceObject> interfaceObjectList;

    public GameLists(){
        this.allCollisionObjectList = new ArrayList<>();//use array lists for speed
        this.freeBodyObjectList = new ArrayList<>();
        this.staticBodyObjectList = new ArrayList<>();
        this.drawManager = new DrawManager();
        this.tickObjectList = new ArrayList<>();
        this.toAddList = new ArrayList<>();
        this.toDeleteList = new ArrayList<>();
        this.interfaceObjectList = new ArrayList<>();
    }

    public List<CollisionDetectionObject> getAllCollisionObjectList() {
        return allCollisionObjectList;
    }

    public List<CollisionDetectionFree> getFreeBodyObjectList() {
        return freeBodyObjectList;
    }

    public List<CollisionDetectionStatic> getStaticBodyObjectList() {
        return staticBodyObjectList;
    }

    public List<GameObject> getToAddList() {
        return toAddList;
    }

    public List<GameObject> getToDeleteList() {
        return toDeleteList;
    }

    public DrawManager getDrawManager() {
        return drawManager;
    }

    public List<TickObject> getTickObjectList() {
        return tickObjectList;
    }

    public List<InterfaceObject> getInterfaceObjectList() {
        return interfaceObjectList;
    }

    public void addToAddList(GameObject gameObject) {
        toAddList.add(gameObject);
    }

    public void addToDeleteList(GameObject gameObject) {
        toDeleteList.add(gameObject);
    }



    //adds the objects in toAddList to all appropriate lists, and calls doOnAdd for them
    public void addFromToAdd(){
        for(int i=0;i<toAddList.size();i++){
            GameObject gameObject = toAddList.get(i);
            if(gameObject instanceof CollisionDetectionObject){
                allCollisionObjectList.add((CollisionDetectionObject) gameObject);
                if(gameObject instanceof CollisionDetectionFree){
                    freeBodyObjectList.add((CollisionDetectionFree) gameObject);
                }else if(gameObject instanceof CollisionDetectionStatic){
                    staticBodyObjectList.add((CollisionDetectionStatic) gameObject);
                }
            }

            if(gameObject instanceof DrawableObject){
                drawManager.add((DrawableObject) gameObject);
            }
            if(gameObject instanceof TickObject){
                tickObjectList.add((TickObject) gameObject);
            }
            if(gameObject instanceof DoSomethingOnAdd){
                ((DoSomethingOnAdd) gameObject).doOnAdd();
            }
            Log.d(CLASS_NAME,"sucsessfully added " + gameObject + " to the game");
        }

        toAddList.clear();
    }

    //removes the objects in the delete list from all the game lists they are in. there is no doOnDelete method for them because
    //those objects could have done what they needed to do before deleting themselves
    public void deleteFromToDelete(){
        for(int i=0;i<toDeleteList.size();i++){
            GameObject gameObject = toDeleteList.get(i);
            if(gameObject instanceof CollisionDetectionObject){
                allCollisionObjectList.remove(gameObject);
                if(gameObject instanceof CollisionDetectionFree){
                    freeBodyObjectList.remove(gameObject);

                }else if(gameObject instanceof CollisionDetectionStatic){
                    staticBodyObjectList.remove(gameObject);
                }
            }

            if(gameObject instanceof DrawableObject){
                drawManager.remove((DrawableObject) gameObject);
            }
            if(gameObject instanceof TickObject){
                tickObjectList.remove(gameObject);
            }

            Log.d(CLASS_NAME,"sucsessfully deleted " + gameObject + " from the game");
        }

        toDeleteList.clear();
    }


    public void addInterfaceObject(InterfaceObject interfaceObject){
        interfaceObjectList.add(interfaceObject);
        drawManager.add(interfaceObject);

        if(interfaceObject instanceof TickObject){
            tickObjectList.add((TickObject) interfaceObject);
        }
    }

    public void removeInterfaceObject(InterfaceObject interfaceObject){
        interfaceObjectList.remove(interfaceObject);
        drawManager.remove(interfaceObject);
        if(interfaceObject instanceof TickObject){
            tickObjectList.remove(interfaceObject);
        }
    }



    //called once per tick. checks for collision for all free bodies with all other free bodies and
    //also checks all free bodies with all static objects. static objects are not checked with eachother
    //calls collision mediator for each pair of collisions
    public void collisionDetection() {

        //check all free bodies with each other
        for(int i=0;i<freeBodyObjectList.size();i++){
            CollisionDetectionFree freeBody = freeBodyObjectList.get(i);
            for(int j=i+1;j<freeBodyObjectList.size();j++){
                CollisionDetectionFree freeBody2 = freeBodyObjectList.get(j);

                if(freeBody.getHitbox().intersects(freeBody2.getHitbox())){
                    Log.d(CLASS_NAME, "Free Free: Collision between " + freeBody + " and " + freeBody2);
                    CollisionMediator.addCollisionPairThisTick(freeBody,freeBody2);
                    freeBody.handleCollision(freeBody2);
                }
            }
        }

        //check all free bodies with all static objects (static objects are not checked with each other)
        for(int i=0;i<freeBodyObjectList.size();i++){
            CollisionDetectionFree freeBody = freeBodyObjectList.get(i);
            for(int j=0;j<staticBodyObjectList.size();j++){
                CollisionDetectionStatic staticObject = staticBodyObjectList.get(j);

                if(freeBody.getHitbox().intersects(staticObject.getHitbox())){
                    Log.d(CLASS_NAME, "Free Static: Collision between " + freeBody + " and " + staticObject);
                    CollisionMediator.addCollisionPairThisTick(freeBody,staticObject);
                    freeBody.handleCollision(staticObject);
                }
            }
        }

        //tell collision mediator that a tick has finished so that it can update its lists of what collided last tick
        CollisionMediator.nextCollisionTick();
    }


    //returns the first object the ray collides with that meets the rayCastFilter criteria.
    //fills collisionCoordinates with the collision coordinates if given
    //lineHitbox x1 and y1 are the origin of the ray
    public CollisionDetectionObject rayCast(LineHitbox ray, RayCastFilter rayCastFilter, double[] collisionCoordinates){
        double closestDistance = Double.MAX_VALUE;
        double closestX = Float.MAX_VALUE;
        double closestY = Float.MAX_VALUE;
        CollisionDetectionObject closestObject = null;
        for(int i=0;i<allCollisionObjectList.size();i++){
            CollisionDetectionObject collisionDetectionObject = allCollisionObjectList.get(i);
            if(rayCastFilter.checkCollisionWithObject(collisionDetectionObject)){
                if(collisionDetectionObject.getHitbox().rayCast(ray,collisionCoordinates)){
                    //collisionCoordinatesArray now has this object's collision point if it hit, or it kept its old value if there was no collision
                    double distanceToCurrentIntersection = Math.hypot(ray.getX1()-collisionCoordinates[0],ray.getY1()-collisionCoordinates[1]);
                    if(distanceToCurrentIntersection<closestDistance){//update closest values
                        closestDistance = distanceToCurrentIntersection;
                        closestX = collisionCoordinates[0];
                        closestY = collisionCoordinates[1];
                        closestObject = collisionDetectionObject;
                    }
                }
            }
        }

        //put closest values in collisionCoordinates to be used by the caller
        collisionCoordinates[0] = closestX;
        collisionCoordinates[1] = closestY;
        return closestObject;
    }

    public void gameTick(InputStatus inputStatus) {
        for(int i=0;i<tickObjectList.size();i++){
            tickObjectList.get(i).doOnGameTick(inputStatus);
        }

        collisionDetection();
        deleteFromToDelete();
        addFromToAdd();
    }
}

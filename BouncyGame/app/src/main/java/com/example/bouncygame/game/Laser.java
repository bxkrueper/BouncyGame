package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bouncygame.bricks.Brick;
import com.example.bouncygame.bricks.Glass;
import com.example.bouncygame.bricks.Grid;
import com.example.bouncygame.hitboxes.CircleHitbox;
import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.HitboxMediator;
import com.example.bouncygame.hitboxes.LineHitbox;
import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

public class Laser implements CollisionDetectionFree, Projectile {
    private static final float MAX_DISTANCE = 3000;
    private static final float DISTANCE_PER_RAYCAST = 500;
    private static final int MAX_BOUNCES = 30;
    private static final int DAMAGE = 10;

    private final RayCastFilter rayCastFilter;

    private double originDirection;

    private CircleHitbox tipHitbox;
    private LineHitbox rayCast;
    private float distanceLeft;
    private double tipDirection;
    private double[] collisionCoordinates;

    private float[] bouncePointXs;//bouncePointXs[0] is the origin
    private float[] bouncePointYs;
    private int bounceIndex;//index of most recent bounce


    public Laser(float xCenter,float yCenter,float diameter,double direction){
        tipHitbox = new CircleHitbox(xCenter,yCenter,diameter/2);
        this.bouncePointXs = new float[MAX_BOUNCES];
        this.bouncePointYs = new float[MAX_BOUNCES];
        bouncePointXs[0] = xCenter;//bouncePointX/Y[0] act as the origin and is not updated during path finding algorithm
        bouncePointYs[0] = yCenter;
        this.originDirection = direction;
        this.rayCast = new LineHitbox(0,0,0,0);
        this.distanceLeft = MAX_DISTANCE;
        this.tipDirection = direction;
        this.collisionCoordinates = new double[2];
        this.rayCastFilter = new LaserRayCastFilter(this);

        this.bounceIndex = 0;
    }

    public int getDamage(){
        return DAMAGE;
    }

    public void setOrigin(float x, float y){
        bouncePointXs[0] = x;
        bouncePointYs[0] = y;
    }
    public void setOriginDirection(double newDirection){
        this.originDirection = newDirection;
    }

    public void drawMe(GameView view, Paint paint, Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(tipHitbox.getDiameter());

        //draw laser path
        for(int i=0;i<bounceIndex;i++){
            canvas.drawLine(bouncePointXs[i],bouncePointYs[i],bouncePointXs[i+1],bouncePointYs[i+1],paint);
        }

        canvas.drawCircle(tipHitbox.getCenterX(), tipHitbox.getCenterY(), tipHitbox.getRadius(),paint);
    }


    public void doThisOnGameTick(InputStatus inputStatus) {
        //reset to origin
        tipHitbox.setCenterX(bouncePointXs[0]);
        tipHitbox.setCenterY(bouncePointYs[0]);
        this.distanceLeft = MAX_DISTANCE;
        this.tipDirection = originDirection;
        this.bounceIndex = 0;

        moveTipForwardMindingCollisionAndDistance();
    }

    //moves forward until it hits an obstacle or runs out of distance
    public void moveTipForwardMindingCollisionAndDistance() {
        if(bounceIndex==bouncePointXs.length-1 || distanceLeft==0){
            return;
        }

        //todo: move forward in increments to consider fewer bricks per ray cast
        float goalX = (float) (tipHitbox.getCenterX()+distanceLeft*Math.cos(tipDirection));
        float goalY = (float) (tipHitbox.getCenterY()+distanceLeft*Math.sin(tipDirection));

        rayCast.setX1(tipHitbox.getCenterX());
        rayCast.setY1(tipHitbox.getCenterY());
        rayCast.setX2(goalX);
        rayCast.setY2(goalY);


        CollisionDetectionObject objectItHit = Game.getInstance().rayCast(rayCast,rayCastFilter,collisionCoordinates);

        if(objectItHit==null){
            moveTipMindingDistance(goalX,goalY);
        }else{
            moveTipMindingDistance((float) collisionCoordinates[0],(float) collisionCoordinates[1]);
            CollisionMediator.handleCollision(this,objectItHit);
        }
    }

    //doesn't care about collisions. helper method for moveTipForwardMindingCollisionAndDistance
    private void moveTipMindingDistance(float newX, float newY) {
        float distanceBetweenOldAndNew = (float) Math.hypot(newX-tipHitbox.getCenterX(),newY-tipHitbox.getCenterY());
        if(distanceBetweenOldAndNew<distanceLeft-.001){//.001 to stop floating point errors from making it ray cast again for a trivial distance
            moveTip(newX,newY);
            distanceLeft-=distanceBetweenOldAndNew;
        }else{//only move as much as it can before running out of distance
            moveTip((float) (tipHitbox.getCenterX()+distanceLeft*Math.cos(tipDirection)),(float) (tipHitbox.getCenterY()+distanceLeft*Math.sin(tipDirection)));
            distanceLeft=0f;
        }
    }

    //helper method for moveTipMindingDistance
    private void moveTip(float newX, float newY){
        bounceIndex++;
        tipHitbox.setCenterX(newX);
        tipHitbox.setCenterY(newY);
        bouncePointXs[bounceIndex] = newX;
        bouncePointYs[bounceIndex] = newY;
    }

    @Override
    public Hitbox getHitbox() {
        return tipHitbox;
    }

    @Override
    public void handleCollision(CollisionDetectionObject secondObject) {
//        CollisionMediator.handleCollision(this,secondObject);
    }


    //projectile methods
    @Override
    public float getXVelocity() {
        return (float) Math.cos(tipDirection);
    }

    @Override
    public float getYVelocity() {
        return (float) Math.sin(tipDirection);
    }

    @Override
    public void setXVelocity(float newXVelocity) {
        float yVelocity = getYVelocity();
        this.tipDirection = HitboxMediator.getDirection(0f,0f,newXVelocity,yVelocity);
    }

    @Override
    public void setYVelocity(float newYVelocity) {
        float xVelocity = getXVelocity();
        this.tipDirection = HitboxMediator.getDirection(0f,0f,xVelocity,newYVelocity);
    }

    //velocity is unused: just one
    @Override
    public void doVelocity() {
        moveForward(1);
    }

    @Override
    public float getSpeed() {
        return 1f;
    }

    @Override
    public double getDirection() {
        return tipDirection;
    }

    @Override
    public void moveForward(float distance) {
        this.tipHitbox.moveXBy(getXVelocity());
        this.tipHitbox.moveYBy(getYVelocity());
    }

    @Override
    public void setSpeed(float newSpeed) {
        //speed is unused. constantly at one
    }

    @Override
    public void setDirection(double newDirection) {
        this.tipDirection = newDirection;
    }









    private class LaserRayCastFilter implements RayCastFilter{
        private Laser laser;

        public LaserRayCastFilter(Laser laser){
            this.laser = laser;
        }
        @Override
        public boolean checkCollisionWithObject(CollisionDetectionObject collisionDetectionObject) {
            return (!laser.getHitbox().intersects(collisionDetectionObject.getHitbox()) && (collisionDetectionObject instanceof Grid || collisionDetectionObject instanceof BouncyBall || collisionDetectionObject instanceof Finger || collisionDetectionObject instanceof GameSide));
        }
    }
}

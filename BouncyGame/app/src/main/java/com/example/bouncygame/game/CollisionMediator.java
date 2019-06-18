package com.example.bouncygame.game;

import com.example.bouncygame.bricks.Brick;
import com.example.bouncygame.bricks.Grid;
import com.example.bouncygame.hitboxes.HitboxMediator;
import com.example.bouncygame.hitboxes.RectHitbox;

import java.util.List;

public class CollisionMediator {
    private static final String CLASS_NAME = "CollisionMediator";

    private static final CollisionPairTracker collisionPairTracker = new CollisionPairTracker();

    public static void addCollisionPairThisTick(CollisionDetectionObject firstObject, CollisionDetectionObject secondObject) {
        collisionPairTracker.addCollisionPairThisTick(firstObject,secondObject);
    }
    public static boolean collidedLastTick(CollisionDetectionObject o1, CollisionDetectionObject o2){
        return collisionPairTracker.collidedLastTick(o1,o2);
    }
    public static void nextCollisionTick(){
        collisionPairTracker.nextCollisionTick();
    }



    //double dispatch methdos:     order: BouncyBall, Grid, Paddle, GameBounds, Finger, Item, Explosion
    public static void handleCollision(BouncyBall firstObject, CollisionDetectionObject secondObject) {
        if(secondObject instanceof BouncyBall){
            handleCollision(firstObject,(BouncyBall) secondObject);
        }else{
            secondObject.handleCollision(firstObject);
        }
    }

    public static void handleCollision(Grid firstObject, CollisionDetectionObject secondObject) {
        if(secondObject instanceof BouncyBall){
            handleCollision(firstObject,(BouncyBall) secondObject);
        }else if(secondObject instanceof Grid){
            handleCollision(firstObject,(Grid) secondObject);
        }else{
            secondObject.handleCollision(firstObject);
        }
    }

    public static void handleCollision(Paddle firstObject, CollisionDetectionObject secondObject) {
        if(secondObject instanceof BouncyBall){
            handleCollision(firstObject,(BouncyBall) secondObject);
        }else if(secondObject instanceof Grid){
            handleCollision(firstObject,(Grid) secondObject);
        }else if(secondObject instanceof Paddle){
            handleCollision(firstObject,(Paddle) secondObject);
        }else{
            secondObject.handleCollision(firstObject);
        }
    }

    public static void handleCollision(GameSide firstObject, CollisionDetectionObject secondObject) {
        if(secondObject instanceof BouncyBall){
            handleCollision(firstObject,(BouncyBall) secondObject);
        }else if(secondObject instanceof Grid){
            handleCollision(firstObject,(Grid) secondObject);
        }else if(secondObject instanceof Paddle){
            handleCollision(firstObject,(Paddle) secondObject);
        }else if(secondObject instanceof GameSide){
            handleCollision(firstObject,(GameSide) secondObject);
        }else{
            secondObject.handleCollision(firstObject);
        }
    }

    public static void handleCollision(Finger firstObject, CollisionDetectionObject secondObject) {
        if(secondObject instanceof BouncyBall){
            handleCollision(firstObject,(BouncyBall) secondObject);
        }else if(secondObject instanceof Grid){
            handleCollision(firstObject,(Grid) secondObject);
        }else if(secondObject instanceof Paddle){
            handleCollision(firstObject,(Paddle) secondObject);
        }else if(secondObject instanceof GameSide){
            handleCollision(firstObject,(GameSide) secondObject);
        }else if(secondObject instanceof Finger){
            handleCollision(firstObject,(Finger) secondObject);
        }else{
            secondObject.handleCollision(firstObject);
        }
    }

    public static void handleCollision(Item firstObject, CollisionDetectionObject secondObject) {
        if(secondObject instanceof BouncyBall){
            handleCollision(firstObject,(BouncyBall) secondObject);
        }else if(secondObject instanceof Grid){
            handleCollision(firstObject,(Grid) secondObject);
        }else if(secondObject instanceof Paddle){
            handleCollision(firstObject,(Paddle) secondObject);
        }else if(secondObject instanceof GameSide){
            handleCollision(firstObject,(GameSide) secondObject);
        }else if(secondObject instanceof Finger){
            handleCollision(firstObject,(Finger) secondObject);
        }else if(secondObject instanceof Item){
            handleCollision(firstObject,(Item) secondObject);
        }else{
            secondObject.handleCollision(firstObject);
        }
    }

    public static void handleCollision(Explosion firstObject, CollisionDetectionObject secondObject) {
        if(secondObject instanceof BouncyBall){
            handleCollision(firstObject,(BouncyBall) secondObject);
        }else if(secondObject instanceof Grid){
            handleCollision(firstObject,(Grid) secondObject);
        }else if(secondObject instanceof Paddle){
            handleCollision(firstObject,(Paddle) secondObject);
        }else if(secondObject instanceof GameSide){
            handleCollision(firstObject,(GameSide) secondObject);
        }else if(secondObject instanceof Finger){
            handleCollision(firstObject,(Finger) secondObject);
        }else if(secondObject instanceof Item){
            handleCollision(firstObject,(Item) secondObject);
        }else if(secondObject instanceof Explosion){
            handleCollision(firstObject,(Explosion) secondObject);
        }else{
            secondObject.handleCollision(firstObject);
        }
    }

    public static void handleCollision(Laser firstObject, CollisionDetectionObject secondObject) {
        if(secondObject instanceof BouncyBall){
            handleCollision(firstObject,(BouncyBall) secondObject);
        }else if(secondObject instanceof Grid){
            handleCollision(firstObject,(Grid) secondObject);
        }else if(secondObject instanceof Paddle){
            handleCollision(firstObject,(Paddle) secondObject);
        }else if(secondObject instanceof GameSide){
            handleCollision(firstObject,(GameSide) secondObject);
        }else if(secondObject instanceof Finger){
            handleCollision(firstObject,(Finger) secondObject);
        }else if(secondObject instanceof Item){
            handleCollision(firstObject,(Item) secondObject);
        }else if(secondObject instanceof Explosion){
            handleCollision(firstObject,(Explosion) secondObject);
        }else if(secondObject instanceof Laser){
            handleCollision(firstObject,(Laser) secondObject);
        }else{
            secondObject.handleCollision(firstObject);
        }
    }



    //base handling methods:

    private static void handleCollision(BouncyBall firstObject,BouncyBall secondObject){
        if(collidedLastTick(firstObject,secondObject)){
            return;
        }
        handleCircularCollision(firstObject,secondObject);
    }

    private static void handleCollision(Grid firstObject,BouncyBall secondObject){
        List<Brick> allCollisionsList = firstObject.getAllBricksCollidingWith(secondObject.getHitbox());

        //only collide with the closest brick
        Brick brick = allCollisionsList.get(0);
        if(allCollisionsList.size()>1){//there is more than one brick. Choose the closest
            double closestDistance = Double.MAX_VALUE;
            for(int i=0;i<allCollisionsList.size();i++){
                Brick canidate = allCollisionsList.get(i);
                double canidateDistance = Math.hypot(secondObject.getHitbox().getCenterX()-canidate.getHitbox().getCenterX(),secondObject.getHitbox().getCenterY()-canidate.getHitbox().getCenterY());
                if(canidateDistance<closestDistance){
                    brick = canidate;
                    closestDistance = canidateDistance;
                }
            }
        }

        //variable brick is now the one the ball will collide with
        brick.gotHit((int) (secondObject.getDamage()*secondObject.getSpeed()/20)+1,secondObject.getXVelocity(),secondObject.getYVelocity());
        handleProjectileOffStaticRec(brick.getHitbox(), secondObject);
    }
    private static void handleCollision(Grid firstObject,Grid secondObject){
        //this collision should not happen (two static objects)
    }

    private static void handleCollision(Paddle firstObject,BouncyBall secondObject){
        handleProjectileOffStaticRec((RectHitbox) firstObject.getHitbox(),secondObject);

        //change x velocity of ball slightly depending on where it hits the paddle
        float distanceFromPaddle = secondObject.getXPosition() - firstObject.getCenterX();
        secondObject.setXVelocity(secondObject.getXVelocity() + (distanceFromPaddle * Paddle.X_VEL_CONSTANT));
        secondObject.setRotationSpeed(secondObject.getRotationSpeed()+distanceFromPaddle*0.03f);



        firstObject.gotHit(secondObject.getDamage());
    }
    private static void handleCollision(Paddle firstObject,Grid secondObject){
        //do nothing
    }
    private static void handleCollision(Paddle firstObject,Paddle secondObject){
        //do nothing
    }

    private static void handleCollision(GameSide firstObject,BouncyBall secondObject){
        handleProjectileOffStaticRec((RectHitbox) firstObject.getHitbox(), secondObject);
    }
    private static void handleCollision(GameSide firstObject,Grid secondObject){
        //this collision should not happen (two static objects)
    }
    private static void handleCollision(GameSide firstObject,Paddle secondObject){
        //do nothing
    }
    private static void handleCollision(GameSide firstObject,GameSide secondObject){
        //this collision should not happen (two static objects)
    }

    private static void handleCollision(Finger firstObject,BouncyBall secondObject){
        if(collidedLastTick(firstObject,secondObject)){
            return;
        }

        //make sure the bouncy ball is outside of the finger
        double angleOfCollision = HitboxMediator.getDirection(firstObject.getHitbox(),secondObject.getHitbox());
        float distanceBetweenTouchingBalls = firstObject.getPhantomBall().getRadius()+secondObject.getRadius();
        secondObject.setCenterX(firstObject.getPhantomBall().getXPosition()+distanceBetweenTouchingBalls*(float) Math.cos(angleOfCollision));
        secondObject.setCenterY(firstObject.getPhantomBall().getYPosition()+distanceBetweenTouchingBalls*(float) Math.sin(angleOfCollision));

        handleCircularCollision(firstObject.getPhantomBall(),secondObject);
    }
    private static void handleCollision(Finger firstObject,Grid secondObject){
        List<Brick> allCollisionsList = secondObject.getAllBricksCollidingWith(firstObject.getHitbox());
        for(int i=0;i<allCollisionsList.size();i++){
            Brick brick = allCollisionsList.get(i);
            //add collision pair here because brick is not added to the game object list directly, and is not added in Game.
            addCollisionPairThisTick(firstObject,brick);

            if(collidedLastTick(firstObject,brick)){
                return;
            }else{
                brick.gotHit(100,0,0);
            }

        }
    }
    private static void handleCollision(Finger firstObject,Paddle secondObject){
        //do nothing
    }
    private static void handleCollision(Finger firstObject,GameSide secondObject){
        //do nothing
    }
    private static void handleCollision(Finger firstObject,Finger secondObject){
        //do nothing
    }

    private static void handleCollision(Item firstObject,BouncyBall secondObject){
        firstObject.collected(secondObject);
    }
    private static void handleCollision(Item firstObject,Grid secondObject){
        //this collision should not happen (two static objects)
    }
    private static void handleCollision(Item firstObject,Paddle secondObject){
        //do nothing
    }
    private static void handleCollision(Item firstObject,GameSide secondObject){
        //this collision should not happen (two static objects)
    }
    private static void handleCollision(Item firstObject,Finger secondObject){
        //do nothing
    }
    private static void handleCollision(Item firstObject,Item secondObject){
        //this collision should not happen (two static objects)
    }

    private static void handleCollision(Explosion firstObject,BouncyBall secondObject){
        double angleOfCollision = HitboxMediator.getDirection(firstObject.getHitbox(),secondObject.getHitbox());
        float deltaVx = (float) (firstObject.getDamage()*Math.cos(angleOfCollision));
        float deltaVy = (float) (firstObject.getDamage()*Math.sin(angleOfCollision));
        secondObject.setXVelocity(secondObject.getXVelocity()+deltaVx);
        secondObject.setYVelocity(secondObject.getYVelocity()+deltaVy);
    }
    private static void handleCollision(Explosion firstObject,Grid secondObject){
        List<Brick> allCollisionsList = secondObject.getAllBricksCollidingWith(firstObject.getHitbox());
        for(int i=0;i<allCollisionsList.size();i++){
            Brick brick = allCollisionsList.get(i);
            double angleOfCollision = HitboxMediator.getDirection(firstObject.getHitbox(),brick.getHitbox());
            brick.gotHit(firstObject.getDamage(),(float) (firstObject.getDamage()*Math.cos(angleOfCollision)),(float) (firstObject.getDamage()*Math.sin(angleOfCollision)));
        }
    }
    private static void handleCollision(Explosion firstObject,Paddle secondObject){
        //do nothing
    }
    private static void handleCollision(Explosion firstObject,GameSide secondObject){
        //do nothing
    }
    private static void handleCollision(Explosion firstObject,Finger secondObject){
        //do nothing
    }
    private static void handleCollision(Explosion firstObject,Item secondObject){
        //do nothing
    }
    private static void handleCollision(Explosion firstObject,Explosion secondObject){
        //do nothing
    }


    private static void handleCollision(Laser firstObject,BouncyBall secondObject){
        float dx = firstObject.getXVelocity();
        float dy = firstObject.getYVelocity();
        secondObject.setXVelocity(secondObject.getXVelocity()+dx);
        secondObject.setYVelocity(secondObject.getYVelocity()+dy);
    }
    private static void handleCollision(Laser firstObject,Grid secondObject){
        List<Brick> allCollisionsList = secondObject.getAllBricksCollidingWith(firstObject.getHitbox());
        for(int i=0;i<allCollisionsList.size();i++){
            Brick brick = allCollisionsList.get(i);
            //add collision pair here because brick is not added to the game object list directly, and is not added in Game.
            addCollisionPairThisTick(firstObject,brick);

            brick.gotHit(firstObject.getDamage(),0,0);

        }
    }
    private static void handleCollision(Laser firstObject,Paddle secondObject){
        handleProjectileOffStaticRec((RectHitbox) secondObject.getHitbox(),firstObject);
    }
    private static void handleCollision(Laser firstObject,GameSide secondObject){
//        RectHitbox rectHitbox;
//
//        rectHitbox = secondObject.getHitbox(GameSide.TOP);
//        if(rectHitbox!=null && rectHitbox.getBottom()>firstObject.getHitbox().getTop()){
//            handleProjectileOffStaticRec(rectHitbox,firstObject);
//        }
//        rectHitbox = secondObject.getHitbox(GameSide.LEFT);
//        if(rectHitbox!=null && rectHitbox.getRight()>firstObject.getHitbox().getLeft()){
//            handleProjectileOffStaticRec(rectHitbox,firstObject);
//        }
//        rectHitbox = secondObject.getHitbox(GameSide.BOTTOM);
//        if(rectHitbox!=null && rectHitbox.getTop()<firstObject.getHitbox().getBottom()){
//            handleProjectileOffStaticRec(rectHitbox,firstObject);
//        }
//        rectHitbox = secondObject.getHitbox(GameSide.RIGHT);
//        if(rectHitbox!=null && rectHitbox.getLeft()<firstObject.getHitbox().getRight()){
//            handleProjectileOffStaticRec(rectHitbox,firstObject);
//        }

        handleProjectileOffStaticRec((RectHitbox) secondObject.getHitbox(), firstObject);
        //tell laser to keep going
        firstObject.moveTipForwardMindingCollisionAndDistance();
    }
    private static void handleCollision(Laser firstObject,Finger secondObject){
//        handleCircularCollision(firstObject,secondObject);
    }
    private static void handleCollision(Laser firstObject,Item secondObject){
        //do nothing
    }
    private static void handleCollision(Laser firstObject,Explosion secondObject){
        //do nothing
    }
    private static void handleCollision(Laser firstObject,Laser secondObject){
        //do nothing
    }













    //support methods:


    public static void bounceBallOfPoint(Projectile b, float pointX, float pointY){
        double ballDirection = Math.atan2(b.getYVelocity(),b.getXVelocity());
        double angle = Math.atan2(pointY - b.getHitbox().getCenterY(),pointX - b.getHitbox().getCenterX()) + Math.PI / 2;//////
        double speed = Math.hypot(b.getYVelocity(),b.getXVelocity());
        double collisionAngle = ballDirection - angle;
        double newAngle = ballDirection - 2 * collisionAngle;
        b.setXVelocity((float) (speed * Math.cos(newAngle)));
        b.setYVelocity((float) (speed * Math.sin(newAngle)));
    }

    private static void handleProjectileOffStaticRec(RectHitbox rectHitbox, Projectile go2){
        float ballX = go2.getHitbox().getCenterX();
        float ballY = go2.getHitbox().getCenterY();
        float rectRight = rectHitbox.getRight();
        float rectTop = rectHitbox.getTop();
        float rectBottom = rectHitbox.getBottom();
        float rectLeft = rectHitbox.getLeft();
        float rectCenterX = rectHitbox.getCenterX();
        float rectCenterY = rectHitbox.getCenterY();
        double angleOfCollision = HitboxMediator.getDirection(rectHitbox,go2.getHitbox());

        if(ballX>rectRight && ballY > rectBottom){//bottom right corner
            bounceBallOfPoint(go2,rectCenterX,rectCenterY);
            return;
        }else if(ballX>rectRight && ballY < rectTop){//top right corner
            bounceBallOfPoint(go2,rectCenterX,rectCenterY);
            return;
        }else if(ballX<rectLeft && ballY > rectBottom){//bottom left
            bounceBallOfPoint(go2,rectCenterX,rectCenterY);
            return;
        }else if(ballX<rectLeft && ballY < rectTop){//top left
            bounceBallOfPoint(go2,rectCenterX,rectCenterY);
            return;
        }

        if(angleOfCollision<rectHitbox.getAngleToTopRight() && angleOfCollision>rectHitbox.getAngleToTopLeft()){// circle in top triangle
            go2.setYVelocity(Math.abs(go2.getYVelocity())*-1);
        }else if(angleOfCollision<rectHitbox.getAngleToTopLeft() && angleOfCollision>rectHitbox.getAngleToBottomLeft()){//left triangle
            go2.setXVelocity(Math.abs(go2.getXVelocity())*-1);
        }else if(angleOfCollision<rectHitbox.getAngleToBottomLeft() && angleOfCollision>rectHitbox.getAngleToBottomRight()){//bottom triangle
            go2.setYVelocity(Math.abs(go2.getYVelocity()));
        }else{//right triangle
            go2.setXVelocity(Math.abs(go2.getXVelocity()));
        }
    }




    //assumes hitboxes are circular
    private static void handleCircularCollision(Projectile go1, Projectile go2) {
        double speed1 = go1.getSpeed();
        double speed2 = go2.getSpeed();
        double direction1 = go1.getDirection();
        double direction2 = go2.getDirection();
        double mass1 = go1.getHitbox().getRectRadiusX() * go1.getHitbox().getRectRadiusX()*Math.PI;
        double mass2 = go2.getHitbox().getRectRadiusX()* go2.getHitbox().getRectRadiusX()*Math.PI;
        double p = Math.atan2(go1.getHitbox().getCenterY()-go2.getHitbox().getCenterY(),go1.getHitbox().getCenterX()-go2.getHitbox().getCenterX());

        double u1 = (speed1*Math.cos(direction1-p)*(mass1-mass2)+2*mass2*speed2*Math.cos(direction2-p))/(mass1+mass2);
        double u2 = (speed2*Math.cos(direction2-p)*(mass2-mass1)+2*mass1*speed1*Math.cos(direction1-p))/(mass1+mass2);

        double v1x = u1 * Math.cos(p) + speed1*Math.sin(direction1-p)*Math.cos(p+Math.PI/2);
        double v2x = u2 * Math.cos(p) + speed2*Math.sin(direction2-p)*Math.cos(p+Math.PI/2);
        double v1y = u1 * Math.sin(p) + speed1*Math.sin(direction1-p)*Math.sin(p+Math.PI/2);
        double v2y = u2 * Math.sin(p) + speed2*Math.sin(direction2-p)*Math.sin(p+Math.PI/2);

        go1.setXVelocity((float) v1x);
        go1.setYVelocity((float) v1y);
        go2.setXVelocity((float) v2x);
        go2.setYVelocity((float) v2y);
    }



}

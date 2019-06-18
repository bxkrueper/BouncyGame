package com.example.bouncygame.hitboxes;



public class HitboxMediator {
    private static final String CLASS_NAME = "HitboxMediator";

    private static final double PIo2 = Math.PI / 2;
    private static final double PIt3o2 = Math.PI * 1.5;


    public static boolean intersects(RectHitbox rectHitbox, Hitbox otherHitbox) {
        if (otherHitbox instanceof RectHitbox) {
            return intersects(rectHitbox, (RectHitbox) otherHitbox);
        } else {
            return otherHitbox.intersects(rectHitbox);
        }
    }

    public static boolean intersects(CircleHitbox circleHitbox, Hitbox otherHitbox) {
        if (otherHitbox instanceof RectHitbox) {
            return intersects(circleHitbox, (RectHitbox) otherHitbox);
        } else if (otherHitbox instanceof CircleHitbox) {
            return intersects(circleHitbox, (CircleHitbox) otherHitbox);
        } else {
            return otherHitbox.intersects(circleHitbox);
        }
    }

    public static boolean intersects(LineHitbox lineHitbox, Hitbox otherHitbox) {
        if (otherHitbox instanceof RectHitbox) {
            return intersects(lineHitbox, (RectHitbox) otherHitbox);
        } else if (otherHitbox instanceof CircleHitbox) {
            return intersects(lineHitbox, (CircleHitbox) otherHitbox);
        } else if (otherHitbox instanceof LineHitbox) {
            return intersects(lineHitbox, (LineHitbox) otherHitbox);
        } else {
            return otherHitbox.intersects(lineHitbox);
        }
    }


    private static boolean intersects(RectHitbox r1, RectHitbox r2) {
        return (isInRange(r1.getLeft(), r1.getLeft() + r1.getWidth(), r2.getLeft(), r2.getLeft() + r2.getWidth()) &&
                isInRange(r1.getTop(), r1.getTop() + r1.getHeight(), r2.getTop(), r2.getTop() + r2.getHeight()));
    }

    private static boolean intersects(CircleHitbox circle, RectHitbox rect) {
        //circle intersects side but not corner
        //do this by testing if the center of the circle is in a plus shapped region centered on the rectangle
        //   extension length is the circle radius


        float radius = circle.getRadius();
        //vertical part of plus
        if (rectangleContainsPoint(rect.getLeft(), rect.getRight(), rect.getTop() - radius, rect.getBottom() + radius, circle.getxPosition(), circle.getyPosition())) {
            return true;
        }
        //horizontal part of plus
        if (rectangleContainsPoint(rect.getLeft() - radius, rect.getRight() + radius, rect.getTop(), rect.getBottom(), circle.getxPosition(), circle.getyPosition())) {
            return true;
        }

        //test if each of the rectangle's points is in the circle
        if (circle.containsPoint(rect.getLeft(), rect.getTop())) {
            return true;
        }
        if (circle.containsPoint(rect.getLeft(), rect.getBottom())) {
            return true;
        }
        if (circle.containsPoint(rect.getRight(), rect.getTop())) {
            return true;
        }
        if (circle.containsPoint(rect.getRight(), rect.getBottom())) {
            return true;
        }

        return false;
    }

    private static boolean intersects(CircleHitbox c1, CircleHitbox c2) {
        return Math.hypot(c1.getxPosition() - c2.getxPosition(), c1.getyPosition() - c2.getyPosition()) < c1.getRadius() + c2.getRadius();
    }


    private static boolean intersects(LineHitbox lineHitbox, RectHitbox h2) {
        //use get top because that is the lower coordinate value
        return RayCastMediator.lineRectIntersect(lineHitbox.getX1(), lineHitbox.getY1(), lineHitbox.getX2(), lineHitbox.getY2(), h2.getLeft(), h2.getTop(), h2.getWidth(), h2.getHeight(),null);
    }

    private static boolean intersects(LineHitbox lineHitbox, CircleHitbox h2) {
        return RayCastMediator.lineCircleIntersect(lineHitbox.getX1(), lineHitbox.getY1(), lineHitbox.getX2(), lineHitbox.getY2(), h2.getCenterX(), h2.getCenterY(), h2.getRadius(),null);
    }

    private static boolean intersects(LineHitbox lineHitbox, LineHitbox h2) {
        return RayCastMediator.linesIntersect(lineHitbox.getX1(), lineHitbox.getY1(), lineHitbox.getX2(), lineHitbox.getY2(), h2.getX1(), h2.getY1(), h2.getX2(), h2.getY2(),null);
    }










    //support methods down here

    //returns the direction between the hitboxes' centers (from h1 to h2)
    public static double getDirection(Hitbox h1, Hitbox h2) {
        return getDirection(h1.getCenterX(), h1.getCenterY(), h2.getCenterX(), h2.getCenterY());
    }

    //returns the direction of (x2,y2) from (x,y) in radians from the +x axis (between 0 and 2PI)
    public final static double getDirection(float x1, float y1, float x2, float y2) {
        double xlength = Math.abs(x2 - x1);
        double ylength = Math.abs(y2 - y1);
        if (x2 > x1) {
            if (y2 > y1) {//first quadrant
                return Math.atan(ylength / xlength);
            } else if (y2 == y1) {//+x axis
                return 0.0;
            } else {//y2<y1   forth quadrant
                return Math.atan(xlength / ylength) + Math.PI * 1.5;
            }


        } else if (x2 == x1) {
            if (y2 > y1) {//+y axis
                return Math.PI / 2;
            } else if (y2 == y1) {//same point
                return 0.0;
            } else {//y2<y1   -y axis
                return Math.PI * 1.5;
            }


        } else {//x2<x1
            if (y2 > y1) {//second quadrant
                return Math.atan(xlength / ylength) + Math.PI / 2;
            } else if (y2 == y1) {//-x axis
                return Math.PI;
            } else {//y2<y1    third quadrant
                return Math.atan(ylength / xlength) + Math.PI;
            }
        }
    }

    //    //input and output between 0 and 2PI unless input is NO_COLLISION  if it is just return that
//    public static double oppositeAngleCheckNoCollision(double angle) {
//        if(angle==NO_COLLISION) {
//            return angle;
//        }
//
//        return oppositeAngle(angle);
//    }
    //don't check for NO_COLLISION. assumes angle is between 0 and 2PI
    public static double oppositeAngle(double angle) {
        if (angle > Math.PI) {
            return angle - Math.PI;
        } else {
            return angle + Math.PI;
        }
    }


    //same as RectHitbox method, but we don't want to create new objects in collision detection methods
    private static boolean rectangleContainsPoint(float left, float right, float top, float bottom, float x, float y) {
        return x > left && x < right && y > top && y < bottom;
    }

    private static boolean isInRange(double a1, double a2, double b1, double b2) {
        if (b1 >= a1 && b1 <= a2)
            return true;
        if (b2 >= a1 && b2 <= a2)
            return true;
        if (b1 <= a1 && b2 >= a2)
            return true;

        return false;
    }







}
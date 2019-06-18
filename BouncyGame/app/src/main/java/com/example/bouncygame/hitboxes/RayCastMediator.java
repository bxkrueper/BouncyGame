package com.example.bouncygame.hitboxes;

import android.util.Log;

enum RectRegion{ABOVE_LEFT,ABOVE_CENTER,ABOVE_RIGHT,LEFT_CENTER,INSIDE,RIGHT_CENTER,BELOW_LEFT,BELOW_CENTER,BELOW_RIGHT}

//returns if the ray intersects the hitbox and fills in the closest intersection point to the source of the ray into collisionCoordinates
public class RayCastMediator {
    private static final String CLASS_NAME = "RayCastMediator";

    public static boolean rayCast(Hitbox hitbox,LineHitbox ray, double[] collisionCoordinates) {
        if (hitbox instanceof RectHitbox) {
            return rayCast((RectHitbox) hitbox,ray, collisionCoordinates);
        } else if (hitbox instanceof CircleHitbox) {
            return rayCast((CircleHitbox) hitbox,ray, collisionCoordinates);
        } else if (hitbox instanceof LineHitbox) {
            return rayCast((LineHitbox) hitbox,ray, collisionCoordinates);
        } else {
            Log.d(CLASS_NAME,"unrecognized hitbox in RayCastMediator!");
            return hitbox.rayCast(ray,collisionCoordinates);
        }
    }




    public static boolean rayCast(RectHitbox h2,LineHitbox ray, double[] collisionCoordinates) {
        //use get top because that is the lower coordinate value
        return lineRectIntersect(ray.getX1(), ray.getY1(), ray.getX2(), ray.getY2(), h2.getLeft(), h2.getTop(), h2.getWidth(), h2.getHeight(),collisionCoordinates);
    }

    public static boolean rayCast(CircleHitbox h2,LineHitbox ray, double[] collisionCoordinates) {
        return lineCircleIntersect(ray.getX1(), ray.getY1(), ray.getX2(), ray.getY2(), h2.getCenterX(), h2.getCenterY(), h2.getRadius(),collisionCoordinates);
    }

    public static boolean rayCast(LineHitbox lineHitbox, LineHitbox ray, double[] collisionCoordinates) {
        return linesIntersect(ray.getX1(), ray.getY1(), ray.getX2(), ray.getY2(), lineHitbox.getX1(), lineHitbox.getY1(), lineHitbox.getX2(), lineHitbox.getY2(),collisionCoordinates);
    }












    //returns the region of the point relative to the rectangle.
    //0: above left   1: above   2: above right   3: left   4: inside   5: right   6: below left  7: below   8: below right
    private static RectRegion getRectRegionOfPoint(double pointX, double pointY, double rectLeft, double rectBottom, double rectWidth, double rectHeight) {
        if (pointY > rectBottom + rectHeight) {//point is above the rect
            if (pointX < rectLeft) {//point is left of rect
                return RectRegion.ABOVE_LEFT;
            } else if (pointX > rectLeft + rectWidth) {//point is right of rect
                return RectRegion.ABOVE_RIGHT;
            } else {//point is at same x region of rect
                return RectRegion.ABOVE_CENTER;
            }
        } else if (pointY < rectBottom) {//point is below the rect
            if (pointX < rectLeft) {//point is left of rect
                return RectRegion.BELOW_LEFT;
            } else if (pointX > rectLeft + rectWidth) {//point is right of rect
                return RectRegion.BELOW_RIGHT;
            } else {//point is at same x region of rect
                return RectRegion.BELOW_CENTER;
            }
        } else {//point is at the same y region as the rectangle
            if (pointX < rectLeft) {//point is left of rect
                return RectRegion.LEFT_CENTER;
            } else if (pointX > rectLeft + rectWidth) {//point is right of rect
                return RectRegion.RIGHT_CENTER;
            } else {//point is at same x region of rect
                return RectRegion.INSIDE;//point is inside rectangle
            }
        }
    }

    //Pass arrayToPutAnswer to have the method fill it with the answer ([0] is the x, [1] is the y of the intersection)
    //The closest point to line 1 point 1 is chosen
    //if there is no collision, the array is not edited
    public static boolean lineRectIntersect(double linex1, double liney1, double linex2, double liney2, double rectLeft, double rectBottom, double rectWidth, double rectHeight, double[] arrayToPutAnswer) {
        RectRegion linePoint1Region = getRectRegionOfPoint(linex1, liney1, rectLeft, rectBottom, rectWidth, rectHeight);
        RectRegion linePoint2Region = getRectRegionOfPoint(linex2, liney2, rectLeft, rectBottom, rectWidth, rectHeight);
        double rectRight = rectLeft + rectWidth;
        double rectTop = rectBottom + rectHeight;

        //line 2 and line 3 are sides of the rectangle that may be hit first by the ray. line 3 may not be considered if the linePoint1 is in a corner region
        double line2x1, line2y1, line2x2, line2y2;
        double line3x1 = Double.NaN, line3y1 = Double.NaN, line3x2 = Double.NaN, line3y2 = Double.NaN;//top line
        boolean considerLine3;

        //set line possibility values
        switch (linePoint1Region) {
            case ABOVE_LEFT:
                switch (linePoint2Region) {
                    case ABOVE_LEFT:
                    case ABOVE_CENTER:
                    case ABOVE_RIGHT:
                    case LEFT_CENTER:
                    case BELOW_LEFT:
                        return false;
                    default://ray is intersecting left, top, or none
                        line2x1 = rectLeft;
                        line2y1 = rectBottom;
                        line2x2 = rectLeft;
                        line2y2 = rectTop;//left line
                        line3x1 = rectLeft;
                        line3y1 = rectTop;
                        line3x2 = rectRight;
                        line3y2 = rectTop;//top line
                        considerLine3 = true;
                }
                break;
            case ABOVE_CENTER:
                switch (linePoint2Region) {
                    case ABOVE_LEFT:
                    case ABOVE_CENTER:
                    case ABOVE_RIGHT:
                        return false;
                    default://ray is intersecting top or none
                        line2x1 = rectLeft;
                        line2y1 = rectTop;
                        line2x2 = rectRight;
                        line2y2 = rectTop;//top line
                        considerLine3 = false;
                }
                break;
            case ABOVE_RIGHT:
                switch (linePoint2Region) {
                    case ABOVE_LEFT:
                    case ABOVE_CENTER:
                    case ABOVE_RIGHT:
                    case RIGHT_CENTER:
                    case BELOW_RIGHT:
                        return false;
                    default://ray is intersecting right, top, or none
                        line2x1 = rectRight;
                        line2y1 = rectBottom;
                        line2x2 = rectRight;
                        line2y2 = rectTop;//right line
                        line3x1 = rectLeft;
                        line3y1 = rectTop;
                        line3x2 = rectRight;
                        line3y2 = rectTop;//top line
                        considerLine3 = true;
                }
                break;
            case LEFT_CENTER:
                switch (linePoint2Region) {
                    case ABOVE_LEFT:
                    case LEFT_CENTER:
                    case BELOW_LEFT:
                        return false;
                    default://ray is intersecting left or none
                        line2x1 = rectLeft;
                        line2y1 = rectBottom;
                        line2x2 = rectLeft;
                        line2y2 = rectTop;//left line
                        considerLine3 = false;
                }
                break;
            case INSIDE://ray trace goes nowhere
                if (arrayToPutAnswer != null) {
                    arrayToPutAnswer[0] = linex1;
                    arrayToPutAnswer[1] = liney1;
                }
                return true;
            case RIGHT_CENTER:
                switch (linePoint2Region) {
                    case ABOVE_RIGHT:
                    case RIGHT_CENTER:
                    case BELOW_RIGHT:
                        return false;
                    default://ray is intersecting right or none
                        line2x1 = rectRight;
                        line2y1 = rectBottom;
                        line2x2 = rectRight;
                        line2y2 = rectTop;//right line
                        considerLine3 = false;
                }
                break;
            case BELOW_LEFT:
                switch (linePoint2Region) {
                    case ABOVE_LEFT:
                    case BELOW_CENTER:
                    case BELOW_RIGHT:
                    case LEFT_CENTER:
                    case BELOW_LEFT:
                        return false;
                    default://ray is intersecting left, bottom, or none
                        line2x1 = rectLeft;
                        line2y1 = rectBottom;
                        line2x2 = rectLeft;
                        line2y2 = rectTop;//left line
                        line3x1 = rectLeft;
                        line3y1 = rectBottom;
                        line3x2 = rectRight;
                        line3y2 = rectBottom;//bottom line
                        considerLine3 = true;
                }
                break;
            case BELOW_CENTER:
                switch (linePoint2Region) {
                    case BELOW_LEFT:
                    case BELOW_CENTER:
                    case BELOW_RIGHT:
                        return false;
                    default://ray is intersecting bottom or none
                        line2x1 = rectLeft;
                        line2y1 = rectBottom;
                        line2x2 = rectRight;
                        line2y2 = rectBottom;//bottom line
                        considerLine3 = false;
                }
                break;
            case BELOW_RIGHT:
                switch (linePoint2Region) {
                    case BELOW_LEFT:
                    case BELOW_CENTER:
                    case BELOW_RIGHT:
                    case RIGHT_CENTER:
                    case ABOVE_RIGHT:
                        return false;
                    default://ray is intersecting right, bottom, or none
                        line2x1 = rectRight;
                        line2y1 = rectBottom;
                        line2x2 = rectRight;
                        line2y2 = rectTop;//right line
                        line3x1 = rectLeft;
                        line3y1 = rectBottom;
                        line3x2 = rectRight;
                        line3y2 = rectBottom;//bottom line
                        considerLine3 = true;
                }
                break;
            default:
                throw new Error("unexpected region in lineRectIntersect method!");
        }


        //try one or both possibilities. don't need to worry about there being two intersections. if there are, only line2 is considered
        if (linesIntersect(linex1, liney1, linex2, liney2, line2x1, line2y1, line2x2, line2y2, arrayToPutAnswer)) {
            return true;
        } else if (considerLine3) {
            return linesIntersect(linex1, liney1, linex2, liney2, line3x1, line3y1, line3x2, line3y2, arrayToPutAnswer);
        } else {
            return false;
        }
    }




    //Pass arrayToPutAnswer to have the method fill it with the answer ([0] is the x, [1] is the y of the intersection)
    //The closest point to line 1 point 1 is chosen
    //if there is no collision, the array is not edited
    //credit:  https://math.stackexchange.com/questions/311921/get-location-of-vector-circle-intersection
    public static boolean lineCircleIntersect(double linex1, double liney1, double linex2, double liney2, double circleCenterX, double circleCenterY, double radius, double[] arrayToPutAnswer) {
        //(x-h)^2+(y-k)^2==r^2
        //x(t) = (x1-x0)t+x0
        //y(t) = (y1-y0)t+y0
        //substitute x(t) and y(t) into circle equation

//        h = circleCenterX;  k = circleCenterY;  x1 = linex2;  x0 = linex1;  y1 = liney2;  y0 = liney1;

        //solve quadratic equation
        double x1minusX0 = linex2 - linex1;
        double y1minusY0 = liney2 - liney1;
        double x0minusH = linex1 - circleCenterX;
        double y0minusK = liney1 - circleCenterY;
        double a = x1minusX0 * x1minusX0 + y1minusY0 * y1minusY0;//a>=0
        double b = 2 * (x1minusX0) * (linex1 - circleCenterX) + 2 * (y1minusY0) * (liney1 - circleCenterY);
        double c = x0minusH * x0minusH + y0minusK * y0minusK - radius * radius;//c is positive if the starting point lies outside the circle

        if (c <= 0) {//point is inside circle
            if (arrayToPutAnswer != null) {
                arrayToPutAnswer[0] = linex1;
                arrayToPutAnswer[1] = liney1;
            }
            return true;
        }


        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {//extended line segment will never hit circle
            return false;
        }

//        double t = (2*c)/(-b+Math.sqrt(discriminant));//alternative quadratic formula (both work)
        double t = (-b - Math.sqrt(discriminant)) / (2 * a);//quadratic formula  (use negative discriminate because we only care about the closest intersection point)

        if (t < 0 || t > 1) {//intersection point is not on line segment
            return false;
        }

        //substitute t back in parametric equations
        if (arrayToPutAnswer != null) {
            arrayToPutAnswer[0] = (linex2 - linex1) * t + linex1;
            arrayToPutAnswer[1] = (liney2 - liney1) * t + liney1;
        }
        return true;


    }




    //pass arrayToPutAnswer to have the method fill it with the answer ([0] is the x, [1] is the y of the intersection)
    //if the lines overlap, the closest point to line 1 point 1 is chosen
    //if the lines are not touching, the array is not edited
    public static boolean linesIntersect(double line1x1, double line1y1, double line1x2, double line1y2, double line2x1, double line2y1, double line2x2, double line2y2, double[] arrayToPutAnswer) {
        double left1 = Math.min(line1x1, line1x2);
        double right1 = Math.max(line1x1, line1x2);
        double left2 = Math.min(line2x1, line2x2);
        double right2 = Math.max(line2x1, line2x2);
        double bottom1 = Math.min(line1y1, line1y2);//using normal coordinate system, not reversed computer graphics system
        double top1 = Math.max(line1y1, line1y2);
        double bottom2 = Math.min(line2y1, line2y2);
        double top2 = Math.max(line2y1, line2y2);

        double rise1 = line1y2 - line1y1;
        double run1 = line1x2 - line1x1;
        double rise2 = line2y2 - line2y1;
        double run2 = line2x2 - line2x1;

        //test if slopes are infinite
        if (run1 == 0 || run2 == 0) {
            if (run1 == 0 && run2 == 0) {//they are both vertical lines
                if (line1x1 == line2x1) {//if the extended lines are touching
                    if (bottom2 > top1 || bottom1 > top2) {//the segments are not touching
                        return false;
                    }

                    //the segments are touching somewhere
                    if (arrayToPutAnswer != null) {
                        //they are touching. Return one intersection point (closest to line1point1 for ray tracing)
                        double intersectX = line1x1;
                        double intersectY;

                        if (line1y1 <= top2 && line1y1 >= bottom2) {//ray trace went nowhere
                            intersectY = line1y1;
                        } else if (line1y1 > top2) {
                            intersectY = top2;
                        } else {
                            intersectY = bottom2;
                        }

                        arrayToPutAnswer[0] = intersectX;
                        arrayToPutAnswer[1] = intersectY;
                    }
                    return true;
                } else {
                    return false;//not touching
                }
            }
            if (run1 == 0) {//only line1 is vertical
                double m2 = rise2 / run2;
                double intersectX = line1x1;
                double intersectY = line2y1 + m2 * (intersectX - line2x1);//line 2's intersection point assuming lines are infinite

                //test if the intersection point is actually on the lines
                if (intersectY < bottom1 || intersectY < bottom2 || intersectY > top1 || intersectY > top2 || intersectX < left2 || intersectX > right2) {
                    return false;
                }

                if (arrayToPutAnswer != null) {
                    arrayToPutAnswer[0] = intersectX;
                    arrayToPutAnswer[1] = intersectY;
                }
                return true;
            }
            if (run2 == 0) {//only line2 is vertical
                double m1 = rise1 / run1;
                double intersectX = line2x1;
                double intersectY = line1y1 + m1 * (intersectX - line1x1);//line 1's intersection point assuming lines are infinite

                //test if the intersection point is actually on the lines
                if (intersectY < bottom1 || intersectY < bottom2 || intersectY > top1 || intersectY > top2 || intersectX < left1 || intersectX > right1) {
                    return false;
                }

                if (arrayToPutAnswer != null) {
                    arrayToPutAnswer[0] = intersectX;
                    arrayToPutAnswer[1] = intersectY;
                }
                return true;
            }
        }

        //slopes are not infinite

        double m1 = rise1 / run1;
        double m2 = rise2 / run2;

        //if lines are parallel
        if (m1 == m2) {
            //point slope form: y=y1+m1(x-x1)
            //are both equations equal at x=0?: y1-x1m1==v1-m2u1   where v1 is line2's y1 and u1 is line2's x1
            if (line1y1 - line1x1 * m1 == line2y1 - m2 * line2x1) {
                //Infinitely extended lines are on top of each other

                if (left2 > right1 || left1 > right2) {//the segments are not touching
                    return false;
                }

                //segments are touching. Return one intersection point (first one for ray tracing
                if (arrayToPutAnswer != null) {
                    double intersectX;
                    if (line1x1 <= right2 && line1x1 >= left2) {//ray trace went nowhere
                        intersectX = line1x1;
                    } else if (line1x1 > right2) {
                        intersectX = right2;
                    } else {
                        intersectX = left2;
                    }

                    arrayToPutAnswer[0] = intersectX;
                    arrayToPutAnswer[1] = line1y1 + m1 * (intersectX - line1x1);//intersectY
                }
                return true;
            } else {
                return false;
            }
        }


        //lines are not parallel

        double intersectX = (line2y1 - line1y1 + m1 * line1x1 - m2 * line2x1) / (m1 - m2);

        //test if the intersection point is actually on the lines
        if (intersectX < left1 || intersectX < left2 || intersectX > right1 || intersectX > right2) {
            return false;
        }

        //they intersect normally at one point
        //standard situation:
        //Xi = (v1-y1+m1x1-m2u1)/(m1-m2)
        //Yi = (y1+m1(Xi-x1)

        double intersectY = line1y1 + m1 * (intersectX - line1x1);

        if (arrayToPutAnswer != null) {
            arrayToPutAnswer[0] = intersectX;
            arrayToPutAnswer[1] = intersectY;
        }
        return true;
    }
}

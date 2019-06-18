package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.bouncygame.hitboxes.Hitbox;
import com.example.bouncygame.hitboxes.RectHitbox;
import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

public class Explosion extends GameObject implements CollisionDetectionFree,DoSomethingOnAdd{
    private static final String CLASS_NAME = "Explosion";

    private Hitbox hitbox;
    private int damage;

    private ExplosionAnimation animationObject;


    public Explosion(float width,float height, int damage, float xCenter,float yCenter) {
        this.hitbox = new RectHitbox(xCenter - width / 2 + 1, yCenter - height / 2 + 1, width-2, height-2);//a little smaller that the grid it covers to prevent just baelry hiting blocks beyond its radius
        this.damage = damage;
        this.animationObject = new ExplosionAnimation();

        int intWidth = (int) width;
        int intHeight = (int) height;
        ImageCache.explosion[0].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[1].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[2].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[3].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[4].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[5].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[6].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[7].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[8].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[9].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[10].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[11].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[12].setBounds(0, 0, intWidth, intHeight);
        ImageCache.explosion[13].setBounds(0, 0, intWidth, intHeight);
    }



    public int getDamage(){
        return damage;
    }

    @Override
    public void doOnAdd() {
        addGameObject(animationObject);
        delete();//alive for one tick so collision detection will handle it once

    }





    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public void handleCollision(CollisionDetectionObject secondObject) {
        CollisionMediator.handleCollision(this,secondObject);
    }



//    @Override
//    public void draw(GameView view, Paint paint, Canvas canvas) {
//        paint.setColor(Color.GREEN);
//        canvas.drawRect((int) hitbox.getLeft(),(int) hitbox.getTop(),(int) hitbox.getRight(),(int) hitbox.getBottom(),paint);
//    }


    private class ExplosionAnimation extends GameObject implements DrawableObject,TickObject,AnimationCycleListener{

        MyAnimation animation;

        public ExplosionAnimation(){
            this.animation = new MyAnimation(ImageCache.explosion, 3, false, this);
        }




        @Override
        public void animationFinishedCycle() {
            this.delete();
        }


        @Override
        public void doOnGameTick(InputStatus inputStatus) {
            animation.advanceTick();
        }

        @Override
        public void draw(GameView view, Paint paint, Canvas canvas) {
            canvas.save();
            canvas.translate(hitbox.getLeft(),hitbox.getTop());
            animation.draw(canvas);
            canvas.restore();
        }

        @Override
        public int getDrawLayerToAddTo() {
            return DrawManager.MIDDLEGROUND;
        }


    }
}

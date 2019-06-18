package com.example.bouncygame.game;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.views.GameView;

//   https://developer.android.com/guide/topics/graphics/drawable-animation
public class TestImage extends GameObject implements DrawableObject,DoSomethingOnAdd{
    private static final String CLASS_NAME = "TestImage";

    private Rect rect;//relitive to position (left: 0, top: 0, right: width, bottom: height)
    private Thread thread;

    public TestImage(){
        this.rect = new Rect(0,0,200,200);
    }

    public void setSize(int size){
        rect.right = rect.left+size;
        rect.bottom = rect.top+size;
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        ImageCache.testAnimation.setBounds(rect);
        canvas.save();//////only save once?
        canvas.translate(100,1100);
        ImageCache.testAnimation.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.MIDDLEGROUND;
    }

    @Override
    public void doOnAdd() {
        thread = new Thread(){
            @Override
            public void run() {
                ImageCache.testAnimation.start();
                Log.d(CLASS_NAME,"the animation started");
            }
        };
        thread.start();
    }

    public void start(){


    }
}

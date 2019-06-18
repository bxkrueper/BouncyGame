package com.example.bouncygame;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.sounds.SoundCache;
import com.example.bouncygame.views.GameView;

public class MainActivity extends AppCompatActivity {
    private static final String CLASS_NAME = "MainActivity";

    AnimationDrawable testAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SoundCache.initilize(this);
        ImageCache.initilize(this);




//        ImageView testAnimationImageView = (ImageView) findViewById(R.id.testAnimationImageView);
//        testAnimationImageView.setBackgroundResource(R.drawable.test_animation);
//        testAnimation = (AnimationDrawable) testAnimationImageView.getBackground();
//
//        testAnimationImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                testAnimation.start();
//            }
//        });
    }

    //https://developer.android.com/reference/android/app/Activity#ActivityLifecycle
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        SoundCache.destroy();
    }
}

package com.example.bouncygame.images;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;

import com.example.bouncygame.R;

public class ImageCache {
    private static final String CLASS_NAME = "ImageCache";
    private static Context context;

    public static Drawable missing_image;

    public static Drawable stone;
    public static Drawable obsidian;
    public static Drawable bedrock;
    public static Drawable dirt;
    public static Drawable iron_ore;
    public static Drawable diamond_ore;
    public static Drawable crackImage;
    public static Drawable iron_ingot;
    public static Drawable diamond;
    public static Drawable glass;
    public static Drawable tnt;
    public static Drawable gunpowder;
    public static Drawable spikeBall;
    public static Drawable bonusSpikeBall;
    public static Drawable background;


    public static Drawable[] backgroundScrollArray;
    public static Drawable[] explosion;

    public static AnimationDrawable testAnimation;

    public static void initilize(Context imageContext){
        context = imageContext;

        //.setBounds(0,0,100,100);//set bounds before drawing or nothing gets displayed
        missing_image = context.getResources().getDrawable(R.drawable.missing_image);missing_image.setBounds(0,0,missing_image.getIntrinsicWidth(),missing_image.getIntrinsicHeight());

        stone = context.getResources().getDrawable(R.drawable.stone);
        dirt = context.getResources().getDrawable(R.drawable.dirt);
        bedrock = context.getResources().getDrawable(R.drawable.bedrock);
        obsidian = context.getResources().getDrawable(R.drawable.obsidian);
        iron_ore = context.getResources().getDrawable(R.drawable.iron_ore);
        diamond_ore = context.getResources().getDrawable(R.drawable.diamond_ore);
        crackImage = context.getResources().getDrawable(R.drawable.crack2);
        iron_ingot = context.getResources().getDrawable(R.drawable.iron_ingot);
        diamond = context.getResources().getDrawable(R.drawable.diamond);
        glass = context.getResources().getDrawable(R.drawable.glass);
        tnt = context.getResources().getDrawable(R.drawable.tnt);
        gunpowder = context.getResources().getDrawable(R.drawable.gunpowder);
        spikeBall = context.getResources().getDrawable(R.drawable.spike_ball);
        bonusSpikeBall = context.getResources().getDrawable(R.drawable.bonus_spike_ball);
        background = context.getResources().getDrawable(R.drawable.background);

        backgroundScrollArray = new Drawable[3];
        backgroundScrollArray[0] = context.getResources().getDrawable(R.drawable.background0);
        backgroundScrollArray[1] = context.getResources().getDrawable(R.drawable.background1);
        backgroundScrollArray[2] = context.getResources().getDrawable(R.drawable.background2);

        explosion = new Drawable[14];
        explosion[0] = context.getResources().getDrawable(R.drawable.explosion0);explosion[0].setBounds(0,0,explosion[0].getIntrinsicWidth(),explosion[0].getIntrinsicHeight());
        explosion[1] = context.getResources().getDrawable(R.drawable.explosion1);explosion[1].setBounds(0,0,explosion[1].getIntrinsicWidth(),explosion[1].getIntrinsicHeight());
        explosion[2] = context.getResources().getDrawable(R.drawable.explosion2);explosion[2].setBounds(0,0,explosion[2].getIntrinsicWidth(),explosion[2].getIntrinsicHeight());
        explosion[3] = context.getResources().getDrawable(R.drawable.explosion3);explosion[3].setBounds(0,0,explosion[3].getIntrinsicWidth(),explosion[3].getIntrinsicHeight());
        explosion[4] = context.getResources().getDrawable(R.drawable.explosion4);explosion[4].setBounds(0,0,explosion[4].getIntrinsicWidth(),explosion[4].getIntrinsicHeight());
        explosion[5] = context.getResources().getDrawable(R.drawable.explosion5);explosion[5].setBounds(0,0,explosion[5].getIntrinsicWidth(),explosion[5].getIntrinsicHeight());
        explosion[6] = context.getResources().getDrawable(R.drawable.explosion6);explosion[6].setBounds(0,0,explosion[6].getIntrinsicWidth(),explosion[6].getIntrinsicHeight());
        explosion[7] = context.getResources().getDrawable(R.drawable.explosion7);explosion[7].setBounds(0,0,explosion[7].getIntrinsicWidth(),explosion[7].getIntrinsicHeight());
        explosion[8] = context.getResources().getDrawable(R.drawable.explosion8);explosion[8].setBounds(0,0,explosion[8].getIntrinsicWidth(),explosion[8].getIntrinsicHeight());
        explosion[9] = context.getResources().getDrawable(R.drawable.explosion9);explosion[9].setBounds(0,0,explosion[9].getIntrinsicWidth(),explosion[9].getIntrinsicHeight());
        explosion[10] = context.getResources().getDrawable(R.drawable.explosion10);explosion[10].setBounds(0,0,explosion[10].getIntrinsicWidth(),explosion[10].getIntrinsicHeight());
        explosion[11] = context.getResources().getDrawable(R.drawable.explosion11);explosion[11].setBounds(0,0,explosion[11].getIntrinsicWidth(),explosion[11].getIntrinsicHeight());
        explosion[12] = context.getResources().getDrawable(R.drawable.explosion12);explosion[12].setBounds(0,0,explosion[12].getIntrinsicWidth(),explosion[12].getIntrinsicHeight());
        explosion[13] = context.getResources().getDrawable(R.drawable.explosion13);explosion[13].setBounds(0,0,explosion[13].getIntrinsicWidth(),explosion[13].getIntrinsicHeight());

        //animations
        testAnimation = (AnimationDrawable) context.getResources().getDrawable(R.drawable.test_animation);testAnimation.setBounds(0,0,testAnimation.getIntrinsicWidth(),testAnimation.getIntrinsicHeight());

        Log.d(CLASS_NAME, "Images initilized");
    }

    public static void setDimsBasedOnScreenSize(int width,int height){
        background.setBounds(0,0,width,height);
    }




    //opacity between 0 and 1
    public static void setOpacity(Drawable image,double opacity){
        int alpha = (int) (opacity*256);
        if(alpha>255){
            alpha = 255;
        }
        if(alpha<0){
            alpha = 0;
        }
        image.setAlpha(alpha);
    }

}

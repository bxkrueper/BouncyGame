package com.example.bouncygame.sounds;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import com.example.bouncygame.R;

public class SoundCache {
    private static final String CLASS_NAME = "SoundCache";
    private static Context context;
    private static SoundPool soundPool;//better for short sounds. use media player for music
    public static int test_tone,stoneBreak, paddleBounce,dirtBreak,explode,fuse,glassBreak,explosion;
    //todo: make map of ids to streamids so sounds can be paused based on sound id
    private static final int MAX_STREAMS = 10;

    public static void initilize(Context soundContext){
        context = soundContext;

        //make sound pool
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            soundPool = new SoundPool.Builder().setMaxStreams(MAX_STREAMS).setAudioAttributes(audioAttributes).build();
        }else{
            soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC,0);//AudioManager.STREAM_MUSIC: play sound through connected device (like earphones)   src quality: no effect. just put 0
        }

        test_tone = soundPool.load(context,R.raw.test_tone,1);
        stoneBreak = soundPool.load(context,R.raw.stone_break,1);
        paddleBounce = soundPool.load(context,R.raw.paddle_bounce,1);
        dirtBreak = soundPool.load(context,R.raw.dirt_break,1);
        explode = soundPool.load(context,R.raw.explode,1);
        fuse = soundPool.load(context,R.raw.fuse,1);
        glassBreak = soundPool.load(context,R.raw.glass_break,1);
        explosion = soundPool.load(context,R.raw.explosion,1);
    }

    //call when activity is done?
    public static void destroy(){
        soundPool.release();
        soundPool = null;
    }

    public static void play(int soundID){
        play(soundID,1);
    }

    public static void play(int soundID,float volume){
        //left volume and right volume: float between 0 and 1
        //loop: 0 for once, 1 for twice...   or -1 for infinite repeats
        //rate: float between 1 and 2   normal speed: 1
        soundPool.play(soundID,volume,volume,0,0,1);//returns stream ID in case you want to pause it
        Log.d(CLASS_NAME,"sound id: " + Integer.toString(soundID));
    }
}

//public class SoundCache {
//    private static Context context;
//
//    private static MediaPlayer stoneBreak;
//
//    public static void setContext(Context con){
//        context = con;
//    }
//
//    public static void stoneBreak(){
//        if(stoneBreak==null){
//            stoneBreak = MediaPlayer.create(context,R.raw.stone_break);
//        }
//        if(stoneBreak.isPlaying()){
//
//        }
//        stoneBreak.start();
//    }
//}
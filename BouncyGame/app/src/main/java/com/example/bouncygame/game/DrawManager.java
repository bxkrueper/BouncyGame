package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.bouncygame.cameras.CameraInterface;
import com.example.bouncygame.views.GameView;

import java.util.ArrayList;
import java.util.List;

public class DrawManager {
    private static final String CLASS_NAME = "DrawManager";
    public static final int BACK = 0;// not affected by camera
    public static final int BACKGROUND = 1;
    public static final int MIDDLEGROUND = 2;
    public static final int FOREGROUND = 3;
    public static final int INTERFACE = 4;//not affected by camera


    private List<DrawableObject> backList;
    private List<DrawableObject> backGroundList;
    private List<DrawableObject> middleGroundList;
    private List<DrawableObject> foreGroundList;
    private List<DrawableObject> interfaceList;

    private boolean isDrawing;//to stop ConcurrentModificationExceptions in draw method
//    private List<DrawableObject> drawableObjectAddQueue;//should only be used if the game is drawing when trying to modify middleGroundList
    private List<DrawableObject> drawableObjectDeleteQueue;//should only be used if the game is drawing when trying to modify middleGroundList

    public DrawManager(){
        this.backList = new ArrayList<>();
        this.backGroundList = new ArrayList<>();
        this.middleGroundList = new ArrayList<>();
        this.foreGroundList = new ArrayList<>();
        this.interfaceList = new ArrayList<>();

        this.isDrawing = false;
        this.drawableObjectDeleteQueue = new ArrayList<>();
    }

    //layer: use BACK, BACKGROUND,MIDDLEGROUND,FOREGROUND, or INTERFACE
    public void add(DrawableObject drawable){
        switch(drawable.getDrawLayerToAddTo()){
            case BACK:
                backList.add(drawable);
                break;
            case BACKGROUND:
                backGroundList.add(drawable);
                break;
            case MIDDLEGROUND:
                middleGroundList.add(drawable);
                break;
            case FOREGROUND:
                foreGroundList.add(drawable);
                break;
            case INTERFACE:
                interfaceList.add(drawable);
                break;
            default:
                Log.d(CLASS_NAME,"unknown layer number!");
        }
    }
    public void remove(DrawableObject drawable){
        if(isDrawing){
            drawableObjectDeleteQueue.add(drawable);
        }else{
            actualDelete(drawable);
        }
    }

    //deletes the drawable without worrying about being in the middle of drawing. Assumes the object is only in one of the lists, so it starts with what is likely the biggest list and stops when it finds it
    private void actualDelete(DrawableObject drawable) {
        if(middleGroundList.remove(drawable)){
            return;
        }
        if(foreGroundList.remove(drawable)){
            return;
        }
        if(backGroundList.remove(drawable)){
            return;
        }
        if(interfaceList.remove(drawable)){
            return;
        }
        backList.remove(drawable);
    }

    public void draw(GameView gameView, Paint paint, Canvas canvas, CameraInterface camera){
        isDrawing = true;
        for(int i = 0; i< backList.size(); i++){//these objects are not affected by the camera. Expected to have just one object like a black box that fills the screen
            backList.get(i).draw(gameView,paint,canvas);
        }

        //get the camera to mutate the canvas
        canvas.save();
        camera.editCanvasPreDraw(canvas);

        for(int i = 0; i< backGroundList.size(); i++){
            backGroundList.get(i).draw(gameView,paint,canvas);
        }
        for(int i = 0; i< middleGroundList.size(); i++){
            middleGroundList.get(i).draw(gameView,paint,canvas);
        }
        for(int i = 0; i< foreGroundList.size(); i++){
            foreGroundList.get(i).draw(gameView,paint,canvas);
        }

        //restore the canvas. Interface objects are not affected by the camera and draw relative to the screen
        canvas.restore();
        for(int i = 0; i< interfaceList.size(); i++){
            interfaceList.get(i).draw(gameView,paint,canvas);
        }
        isDrawing = false;

        for(int i=0;i<drawableObjectDeleteQueue.size();i++){
            actualDelete(drawableObjectDeleteQueue.get(i));
        }
        drawableObjectDeleteQueue.clear();
    }
}

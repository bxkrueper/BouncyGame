package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.example.bouncygame.cameras.CameraInterface;
import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.views.GameView;

import java.util.ArrayList;
import java.util.List;

//one dimension, downwards
public class ScrollingBackground extends GameObject implements DrawableObject{
    private final int widthOfSegment;
    private final int heightOfSegment;
    private final CameraInterface camera;
    private final List<Drawable> drawableList;

    public ScrollingBackground(CameraInterface camera, int widthOfSegment, int heightOfSegment){
        this.camera = camera;
        this.widthOfSegment = widthOfSegment;
        this.heightOfSegment = heightOfSegment;
        this.drawableList = new ArrayList<>();
    }

    public void addBackgroundtoGrid(Drawable drawable, int index){
        if(index<0){
            return;
        }else if(index<drawableList.size()){
            drawableList.set(index,drawable);
        }else if(index==drawableList.size()){
            drawableList.add(drawable);
        }else{//fill in extra space in list with null
            int nulls = index-drawableList.size();
            for(int i=0;i<nulls;i++){
                drawableList.add(null);
            }
            drawableList.add(drawable);
        }


        drawable.setBounds(0,0,widthOfSegment,heightOfSegment);
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        //determine which indexes to draw from
        int firstIndex = (int) camera.getTop() /heightOfSegment;
        int lastIndex = (int) camera.getBottom() /heightOfSegment;

        for(int i=firstIndex;i<=lastIndex;i++){
            //draw the segment
            Drawable drawable = getDrawable(i);
            if(drawable==null){
                continue;
            }

            canvas.save();
            canvas.translate(camera.getLeft(),heightOfSegment*i);
            drawable.draw(canvas);
            canvas.restore();
        }
    }

    private Drawable getDrawable(int i) {
        if(i>=drawableList.size() || i<0){
            return null;
        }else{
            return drawableList.get(i);
        }
    }

    @Override
    public int getDrawLayerToAddTo() {
        return DrawManager.BACKGROUND;
    }
}

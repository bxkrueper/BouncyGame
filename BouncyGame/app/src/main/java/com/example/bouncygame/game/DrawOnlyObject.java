package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.example.bouncygame.hitboxes.RectHitbox;
import com.example.bouncygame.images.ImageCache;
import com.example.bouncygame.views.GameView;

public class DrawOnlyObject extends GameObject implements DrawableObject {

    private Drawable drawable;
    private RectHitbox hitbox;
    private int layer;

    public DrawOnlyObject(Drawable drawable,int layer,RectHitbox hitbox){
        this.drawable = drawable;
        this.layer = layer;
        this.hitbox = hitbox;
    }

    @Override
    public void draw(GameView view, Paint paint, Canvas canvas) {
        canvas.save();
        canvas.translate(hitbox.getLeft(),hitbox.getTop());
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getDrawLayerToAddTo() {
        return layer;
    }

}

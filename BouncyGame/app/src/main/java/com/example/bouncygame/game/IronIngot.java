package com.example.bouncygame.game;

import com.example.bouncygame.R;
import com.example.bouncygame.hitboxes.CircleHitbox;
import com.example.bouncygame.images.ImageCache;
import android.util.Log;

public class IronIngot extends Item {



    public IronIngot(float xCenter, float yCenter) {
        picture = ImageCache.iron_ingot;

        Log.d("Iron Ingot!", picture.toString());
        this.hitbox = new CircleHitbox(xCenter,yCenter,Item.getWidth()/2);
    }

    @Override
    public void collected(BouncyBall ball) {
        ball.setDamage(ball.getDamage()+10);
        delete();
    }

}

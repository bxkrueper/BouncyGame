package com.example.bouncygame.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.bouncygame.views.GameView;
import com.example.bouncygame.views.InputStatus;

public abstract class GameObject {
    private static final String CLASS_NAME = "GameObject";

    //adds the given game object to the game after the game tick finishes
    public final void addGameObject(GameObject gameObject){
        Game.getInstance().add(gameObject);
    }

    //puts the game object in the delete list so it will be deleted at the end of the tick
    //recomend to call another object method which calls this after doing stuff like spawn animations before deleting itself
    public final void delete(){
        Game.getInstance().delete(this);
    }



    public final void tellGame(Game.GameEvent event){
        Game.getInstance().recieveEvent(event,null);
    }
    //should be overwritten to recieve events
    public void recieveMessage(Game.GameEvent event) {
        Log.d(CLASS_NAME,"don't recognize event: " + event + "recieveMessage() not overwritten");
    }


}

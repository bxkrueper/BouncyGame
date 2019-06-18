package com.example.bouncygame.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActionGroup implements GameAction{

    private List<GameAction> actionList;

    public GameActionGroup(GameAction ... gameAction){
        this.actionList = new ArrayList<>(gameAction.length);
        actionList.addAll(Arrays.asList(gameAction));
    }

    @Override
    public void doAction(Object ... parameters) {
        for(int i=0;i<actionList.size();i++){
            actionList.get(i).doAction(parameters);
        }
    }
}

package com.example.bouncygame.game;

public class GameActionNull implements GameAction{

    private static GameActionNull instance;

    private GameActionNull(){

    }

    public static GameActionNull getInstance(){
        if(instance==null){
            instance = new GameActionNull();
        }
        return instance;
    }

    @Override
    public void doAction(Object ... parameters) {
        //do nothing
    }
}

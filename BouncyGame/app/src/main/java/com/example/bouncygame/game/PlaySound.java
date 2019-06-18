package com.example.bouncygame.game;

import com.example.bouncygame.sounds.SoundCache;

public class PlaySound implements GameAction{

    private int sound;

    public PlaySound(int sound){
        this.sound = sound;
    }

    @Override
    public void doAction(Object... parameters) {
        SoundCache.play(sound);
    }
}

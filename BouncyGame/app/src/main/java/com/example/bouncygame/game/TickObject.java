package com.example.bouncygame.game;

import com.example.bouncygame.views.InputStatus;

public interface TickObject {
    void doOnGameTick(InputStatus inputStatus);
}

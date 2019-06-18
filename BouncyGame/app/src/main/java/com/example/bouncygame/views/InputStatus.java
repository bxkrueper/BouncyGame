package com.example.bouncygame.views;

//GameView sets these fields when input changes and Game reads them
public class InputStatus {
    private static final String CLASS_NAME = "InputStatus";

    private float currentMouseX;
    private float currentMouseY;
    private boolean isTouching;

    public InputStatus(){
        this.currentMouseX = 0;
        this.currentMouseY = 0;
        this.isTouching = false;
    }

    public float getCurrentMouseX() {
        return currentMouseX;
    }

    protected void setCurrentMouseX(float currentMouseX) {
        this.currentMouseX = currentMouseX;
    }

    public float getCurrentMouseY() {
        return currentMouseY;
    }

    protected void setCurrentMouseY(float currentMouseY) {
        this.currentMouseY = currentMouseY;
    }

    public boolean isTouching() {
        return isTouching;
    }

    protected void setTouching(boolean touching) {
        isTouching = touching;
    }
}

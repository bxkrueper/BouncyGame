package com.example.bouncygame.hitboxes;

public class ToggleHitbox extends WrapperHitbox{

    private boolean active;

    public ToggleHitbox(Hitbox hitbox) {
        super(hitbox);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean intersects(Hitbox otherHitbox) {
        if(isActive()){
            return getInnerHitbox().intersects(otherHitbox);
        }else{
            return false;
        }
    }

    @Override
    public boolean rayCast(LineHitbox ray, double[] collisionCoordinates) {
        if(isActive()){
            return RayCastMediator.rayCast(getInnerHitbox(),ray,collisionCoordinates);
        }else{
            return false;
        }

    }

}

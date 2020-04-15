package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import processing.core.PVector;

@Data
public class Node implements SpecimenComponent {

    final private float FRICTION_FACTOR=0.1f;

    protected PVector position = new PVector(0.0f, 0.0f);
    protected PVector velocity = new PVector(0.0f, 0.0f);
    protected PVector acceleration = new PVector(0.0f, 0.0f);

    private List<Cell> cells = new ArrayList<>();


    @Override
    public void act() {
        PVector dragForce = calculateDragForce();
        applyForce(dragForce);
        velocity.add(acceleration);
        position.add(velocity);
        acceleration.set(0f, 0f);
    }


    public void applyForce(PVector force) {
        acceleration.add(force);
    }

    private PVector calculateDragForce() {
        PVector currentVelocity = velocity.copy();
        float velocitySqValue = currentVelocity.magSq();
        PVector velocityDirection = currentVelocity.copy();
        velocityDirection.normalize();
        return velocityDirection.mult(-1*FRICTION_FACTOR*velocitySqValue);
    }

}

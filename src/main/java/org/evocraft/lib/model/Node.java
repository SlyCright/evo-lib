package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;

@Getter
@Setter
public class Node extends SpecimenComponent {

    private PVector position= new PVector(0.0f, 0.0f);
    private PVector velocity = new PVector(0.0f, 0.0f);
    private PVector acceleration = new PVector(0.0f, 0.0f);

    @Override
    public void act() {
        velocity.add(acceleration);
        position.add(velocity);
        acceleration.set(0f, 0f);
    }

    public void applyForce(PVector force) {
        acceleration.add(force);
    }

}

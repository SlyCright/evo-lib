package org.evocraft.lib.model;

import lombok.Data;
import processing.core.PVector;

@Data
public class Connection implements SpecimenComponent {

    Cell input, output;
    PVector initialPosition, terminalPosition;
    final float weight;

    Connection(float weight) {
        this.weight = weight;
    }

    @Override
    public void act() {
        initialPosition = input.getPosition().copy();
        terminalPosition = output.getPosition().copy();
    }
}

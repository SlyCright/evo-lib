package org.evocraft.lib.model;

import lombok.Data;
import processing.core.PVector;

@Data
public class Connection extends Activateable {

    Cell input, output;
    PVector initialPosition, terminalPosition;  //todo refactor: it's duplication of data. Delete it and change getting position for connection
    final float WEIGHT;

    Connection(float WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    @Override
    public void act() {
        initialPosition = input.getPosition().copy();
        terminalPosition = output.getPosition().copy();
        this.isActive=input.isActive();
    }

    public float getSignal() {
        if (input.isActive()) {
            return WEIGHT;
        } else {
            return 0f;
        }
    }

}

package org.evocraft.lib.model;

import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;

@Getter
@Setter
public class Connection extends Activateable {

    private Cell input, output;
    private GridPlaces gridPlaces;
    private PVector initialPosition, terminalPosition;  //todo refactor: it's duplication of data. Delete it and change getting position for connection
    private final float WEIGHT;

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

package org.evocraft.lib.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;

@Getter
@Setter
@EqualsAndHashCode(of = {"inputGridPlace", "outputGridPlace"})
public class Connection extends Activateable {

    private Cell input, output;
    private GridPlace inputGridPlace, outputGridPlace;
    private PVector initialPosition, terminalPosition;  //todo refactor: it's duplication of data. Delete it and change getting position for connection
    private float weight;

    Connection(float weight) {
        this.weight = weight;
    }

    @Override
    public void act() {
        initialPosition = input.getPosition().copy();
        terminalPosition = output.getPosition().copy();
        this.isActive = input.isActive();
    }

    public float getSignal() {
        if (input.isActive()) {
            return weight;
        } else {
            return 0f;
        }
    }

}

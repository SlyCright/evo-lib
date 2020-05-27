package org.evocraft.lib.model;

import processing.core.PVector;

import java.util.List;

public class Fixer extends Cell {

    private static final float FREEZE_RATIO=0.25f;

    public Fixer(boolean doesReverseSignal) {
        super.doesReverseSignal = doesReverseSignal;
    }

    public void act() {
        super.act();
        this.isActive = calculateIfActive(inputConnections);
        fixNodes();
    }

    protected void fixNodes() {

        if (this.isActive) {
            for (Node adjacentNode : adjacentNodes) {

                PVector acceleration = adjacentNode.getAcceleration().copy();
                acceleration.mult(FREEZE_RATIO);
                adjacentNode.setAcceleration(acceleration);

                PVector velocity = adjacentNode.getVelocity().copy();
                velocity.mult(FREEZE_RATIO);
                adjacentNode.setVelocity(velocity);
            }
        }
    }

    protected boolean calculateIfActive(List<Connection> inputConnections) { //todo refactor: the same method as an Muscle
        boolean isActive = false;

        for (Connection connection : inputConnections) {
            if (connection.isActive()) {
                isActive = true;
                break;
            }
        }

        if (doesReverseSignal) {
            isActive = !isActive;
        }

        return isActive;
    }

    @Override
    public Cell copy() {
        return Copier.copy(this);
    }
}

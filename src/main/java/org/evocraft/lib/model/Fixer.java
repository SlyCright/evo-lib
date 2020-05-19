package org.evocraft.lib.model;

import processing.core.PVector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fixer extends Cell {
    boolean isStillFixing = false;
    Map<Integer, PVector> savedNodesPositions = new HashMap<>();


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
                acceleration.mult(0.25f);
                adjacentNode.setAcceleration(acceleration);

                PVector velocity = adjacentNode.getVelocity().copy();
                velocity.mult(0.25f);
                adjacentNode.setVelocity(velocity);
            }
        }
//
//        if (!this.isActive && !this.isStillFixing) {
//            return;
//        }
//
//        if (this.isActive && !this.isStillFixing) {
//            isStillFixing = true;
//
//            for (Node adjacentNode : adjacentNodes) {
//                int key = adjacentNode.hashCode();
//                PVector positionToSave = adjacentNode.getPosition().copy();
//                savedNodesPositions.put(key, positionToSave);
//            }
//        }
//
//        if (!this.isActive && this.isStillFixing) {
//            isStillFixing = false;
//            savedNodesPositions.clear();
//        }
//
//        if (this.isActive && this.isStillFixing) {
//
//            for (Node adjacentNode : adjacentNodes) {
//                adjacentNode.setAcceleration(new PVector(0f, 0f));
//                adjacentNode.setVelocity(new PVector(0f, 0f));
//                int key = adjacentNode.hashCode();
//                PVector savedPosition = savedNodesPositions.get(key).copy();
//                adjacentNode.setPosition(savedPosition);
//            }
//        }

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

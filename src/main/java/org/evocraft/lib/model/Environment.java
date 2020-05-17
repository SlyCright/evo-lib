package org.evocraft.lib.model;

import java.util.ArrayList;

import processing.core.PVector;

public class Environment implements Actionable {

    final static private float DRAG_FRICTION_FACTOR = 0.025f;

    @Override
    public void act() {
        //todo backclog: some codeHere
    }

    public static void interactWith(ArrayList<Specimen> species) {
        applyDragForce(species);
    }

    private static void applyDragForce(ArrayList<Specimen> species) {
        for (Specimen specimen : species) {
            PVector previousPosition = specimen.getPosition().copy();
            PVector currentPosition = specimen.calculatePosition().copy();
            PVector velocity = PVector.sub(currentPosition, previousPosition);
            float velocitySqMag = velocity.magSq();
            PVector velocityDirection = velocity.copy().normalize();
            PVector commonDragForce = velocityDirection.mult(1 * DRAG_FRICTION_FACTOR * velocitySqMag);
            for (Node node : specimen.getNodes().values()) {
                node.applyForce(commonDragForce);
            }
        }

        species.stream()
                .flatMap(s -> s.getNodes().values().stream())
                .forEach(Environment::applyDragForce);
    }

    private static void applyDragForce(Node node) {
        PVector nodeVelocity = node.getVelocity().copy();
        float velocitySqValue = nodeVelocity.magSq();
        PVector velocityDirection = nodeVelocity.copy().normalize();
        PVector dragForce = velocityDirection.mult(-1 * DRAG_FRICTION_FACTOR * velocitySqValue);
        node.applyForce(dragForce);
    }

}

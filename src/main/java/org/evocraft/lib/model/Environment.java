package org.evocraft.lib.model;

import processing.core.PVector;

public class Environment implements Actionable {

    final static private float DRAG_FRICTION_FACTOR = 0.05f;

    @Override
    public void act() {
        //todo backclog: some codeHere
    }

    public static void applyDragForce(Node node) {
        PVector nodeVelocity = node.getVelocity().copy();
        float velocitySqValue = nodeVelocity.magSq();
        PVector velocityDirection = nodeVelocity.copy().normalize();
        PVector dragForce = velocityDirection.mult(-1 * DRAG_FRICTION_FACTOR * velocitySqValue);
        node.applyForce(dragForce);
    }
}

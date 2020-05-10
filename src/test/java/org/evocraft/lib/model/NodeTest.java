package org.evocraft.lib.model;

import org.junit.jupiter.api.Test;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void act() {
        Node node = new Node();
        List<PVector> forces = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            forces.add(PVector.random2D());
        }

        PVector forcesSum = new PVector(0f, 0f);
        for (PVector force : forces) {
            node.applyForce(force);
            forcesSum.add(force);
        }

        node.act();

        PVector position = node.getPosition();
        PVector velocity = node.getVelocity();
        PVector acceleration = node.getAcceleration();

        float epsilon = Float.MIN_VALUE;

        assertTrue(Math.abs(position.x - forcesSum.x) < epsilon);
        assertTrue(Math.abs(position.y - forcesSum.y) < epsilon);

        assertTrue(Math.abs(position.x - velocity.x) < epsilon);
        assertTrue(Math.abs(position.y - velocity.y) < epsilon);

        assertTrue(Math.abs(acceleration.x) < epsilon);
        assertTrue(Math.abs(acceleration.y) < epsilon);

    }

}
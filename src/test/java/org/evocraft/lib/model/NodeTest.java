package org.evocraft.lib.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import processing.core.PVector;

class NodeTest {

    @RepeatedTest(1)
    void modelTuning() {
        Specimen specimen = SpecimenBuilder.generateSpecimen(1, 0);

        Cell cell = specimen.getCells().values().stream().findFirst().get();
        Node node = cell.getAdjacentNodes().get(0);

        for (int i = 0; i < 10; i++) {
            specimen.act();
            for (Node value : specimen.getNodes().values()) {
                Environment.applyDragForce(value);
            }
            cell.calculatePosition();
//            System.out.println("i ============================ " + i);
//            System.out.println("cell.getPosition() = " + cell.getPosition());
//            System.out.println("node.getAcceleration() = " + node.getAcceleration());
//            System.out.println("node.getVelocity() = " + node.getVelocity());
//            System.out.println("node.getPosition() = " + node.getPosition());
        }


//        System.out.println("node.getAcceleration() = " + node.getAcceleration());
//        System.out.println("node.getVelocity().toString() = " + node.getVelocity().toString());
//        System.out.println("node.getPosition().toString() = " + node.getPosition().toString());
    }

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
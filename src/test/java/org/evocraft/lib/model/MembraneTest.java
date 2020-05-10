package org.evocraft.lib.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MembraneTest {

    @Test
    void act() {
        new Membrane().act();
    }

    @Test
    void calculateShouldBeLength() {
        Membrane membrane = new Membrane();
        float epsilon = Float.MIN_VALUE;

        assertTrue(Math.abs(membrane.calculateShouldBeLength() - Membrane.LENGTH) < epsilon);

        membrane.applyLength(0, 0f);
        assertTrue(Math.abs(membrane.calculateShouldBeLength() - Membrane.LENGTH / 2f) < epsilon);

        membrane.applyLength(0, -Membrane.LENGTH);
        assertTrue(Math.abs(membrane.calculateShouldBeLength()) < epsilon);


        membrane.applyLength(1, Membrane.LENGTH);
        assertTrue(Math.abs(membrane.calculateShouldBeLength()) < epsilon);
    }

    @Test
    void calculateMembraneVector() {
        assertNotNull(new Membrane().calculateMembraneVector());
    }

    @Test
    void applyForceToAdjacentNodes() {
        new Membrane().calculateMembraneVector();
    }

    @Test
    void applyLength() {
        new Membrane().applyLength(0, 0f);
    }
}
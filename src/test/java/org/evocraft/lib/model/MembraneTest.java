package org.evocraft.lib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import processing.core.PVector;

@ExtendWith(MockitoExtension.class)
class MembraneTest {

    @Spy
    private Membrane membrane;

    @Test
    void act() {
        PVector pVector = new PVector(1000f, 0f);

        when(membrane.calculateMembraneVector()).thenReturn(pVector);
        when(membrane.calculateShouldBeLength()).thenReturn(0f);

        membrane.act();

        assertEquals(pVector, membrane.getMembrane());
        verify(membrane).applyForceToAdjacentNode(eq(-25f), any(Node.class));
        verify(membrane).applyForceToAdjacentNode(eq(25f), any(Node.class));
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
package org.evocraft.lib.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MuscleTest {

    @Test
    void act() {
        new Muscle(1f,false).act();
    }

    @Test
    void applySizeToMembranes() {
        new Muscle(0.5f,false).applySizeToMembranes(1f);
    }

    @Test
    void calculateIfActive() {
        Muscle muscle = new Muscle(0.5f,false);
        Connection connection = new Connection(0.5f,false);
        muscle.getInputConnections().add(connection);

        connection.setActive(true);
        muscle.act();
        assertTrue(muscle.isActive());

        connection.setActive(false);
        muscle.act();
        assertFalse(muscle.isActive());
    }
}
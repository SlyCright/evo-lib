package org.evocraft.lib.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecimenComponentTest {

    @Test
    void testHashCode() {
        new Connection(0f).hashCode();
        new Muscle(0f).hashCode();
    }
}
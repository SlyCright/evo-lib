package org.evocraft.lib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.Test;

class WorldTest {

    @Test
    void act() {
        World world = new World();
        EpochTicker epochTicker = world.getEpochTicker();

        for (int i = 0; i < epochTicker.getEPOCH_LASTING_TICKS() * 3 + 10; i++) {
            world.act();
        }
    }

    @Test
    void createNextGenerationOf() {
        World world = new World();
        for (int i = 0; i < 1000; i++) {
            world.act();
        }
        ArrayList<Specimen> ancestors = world.getSpecies();

        for (Specimen specimen : ancestors) {
            Map<Integer, Connection> connections = specimen.getConnections();
            assertNotNull(connections);
            assertTrue(connections.size() > 0);

            Connection notNullConnection = null;
            for (SpecimenComponent component : specimen.getComponents()) {
                if (component instanceof Connection) {
                    notNullConnection = (Connection) component;
                }
            }
            assertNotNull(notNullConnection);
        }

        ArrayList<Specimen> offsprings = world.createNextGenerationOf(ancestors);

        world.setSpecies(offsprings);
        for (int i = 0; i < 1000; i++) {
            world.act();
        }

        ancestors = world.getSpecies();

        offsprings = world.createNextGenerationOf(ancestors);

        assertEquals(World.SPECIMENS_TOTAL, offsprings.size());

        for (Specimen offspring : offsprings) {
            Map<Integer, Connection> connections = offspring.getConnections();
            assertNotNull(connections);
            assertTrue(connections.size() > 0);

            Connection notNullConnection = null;
            for (SpecimenComponent component : offspring.getComponents()) {
                if (component instanceof Connection) {
                    notNullConnection = (Connection) component;
                }
            }
//            assertNotNull(notNullConnection);
        }
    }

}
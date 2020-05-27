package org.evocraft.lib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CrossovererTest {

    @Test
    void crossOverBestFittedOf() {
        World world = new World();
        for (int i = 0; i < 1000; i++) {
            world.act();
        }
        ArrayList<Specimen> ancestors = world.getSpecies();
        world.sortByFitness(ancestors);

        ArrayList<Specimen> offsprings = Crossoverer.crossOverBestFittedOf(ancestors);

        for (Specimen offspring : offsprings) {
            Connection notNullConnection = null;
            for (SpecimenComponent component : offspring.getComponents()) {
                if (component instanceof Connection) {
                    notNullConnection = (Connection) component;
                }
            }
//            assertNotNull(notNullConnection);
        }
    }

    @Test
    void buildOffspringOf() {
        List<Specimen> parents = SpecimenBuilder.generateSpecies(2);

        Specimen offspring = Crossoverer.buildOffspringOf(parents);

        Connection notNullConnection = null;
        for (SpecimenComponent component : offspring.getComponents()) {
            if (component instanceof Connection) {
                notNullConnection = (Connection) component;
            }
        }
//        assertNotNull(notNullConnection);

    }

    @Test
    void mapParentsComponents_checkCells() {
        List<SpecimenComponent> parentComponents = new ArrayList<>();

        Cell cell1_1 = new Muscle(0f, false);
        cell1_1.setTileIndex(new TileIndex(0, 0));
        parentComponents.add(cell1_1);

        Cell cell1_2 = new Muscle(0f, false);
        cell1_2.setTileIndex(new TileIndex(1, 1));
        parentComponents.add(cell1_2);

        Cell cell2_1 = new Muscle(0f, false);
        cell2_1.setTileIndex(new TileIndex(0, 0));
        parentComponents.add(cell2_1);

        Cell cell2_2 = new Muscle(0f, false);
        cell2_2.setTileIndex(new TileIndex(-1, -1));
        parentComponents.add(cell2_2);

        Map<Integer, List<SpecimenComponent>> mappedComponents = Crossoverer.mapParentsComponents(parentComponents);

        assertEquals(3, mappedComponents.size());

        int hash = new TileIndex(0, 0).hashCode();
        List<SpecimenComponent> components = mappedComponents.get(hash);
        assertEquals(2, components.size());
        for (SpecimenComponent component : components) {
            assertEquals(hash, component.hashCode());
        }

        hash = new TileIndex(1, 1).hashCode();
        components = mappedComponents.get(hash);
        assertEquals(1, components.size());
        for (SpecimenComponent component : components) {
            assertEquals(hash, component.hashCode());
        }

        hash = new TileIndex(-1, -1).hashCode();
        components = mappedComponents.get(hash);
        assertEquals(1, components.size());
        for (SpecimenComponent component : components) {
            assertEquals(hash, component.hashCode());
        }

    }

    @Test
    void mapParentsComponents_checkConnections() {
        List<SpecimenComponent> parentComponents = new ArrayList<>();

        Connection connection1_1 = new Connection(0f, false);
        connection1_1.setInputTileIndex(new TileIndex(0, 0));
        connection1_1.setOutputTileIndex(new TileIndex(1, 1));
        parentComponents.add(connection1_1);

        Connection connection1_2 = new Connection(0f, false);
        connection1_2.setInputTileIndex(new TileIndex(1, 1));
        connection1_2.setOutputTileIndex(new TileIndex(2, 1));
        parentComponents.add(connection1_2);

        Connection connection2_1 = new Connection(0f, false);
        connection2_1.setInputTileIndex(new TileIndex(0, 0));
        connection2_1.setOutputTileIndex(new TileIndex(1, 1));
        parentComponents.add(connection2_1);

        Connection connection2_2 = new Connection(0f, false);
        connection2_2.setInputTileIndex(new TileIndex(1, 1));
        connection2_2.setOutputTileIndex(new TileIndex(1, 2));
        parentComponents.add(connection2_2);

        Map<Integer, List<SpecimenComponent>> mappedComponents = Crossoverer.mapParentsComponents(parentComponents);

        assertEquals(3, mappedComponents.size());

        Connection connectionTest = new Connection(0f, false);
        connectionTest.setInputTileIndex(new TileIndex(0, 0));
        connectionTest.setOutputTileIndex(new TileIndex(1, 1));
        int hash = connectionTest.hashCode();
        List<SpecimenComponent> components = mappedComponents.get(hash);
        assertEquals(2, components.size());
        for (SpecimenComponent component : components) {
            assertEquals(hash, component.hashCode());
        }

        connectionTest = new Connection(0f, false);
        connectionTest.setInputTileIndex(new TileIndex(1, 1));
        connectionTest.setOutputTileIndex(new TileIndex(2, 1));
        hash = connectionTest.hashCode();
        components = mappedComponents.get(hash);
        assertEquals(1, components.size());
        for (SpecimenComponent component : components) {
            assertEquals(hash, component.hashCode());
        }

        connectionTest = new Connection(0f, false);
        connectionTest.setInputTileIndex(new TileIndex(1, 1));
        connectionTest.setOutputTileIndex(new TileIndex(1, 2));
        hash = connectionTest.hashCode();
        components = mappedComponents.get(hash);
        assertEquals(1, components.size());
        for (SpecimenComponent component : components) {
            assertEquals(hash, component.hashCode());
        }
    }

    @RepeatedTest(10)
    void listOffspringComponents() {
        List<SpecimenComponent> parentComponents = new ArrayList<>();

        Connection connection1_1 = new Connection(0f, false);
        connection1_1.setInputTileIndex(new TileIndex(0, 0));
        connection1_1.setOutputTileIndex(new TileIndex(1, 1));
        parentComponents.add(connection1_1);

        Connection connection1_2 = new Connection(0.25f, false);
        connection1_2.setInputTileIndex(new TileIndex(1, 1));
        connection1_2.setOutputTileIndex(new TileIndex(2, 1));
        parentComponents.add(connection1_2);

        Connection connection2_1 = new Connection(1f, false);
        connection2_1.setInputTileIndex(new TileIndex(0, 0));
        connection2_1.setOutputTileIndex(new TileIndex(1, 1));
        parentComponents.add(connection2_1);

        Connection connection2_2 = new Connection(0.75f, false);
        connection2_2.setInputTileIndex(new TileIndex(1, 1));
        connection2_2.setOutputTileIndex(new TileIndex(1, 2));
        parentComponents.add(connection2_2);

        Map<Integer, List<SpecimenComponent>> mappedComponents = Crossoverer.mapParentsComponents(parentComponents);

        List<SpecimenComponent> components = Crossoverer.listOffspringComponents(mappedComponents);

        assertNotNull(mappedComponents);
        int componentsTotal = components.size();
//        System.out.println("componentsTotal = " + componentsTotal);
        assertTrue(1 <= componentsTotal && componentsTotal <= 3);
//        for (SpecimenComponent component : components) {
//            float weight = ((Connection) component).getWeight();
//            System.out.println("weight = " + weight);
//        }
    }

    @Test
    void mapConnectionsFromComponentList() {
        List<SpecimenComponent> listedComponents = new ArrayList<>();

        Cell cell1_1 = new Muscle(0f, false);
        cell1_1.setTileIndex(new TileIndex(0, 0));
        listedComponents.add(cell1_1);

        Cell cell1_2 = new Muscle(0f, false);
        cell1_2.setTileIndex(new TileIndex(1, 1));
        listedComponents.add(cell1_2);

        Connection connection1_1 = new Connection(0f, false);
        connection1_1.setInputTileIndex(new TileIndex(0, 0));
        connection1_1.setOutputTileIndex(new TileIndex(1, 1));
        listedComponents.add(connection1_1);

        Connection connection1_2 = new Connection(0.25f, false);
        connection1_2.setInputTileIndex(new TileIndex(1, 1));
        connection1_2.setOutputTileIndex(new TileIndex(2, 1));
        listedComponents.add(connection1_2);

        Map<Integer, Connection> mappedConnections = Crossoverer.mapConnectionsFromComponentList(listedComponents);

        assertNotNull(mappedConnections);
        assertEquals(2, mappedConnections.size());
    }

    @Test
    void saveElite() {
        List<Specimen> species = SpecimenBuilder.generateSpecies(100);

        List<Specimen> elite = Crossoverer.saveElite(species, 10);

        assertEquals(10, elite.size());

        for (int i = 0; i < 10; i++) {
            assertTrue(elite.get(i).isCopiedVersionOf(species.get(i)));
        }
    }
}
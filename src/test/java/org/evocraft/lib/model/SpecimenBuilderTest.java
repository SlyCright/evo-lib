package org.evocraft.lib.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import processing.core.PVector;

class SpecimenBuilderTest {

    @RepeatedTest(10)
    void buildZeroGenerationSpecimenAt() {
        SpecimenBuilder specimenBuilder = new SpecimenBuilder();

        Specimen specimen = specimenBuilder.generateSpecimen();

        Optional<TileIndex> nullGridPlace = specimen.getComponents().stream()
                .filter(c -> c instanceof Cell)
                .map(c -> (Cell) c)
                .map(Cell::getTileIndex)
                .filter(Objects::isNull)
                .findFirst();

        assertFalse(nullGridPlace.isPresent());
    }

    @Test
    void generateSpecies() {
        int specimensTotal = 12;
        ArrayList<Specimen> species = SpecimenBuilder.generateSpecies(specimensTotal);

        assertTrue(species.size() == specimensTotal);
        for (Specimen specimen : species) {
            assertNotNull(specimen);
        }
    }

    @Test
    void generateSpecimen() {
        assertNotNull(SpecimenBuilder.generateSpecimen());
    }

    @Test
    void createSpecimenOfCellsAndConnections() {
        Map<Integer, Cell> cells=CellsBuilder.generateCells(12);
        Map<Integer, Connection> connections=ConnectionBuilder.generateConnections(cells,120);

        Specimen specimen=SpecimenBuilder.createSpecimenOfCellsAndConnections(cells,connections);

        Connection notNullConnection=null;
        for (SpecimenComponent component : specimen.getComponents()) {
            if(component instanceof Connection){
                notNullConnection= (Connection) component;
            }
        }
        assertNotNull(notNullConnection);

    }
}
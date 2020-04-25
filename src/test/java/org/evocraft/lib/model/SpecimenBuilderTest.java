package org.evocraft.lib.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import processing.core.PVector;

class SpecimenBuilderTest {

    @RepeatedTest(10)
    public void getRandomPlaceNextTo() {
        SpecimenBuilder specimenBuilder = new SpecimenBuilder();

        GridPlace gridPlace = new GridPlace(0, 0);

        GridPlace result = specimenBuilder.getRandomPlaceNextTo(gridPlace);

        int sum = result.i + result.j;

        assertTrue(sum == 2 || sum == -2);
    }

    @RepeatedTest(10)
    void cellMapFilling() throws NoSuchFieldException, IllegalAccessException {
        SpecimenBuilder specimenBuilder = new SpecimenBuilder();

        Map<GridPlace, Cell> gridPlaceCellMap = specimenBuilder.cellsMapFilling();

        assertTrue(gridPlaceCellMap.size() > 1);
    }

    @Test()
    void linkCellsBetweenNodes() {
        SpecimenBuilder specimenBuilder = new SpecimenBuilder();
        Specimen specimen = specimenBuilder.buildZeroGenerationSpecimenAt(new PVector(0f, 0f));
        List<SpecimenComponent> specimenComponents = specimen.getComponents();
        for (SpecimenComponent component : specimenComponents) {
            if (component instanceof Cell) {
                List<Cell> cells = ((Cell) component).getAdjacentCells();
                for (Cell cell : cells) {
                    List<Cell> adjacentCells = cell.getAdjacentCells();
                    for (Cell adjacentCell : adjacentCells) {
                        assertNotNull(adjacentCell);
                    }
                }
            }
        }
    }

    @RepeatedTest(10)
    void buildZeroGenerationSpecimenAt() {
        SpecimenBuilder specimenBuilder = new SpecimenBuilder();

        Specimen specimen=specimenBuilder.buildZeroGenerationSpecimenAt(new PVector(0f,0f));

        Optional<GridPlace> nullGridPlace = specimen.getComponents().stream()
            .filter(c -> c instanceof Cell)
            .map(c -> (Cell) c)
            .map(Cell::getGridPlace)
            .filter(Objects::isNull)
            .findFirst();

        assertFalse(nullGridPlace.isPresent());
    }

}
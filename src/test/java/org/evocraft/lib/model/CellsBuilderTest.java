package org.evocraft.lib.model;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CellsBuilderTest {

    @Test
    void generateCells() {
        int cellsTotal = 12;
        Map<Integer, Cell> cells = CellsBuilder.generateCells(cellsTotal);

        assertNotNull(cells);
        assertTrue(cells.size() > 0);
        assertTrue(cells.size() < cellsTotal);

        for (Cell cell : cells.values()) {
            assertNotNull(cell);
        }
    }

    @Test
    void createCellOfRandomType() {
        assertNotNull(CellsBuilder.createCellOfRandomType());

        boolean typeIsOk;
        typeIsOk = false;
        for (int i = 0; i < 1000; i++) {
            if (CellsBuilder.createCellOfRandomType() instanceof Muscle) {
                typeIsOk = true;
                break;
            }
        }
        assertTrue(typeIsOk);

        typeIsOk = false;
        for (int i = 0; i < 1000; i++) {
            if (CellsBuilder.createCellOfRandomType() instanceof Neuron) {
                typeIsOk = true;
                break;
            }
        }
        assertTrue(typeIsOk);

        typeIsOk = false;
        for (int i = 0; i < 1000; i++) {
            if (CellsBuilder.createCellOfRandomType() instanceof Oscillator) {
                typeIsOk = true;
                break;
            }
        }
        assertTrue(typeIsOk);
    }

    @RepeatedTest(10)
     void getRandomPlaceNextTo() {
        TileIndex tileIndex = new TileIndex(0, 0);
        TileIndex result = CellsBuilder.getRandomTileIndexNearbyTo(tileIndex);
        int sum = result.i + result.j;
        assertTrue(sum == 2 || sum == -2);
    }

}
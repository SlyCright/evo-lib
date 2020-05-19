package org.evocraft.lib.model;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CellsBuilderTest {

    @Test
    void generateCells() {
        int cellsTotal = 12;
        Map<Integer, Cell> cells = CellsBuilder.generateCells(cellsTotal);

        assertNotNull(cells);
        assertTrue(cells.size() == cellsTotal);
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

        int muscleCount = 0, oscillatorCount = 0, neuronCount = 0, fixerCount = 0;

        for (int i = 0; i < 10_000; i++) {
            Cell cell = CellsBuilder.createCellOfRandomType();
            if (cell instanceof Muscle) {
                muscleCount++;
            }
            if (cell instanceof Oscillator) {
                oscillatorCount++;
            }
            if (cell instanceof Neuron) {
                neuronCount++;
            }
            if (cell instanceof Fixer) {
                fixerCount++;
            }
        }

        System.out.println("muscleCount = " + muscleCount);
        System.out.println("oscillatorCount = " + oscillatorCount);
        System.out.println("neuronCount = " + neuronCount);
        System.out.println("fixerCount = " + fixerCount);
    }

    @RepeatedTest(10)
    void getRandomPlaceNextTo() {
        TileIndex tileIndex = new TileIndex(0, 0);
        TileIndex result = CellsBuilder.getRandomTileIndexNearbyTo(tileIndex);
        int sum = result.i + result.j;
        assertTrue(sum == 2 || sum == -2);
    }

    @Ignore
// @Test
    void ifHashCodesCollide() {

        Map<Integer, Cell> cells = new HashMap<>();

        int i = 0, j = 0;
        int iStep = 0, jStep = 0;

        while (true) {
            TileIndex tileIndex = new TileIndex(i, j);
            Muscle muscle = new Muscle(0f, false);
            muscle.setTileIndex(tileIndex);
            for (Cell value : cells.values()) {
                if (value.hashCode() == muscle.hashCode()) {
                    System.out.println(muscle.getTileIndex().i + ", " + muscle.getTileIndex().j + " - hash:" + muscle.hashCode());
                    System.out.println(value.getTileIndex().i + ", " + value.getTileIndex().j + " - hash:" + value.hashCode());
                    System.out.println("size: " + cells.size());
                    assertTrue(false);
                }
            }

            cells.put(muscle.hashCode(), muscle);

            if (Math.abs(i) == Math.abs(j)) {

                if (i > 0 && j > 0) {
                    i += 2;
                    j += 2;
                    iStep = 0;
                    jStep = -2;
                }

                if (i > 0 && j < 0) {
                    iStep = -2;
                    jStep = 0;
                }

                if (i < 0 && j < 0) {
                    iStep = 0;
                    jStep = 2;
                }

                if (i < 0 && j > 0) {
                    iStep = 2;
                    jStep = 0;
                }

                if (i == 0 && j == 0) {
                    i += 2;
                    j += 2;
                    iStep = 0;
                    jStep = -2;

                    Cell cell = new Muscle(0f, false);
                    cell.setTileIndex(new TileIndex(0, 0));
                    cells.put(cell.hashCode(), cell);
                }
            }
            i += iStep;
            j += jStep;
        }
    }
}
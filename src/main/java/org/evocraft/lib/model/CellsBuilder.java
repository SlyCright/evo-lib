package org.evocraft.lib.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CellsBuilder {

    final static private float MUSCLES_PORTION = 1f / 4f;
    final static private float OSCILLATORS_PORTION = 1f / 4f;
    final static private float NEURON_PORTION = 1f / 4f;
    final static private float FIXES_PORTION = 1f / 4f;

    public static Map<Integer, Cell> generateCells(int cellsTotal) {
        Map<Integer, Cell> cells = new HashMap<>();
        cells.put(new TileIndex(0, 0).hashCode(), createCellOfRandomType());

        while (cells.size() < cellsTotal) {
            insertRandomCell(cells);
        }

        return cells;
    }

    protected static void insertRandomCell(Map<Integer, Cell> cells) {
        if (cells.size() > 0) {
            int randomCellIndex = new Random().nextInt(cells.size());

            SpecimenComponent randomCell = cells.values().stream()
                    .skip(randomCellIndex)
                    .findFirst()
                    .get();

            TileIndex tileIndex = getRandomTileIndexNearbyTo(randomCell.getTileIndex());
            Cell cell = createCellOfRandomType();
            cell.setTileIndex(tileIndex);
            cells.put(cell.hashCode(), cell);
        }
    }

    protected static Cell createCellOfRandomType() {

        Cell cell = null;
        float randomCellType = new Random().nextFloat();

        if (0f < randomCellType && randomCellType < MUSCLES_PORTION) {
            cell = new Muscle(
                    Membrane.LENGTH * (1.5f - new Random().nextFloat()),
                    new Random().nextBoolean()); //todo refactor: make hardcoded value as constant
            return cell;
        }
        randomCellType -= MUSCLES_PORTION;

        if (0f < randomCellType && randomCellType < OSCILLATORS_PORTION) {
            cell = new Oscillator(
                    25 + new Random().nextInt(150),
                    new Random().nextFloat(),
                    new Random().nextBoolean()); //todo refactor: make hardcoded value as constant
            return cell;
        }
        randomCellType -= OSCILLATORS_PORTION;

        if (0f < randomCellType && randomCellType < NEURON_PORTION) {
            cell = new Neuron(
                    new Random().nextFloat(),
                    new Random().nextBoolean());
            return cell;
        }
        randomCellType -= NEURON_PORTION;

        if (0f < randomCellType && randomCellType < FIXES_PORTION) {
            cell = new Fixer(new Random().nextBoolean());
            return cell;
        }

        return cell;
    }

    protected static TileIndex getRandomTileIndexNearbyTo(TileIndex tileIndex) {
        int i = 0, j = 0;
        int directionsTotal = Direction.values().length;
        int randomDirection = new Random().nextInt(directionsTotal);
        Direction direction = Direction.values()[randomDirection];
        switch (direction) {
            case UP:
                i = tileIndex.i;
                j = tileIndex.j - 2;
                break;

            case RIGHT:
                i = tileIndex.i + 2;
                j = tileIndex.j;
                break;

            case DOWN:
                i = tileIndex.i;
                j = tileIndex.j + 2;
                break;

            case LEFT:
                i = tileIndex.i - 2;
                j = tileIndex.j;
                break;
        }
        return new TileIndex(i, j);
    }

}

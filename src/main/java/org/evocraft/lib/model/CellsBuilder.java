package org.evocraft.lib.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CellsBuilder {

    final static private float MUSCLES_PORTION = 1f / 3f;
    final static private float OSCILLATORS_PORTION = 1f / 3f;
    final static private float NEURON_PORTION = 1f / 3f;

    public static Map<Integer, Cell> generateCells(int cellsTotal) {

        TileIndex tileIndex = new TileIndex(0, 0);
        Cell cell;
        Map<Integer, Cell> cells = new HashMap<>();

        do {

            cell = createCellOfRandomType();
            if (cell != null) {
                cell.setTileIndex(tileIndex);
                cells.put(cell.getTileIndex().hashCode(), cell);
            }
            int randomCellIndex = new Random().nextInt(cells.size());
            SpecimenComponent randomCell = cells.values().stream()
                    .skip(randomCellIndex)
                    .findFirst()
                    .get();
            tileIndex = getRandomTileIndexNearbyTo(randomCell.getTileIndex());

        } while (cells.size() != cellsTotal);

        return cells;
    }

    protected static Cell createCellOfRandomType() {

        Cell cell = null;
        float randomCellType = new Random().nextFloat();

        if (0f < randomCellType
                && randomCellType < MUSCLES_PORTION) {
            cell = new Muscle(Membrane.LENGTH * (1.5f - new Random().nextFloat())); //todo refactor: make hardcoded value as constant
        }

        if (MUSCLES_PORTION < randomCellType
                && randomCellType < MUSCLES_PORTION + OSCILLATORS_PORTION) {
            cell = new Oscillator(new Random().nextInt(150)); //todo refactor: make hardcoded value as constant
        }

        if (MUSCLES_PORTION + OSCILLATORS_PORTION < randomCellType
                && randomCellType < MUSCLES_PORTION + OSCILLATORS_PORTION + NEURON_PORTION) {
            cell = new Neuron(new Random().nextFloat());
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

package org.evocraft.lib.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SpecimenBuilder {

    final private int WORLD_LENGTH = 1024, WORLD_HEIGHT = 768; //todo varaibles should be in "world" class
    final private int CELLS_TOTAL = 12;

    public Specimen buildSpecimen() {

        Map<GridPlace, Cell> cellMapping = cellMapFilling();
        placeNodesInCells(cellMapping);

        return null;
    }

    private void placeNodesInCells(Map<GridPlace, Cell> cellMapping) {
        for (GridPlace gridPlace : cellMapping.keySet()) {
            Cell cell = cellMapping.get(gridPlace);
            for (Direction direction : Direction.values()) {
                switch (direction) {
                    case UP:
                        Node upLeftNode = cell.getUpLeftNode() == null
                            ? new Node()
                            : cell.getUpLeftNode();

                        cell.setUpLeftNode(upLeftNode);
                        upLeftNode.setDownRightCell(cell);
todo a lot of things
                        break;
                }
            }
        }
    }

    protected Map<GridPlace, Cell> cellMapFilling() {
        Map<GridPlace, Cell> cellMapping = new HashMap<>();
        Cell cell = new Cell();
        GridPlace gridPlace = new GridPlace(0, 0);
        cellMapping.put(gridPlace, cell);

        for (int i = 0; i < CELLS_TOTAL - 1; i++) {

            int size = cellMapping.size();
            int randomCellIndex = new Random().nextInt(size);

            gridPlace = cellMapping.keySet().stream()
                .skip(randomCellIndex)
                .findFirst()
                .get();

            GridPlace randomPlace = getRandomPlaceNextTo(gridPlace);
            cellMapping.put(randomPlace, new Cell());
        }
        return cellMapping;
    }

    protected GridPlace getRandomPlaceNextTo(GridPlace gridPlace) {
        int i = 0, j = 0;
        int directionsTotal = Direction.values().length;
        int randomDirection = new Random().nextInt(directionsTotal);
        Direction direction = Direction.values()[randomDirection];
        switch (direction) {
            case UP:
                i = gridPlace.i;
                j = gridPlace.j - 1;
                break;

            case RIGHT:
                i = gridPlace.i + 1;
                j = gridPlace.j;
                break;

            case DOWN:
                i = gridPlace.i;
                j = gridPlace.j + 1;
                break;

            case LEFT:
                i = gridPlace.i - 1;
                j = gridPlace.j;
                break;
        }
        return new GridPlace(i, j);
    }

}





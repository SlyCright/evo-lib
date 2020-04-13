package org.evocraft.lib.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import processing.core.PVector;

public class SpecimenBuilder {

    final private int CELLS_TOTAL = 12;
    final private float GRID_MASH_SIZE = 75f;
    final private float WINDOW_WIDTH = 999, WINDOW_HEIGHT = 666;

    public Specimen buildSpecimen() {

        Map<GridPlace, Cell> cellsMapping = cellsMapFilling();
        Map<GridPlace, Node> nodesMapping = nodesMapFilling(cellsMapping);
        linkCellsBetweenNodes(cellsMapping, nodesMapping);
        setEuclidPositionsToNodes(nodesMapping);
        Specimen specimen = placeComponentsInSpecimen(cellsMapping, nodesMapping);
        return specimen;
    }


    protected Map<GridPlace, Cell> cellsMapFilling() {
        Map<GridPlace, Cell> cellsMapping = new HashMap<>();
        Cell cell = new Cell();
        GridPlace gridPlace = new GridPlace(0, 0);
        cellsMapping.put(gridPlace, cell);

        for (int i = 0; i < CELLS_TOTAL - 1; i++) {

            int size = cellsMapping.size();
            int randomCellIndex = new Random().nextInt(size);

            gridPlace = cellsMapping.keySet().stream()
                .skip(randomCellIndex)
                .findFirst()
                .get();

            GridPlace randomPlace = getRandomPlaceNextTo(gridPlace);
            cellsMapping.put(randomPlace, new Cell());
        }
        return cellsMapping;
    }

    protected GridPlace getRandomPlaceNextTo(GridPlace gridPlace) {
        int i = 0, j = 0;
        int directionsTotal = Direction.values().length;
        int randomDirection = new Random().nextInt(directionsTotal);
        Direction direction = Direction.values()[randomDirection];
        switch (direction) {
            case UP:
                i = gridPlace.i;
                j = gridPlace.j - 2;
                break;

            case RIGHT:
                i = gridPlace.i + 2;
                j = gridPlace.j;
                break;

            case DOWN:
                i = gridPlace.i;
                j = gridPlace.j + 2;
                break;

            case LEFT:
                i = gridPlace.i - 2;
                j = gridPlace.j;
                break;
        }
        return new GridPlace(i, j);
    }

    private Map<GridPlace, Node> nodesMapFilling(Map<GridPlace, Cell> cellsMapping) {
        Map<GridPlace, Node> nodesMap = new HashMap<>();
        int i, j;
        float posX, posY;
        PVector euclidPosition;
        GridPlace nodeGridPlace;
        for (GridPlace cellGridPlace : cellsMapping.keySet()) {
            Cell cell = cellsMapping.get(cellGridPlace);
            for (Direction direction : Direction.values()) {
                switch (direction) {
                    case UP:
                        i = cellGridPlace.i - 1;
                        j = cellGridPlace.j - 1;
                        nodeGridPlace = new GridPlace(i, j);
                        nodesMap.put(nodeGridPlace, new Node());
                        break;
                    case LEFT:
                        i = cellGridPlace.i + 1;
                        j = cellGridPlace.j - 1;
                        nodeGridPlace = new GridPlace(i, j);
                        nodesMap.put(nodeGridPlace, new Node());
                        break;
                    case DOWN:
                        i = cellGridPlace.i + 1;
                        j = cellGridPlace.j + 1;
                        nodeGridPlace = new GridPlace(i, j);
                        nodesMap.put(nodeGridPlace, new Node());
                        break;
                    case RIGHT:
                        i = cellGridPlace.i - 1;
                        j = cellGridPlace.j + 1;
                        nodeGridPlace = new GridPlace(i, j);
                        nodesMap.put(nodeGridPlace, new Node());
                        break;
                }
            }
        }
        return nodesMap;
    }

    private void linkCellsBetweenNodes(Map<GridPlace, Cell> cellsMapping, Map<GridPlace, Node> nodesMapping) {
        int i, j;
        Cell cell;
        GridPlace nodeGradePlace;
        Node node;
        for (GridPlace cellGridPlace : cellsMapping.keySet()) {
            cell = cellsMapping.get(cellGridPlace);
            for (Direction direction : Direction.values()) {
                switch (direction) {
                    case UP:
                        i = cellGridPlace.i - 1;
                        j = cellGridPlace.j - 1;
                        nodeGradePlace = new GridPlace(i, j);
                        node = nodesMapping.get(nodeGradePlace);
                        cell.getNodes().add(node);
                        node.getCells().add(cell);
                        break;
                    case RIGHT:
                        i = cellGridPlace.i + 1;
                        j = cellGridPlace.j - 1;
                        nodeGradePlace = new GridPlace(i, j);
                        node = nodesMapping.get(nodeGradePlace);
                        cell.getNodes().add(node);
                        node.getCells().add(cell);
                        break;
                    case DOWN:
                        i = cellGridPlace.i + 1;
                        j = cellGridPlace.j + 1;
                        nodeGradePlace = new GridPlace(i, j);
                        node = nodesMapping.get(nodeGradePlace);
                        cell.getNodes().add(node);
                        node.getCells().add(cell);
                        break;
                    case LEFT:
                        i = cellGridPlace.i - 1;
                        j = cellGridPlace.j + 1;
                        nodeGradePlace = new GridPlace(i, j);
                        node = nodesMapping.get(nodeGradePlace);
                        cell.getNodes().add(node);
                        node.getCells().add(cell);
                        break;
                }
            }
        }
    }

    private void setEuclidPositionsToNodes(Map<GridPlace, Node> nodesMapping) {
        int i, j;
        float posX, posY;
        Node node;
        for (GridPlace gridPlace : nodesMapping.keySet()) {
            node = nodesMapping.get(gridPlace);

            posX = WINDOW_WIDTH / 2f + (((float) gridPlace.i + 1f) / 2f - 1f) * GRID_MASH_SIZE;
            posY = WINDOW_HEIGHT / 2f + (((float) gridPlace.j + 1f) / 2f - 1f) * GRID_MASH_SIZE;

            node.setPosition(new PVector(posX, posY));
        }
    }

    private Specimen placeComponentsInSpecimen(Map<GridPlace, Cell> cellsMapping, Map<GridPlace, Node> nodesMapping) {
        Specimen specimen = new Specimen();
        for (Cell cell : cellsMapping.values()) {
            specimen.getSpecimenComponents().add(cell);
        }
        for (Node node : nodesMapping.values()) {
            specimen.getSpecimenComponents().add(node);
        }
        return specimen;
    }

}





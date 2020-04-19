package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.Data;
import processing.core.PVector;

@Data
public class SpecimenBuilder {

    final private int CELLS_TOTAL = 50;
    final private float MUSCLES_PORTION = 1f / 3f;
    final private int CONNECTIONS_TOTAL = CELLS_TOTAL / 2;
    final private float GRID_MASH_SIZE = 75f;
    final private float INITIAL_X_OF_SPECIMEN = 999f / 2f, INITIAL_Y_OF_SPECIMEN = 666f / 2f;
    final private boolean IF_ARRANGE_NODES_NEEDED = true;

    public Specimen buildSpecimen() {

        Map<GridPlace, Cell> cellsMapping = cellsMapFilling();
        Map<GridPlace, Node> nodesMapping = nodesMapFilling(cellsMapping);
        List<Connection> connections = generateConnections(cellsMapping);
        linkCellsBetweenNodes(cellsMapping, nodesMapping);
        linkNodesBetweenNodes(nodesMapping);
        setEuclidPositionsToNodes(nodesMapping);
        Specimen specimen = placeComponentsInSpecimen(cellsMapping, nodesMapping, connections);
        return specimen;
    }

    private List<Connection> generateConnections(Map<GridPlace, Cell> cellsMapping) {
        List<Connection> connections = new ArrayList<>();
        Cell inputCell, outPutCell;
        Connection connection;
        for (int i = 0; i < CONNECTIONS_TOTAL; i++) {
            inputCell = getRandomCellOf(cellsMapping); //todo backlog: DNA here
            outPutCell = getRandomCellOf(cellsMapping); //todo backlog: DNA here
            connection = new Connection(new Random().nextFloat()); //todo backlog: DNA here
            connection.setInput(inputCell);
            connection.setOutput(outPutCell);
            inputCell.getOutput().add(connection);
            outPutCell.getInput().add(connection);
            connections.add(connection);
        }
        return connections;
    }

    private Cell getRandomCellOf(Map<GridPlace, Cell> cellsMapping) {
        int size = cellsMapping.size();
        int randomCellIndex;
        randomCellIndex = new Random().nextInt(size);
        GridPlace randomPlace = cellsMapping.keySet().stream()
                .skip(randomCellIndex)
                .findFirst()
                .get();
        return cellsMapping.get(randomPlace);
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

            if (new Random().nextFloat() < MUSCLES_PORTION) {
                cell = new Muscle();
            } else {
                cell = new Cell();
            }

            cellsMapping.put(randomPlace, cell);
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

    protected Map<GridPlace, Node> nodesMapFilling(Map<GridPlace, Cell> cellsMapping) {
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

    //todo refactor: better use "bind" instead of "link"
    protected void linkCellsBetweenNodes(Map<GridPlace, Cell> cellsMapping, Map<GridPlace, Node> nodesMapping) {
        int adjacentNodeI = 0, adjacentNodeJ = 0;
        int adjacentCellI = 0, adjacentCellJ = 0;
        Cell cell;
        GridPlace adjacentNodeGridPlace;
        GridPlace adjacentCellGridPlace;
        Node adjacentNode;
        Cell adjacentCell;
        for (GridPlace cellGridPlace : cellsMapping.keySet()) {
            cell = cellsMapping.get(cellGridPlace);
            for (Direction direction : Direction.values()) {

                switch (direction) {
                    case UP:
                        adjacentNodeI = cellGridPlace.i - 1;
                        adjacentNodeJ = cellGridPlace.j - 1;
                        adjacentCellI = cellGridPlace.i;
                        adjacentCellJ = cellGridPlace.j - 2;
                        break;
                    case RIGHT:
                        adjacentNodeI = cellGridPlace.i + 1;
                        adjacentNodeJ = cellGridPlace.j - 1;
                        adjacentCellI = cellGridPlace.i + 2;
                        adjacentCellJ = cellGridPlace.j;
                        break;
                    case DOWN:
                        adjacentNodeI = cellGridPlace.i + 1;
                        adjacentNodeJ = cellGridPlace.j + 1;
                        adjacentCellI = cellGridPlace.i;
                        adjacentCellJ = cellGridPlace.j + 2;
                        break;
                    case LEFT:
                        adjacentNodeI = cellGridPlace.i - 1;
                        adjacentNodeJ = cellGridPlace.j + 1;
                        adjacentCellI = cellGridPlace.i - 2;
                        adjacentCellJ = cellGridPlace.j;
                        break;
                }

                adjacentNodeGridPlace = new GridPlace(adjacentNodeI, adjacentNodeJ);
                adjacentNode = nodesMapping.get(adjacentNodeGridPlace);
                cell.getAdjacentNodes().add(adjacentNode);
                adjacentNode.getAdjacentCells().add(cell);
//todo refactor: here is method linkCellsBetweenCells
                adjacentCellGridPlace = new GridPlace(adjacentCellI, adjacentCellJ);
                adjacentCell = cellsMapping.get(adjacentCellGridPlace);
                if (adjacentCell != null) {
                    cell.getAdjacentCells().add(adjacentCell);
                }
            }
        }
    }

    private void linkNodesBetweenNodes(Map<GridPlace, Node> nodesMapping) {
        int adjacentNodeI = 0, adjacentNodeJ = 0;
        GridPlace adjacentNodeGridPlace;
        Node adjacentNode;
        Node node;
        for (GridPlace cellGridPlace : nodesMapping.keySet()) {
            node = nodesMapping.get(cellGridPlace);
            for (Direction direction : Direction.values()) {
                switch (direction) {
                    case UP:
                        adjacentNodeI = cellGridPlace.i;
                        adjacentNodeJ = cellGridPlace.j - 2;
                        break;
                    case RIGHT:
                        adjacentNodeI = cellGridPlace.i + 2;
                        adjacentNodeJ = cellGridPlace.j;
                        break;
                    case DOWN:
                        adjacentNodeI = cellGridPlace.i;
                        adjacentNodeJ = cellGridPlace.j + 2;
                        break;
                    case LEFT:
                        adjacentNodeI = cellGridPlace.i - 2;
                        adjacentNodeJ = cellGridPlace.j;
                        break;
                }

                adjacentNodeGridPlace = new GridPlace(adjacentNodeI, adjacentNodeJ);
                adjacentNode = nodesMapping.get(adjacentNodeGridPlace);
                if (adjacentNode != null) {
                    node.getAdjacentNodes().add(adjacentNode);
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

            posX = INITIAL_X_OF_SPECIMEN + new Random().nextFloat();
            posY = INITIAL_Y_OF_SPECIMEN + new Random().nextFloat();

            if (IF_ARRANGE_NODES_NEEDED) {
                posX += (((float) gridPlace.i + 1f) / 2f - 1f) * GRID_MASH_SIZE;
                posY += (((float) gridPlace.j + 1f) / 2f - 1f) * GRID_MASH_SIZE;
            }

            node.setPosition(new PVector(posX, posY));
        }
    }

    private Specimen placeComponentsInSpecimen(Map<GridPlace, Cell> cellsMapping, Map<GridPlace, Node> nodesMapping, List<Connection> connections) {
        Specimen specimen = new Specimen();

        for (Cell cell : cellsMapping.values()) {
            specimen.getSpecimenComponents().add(cell);
        }

        for (Node node : nodesMapping.values()) {
            specimen.getSpecimenComponents().add(node);
        }

        for (Connection connection : connections) {
            specimen.getSpecimenComponents().add(connection);
        }

        return specimen;
    }

}





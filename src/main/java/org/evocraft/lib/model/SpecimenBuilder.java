package org.evocraft.lib.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;

@Getter
@Setter
public class SpecimenBuilder { //todo refactor: the class is too large make several classes

    final private int CELLS_TOTAL = 12;
    final private float MUSCLES_PORTION = 1f / 3f;
    final private float OSCILLATORS_PORTION = 1f / 3f;
    final private float NEURON_PORTION = 1f / 3f;
    final private int CONNECTIONS_TOTAL = CELLS_TOTAL / 2;
    final private float GRID_MASH_SIZE = 75f;
    final private boolean IF_ARRANGE_NODES_NEEDED = true;

    public Specimen buildZeroGenerationSpecimenAt(PVector initialPosition) {
        Map<GridPlace, Cell> cellsMapping = cellsMapFilling();
        return makeSpecimenOfCellsAndConnections(cellsMapping, null, initialPosition);
    }

    public Specimen buildOffspringSpecimenOf(Specimen specimenOne, Specimen specimenTwo, PVector initialPosition) { //todo refactor: instead of position argument make separated method moveSpecimenToPosition(Specimen, Position)
        Map<GridPlace, Cell> cellsMapping = cellsMapFilling(specimenOne, specimenTwo);
        Map<GridPlaces, Connection> = connectionsMapFilling(specimenOne, specimenTwo);
        return makeSpecimenOfCellsAndConnections(cellsMapping, null, initialPosition);
    }

    protected Map<GridPlaces, Connection> connectionsMapFilling(Specimen ancestorOne, Specimen ancestorTwo) {
        Map<GridPlaces, Cell> offspringCellsMap = new HashMap<>();
        Map<GridPlace, PairOfAncestorsCells> pairOfAncestorsCellsMap = new HashMap<>();
        Map<GridPlace, Cell> cellsMapOfAncestorOne = generateCellsMapOf(ancestorOne);
        Map<GridPlace, Cell> cellsMapOfAncestorTwo = generateCellsMapOf(ancestorTwo);
        Cell cellOne, cellTwo;

        for (GridPlace gridPlaceOne : cellsMapOfAncestorOne.keySet()) {

            GridPlace gridPlaceOfPair = new GridPlace(gridPlaceOne.i, gridPlaceOne.j);
            PairOfAncestorsCells pairOfAncestorsCells = new PairOfAncestorsCells();
            pairOfAncestorsCellsMap.put(gridPlaceOfPair, pairOfAncestorsCells);

            cellOne = cellsMapOfAncestorOne.get(gridPlaceOne);
            pairOfAncestorsCells.setOne(cellOne);
        }

        for (GridPlace gridPlaceTwo : cellsMapOfAncestorTwo.keySet()) {

            GridPlace gridPlaceOfPair = new GridPlace(gridPlaceTwo.i, gridPlaceTwo.j);
            PairOfAncestorsCells pairOfAncestorsCells = pairOfAncestorsCellsMap.get(gridPlaceOfPair);
            if (pairOfAncestorsCells == null) {
                pairOfAncestorsCells = new PairOfAncestorsCells();
                pairOfAncestorsCellsMap.put(gridPlaceOfPair, pairOfAncestorsCells);
            }

            cellTwo = cellsMapOfAncestorOne.get(gridPlaceTwo);
            pairOfAncestorsCells.setTwo(cellTwo);
        }

        for (GridPlace gridPlace : pairOfAncestorsCellsMap.keySet()) {
            GridPlace gridPlaceOffspring = new GridPlace(gridPlace.i, gridPlace.j);
            Cell offspringCell = null;

            if (new Random().nextFloat() < 0.5f) {
                Cell one = pairOfAncestorsCellsMap.get(gridPlace).getOne();
                if (one != null) {
                    offspringCell = one.copy();
                }
            } else {
                Cell two = pairOfAncestorsCellsMap.get(gridPlace).getTwo();
                if (two != null) {
                    offspringCell = two.copy();
                }
            }

            if (offspringCell != null) {
                offspringCellsMap.put(gridPlaceOffspring, offspringCell);
            }
        }

        return offspringCellsMap;
    }

    private Specimen makeSpecimenOfCellsAndConnections(Map<GridPlace, Cell> cellsMapping, List<Connection> connectionsList, PVector initialPosition) {
        Map<GridPlace, Node> nodesMapping = nodesMapFilling(cellsMapping);
        Map<GridPlaces, Connection> connections = generateConnections(cellsMapping);
        linkCellsBetweenNodes(cellsMapping, nodesMapping);
        linkNodesBetweenNodes(nodesMapping);
        setEuclidPositionsToNodes(nodesMapping, initialPosition);
        return placeComponentsInSpecimen(cellsMapping, nodesMapping, connections);
    }

    protected Map<GridPlace, Cell> cellsMapFilling(Specimen ancestorOne, Specimen ancestorTwo) {
        Map<GridPlace, Cell> offspringCellsMap = new HashMap<>();
        Map<GridPlace, PairOfAncestorsCells> pairOfAncestorsCellsMap = new HashMap<>();
        Map<GridPlace, Cell> cellsMapOfAncestorOne = generateCellsMapOf(ancestorOne);
        Map<GridPlace, Cell> cellsMapOfAncestorTwo = generateCellsMapOf(ancestorTwo);
        Cell cellOne, cellTwo;

        for (GridPlace gridPlaceOne : cellsMapOfAncestorOne.keySet()) {

            GridPlace gridPlaceOfPair = new GridPlace(gridPlaceOne.i, gridPlaceOne.j);
            PairOfAncestorsCells pairOfAncestorsCells = new PairOfAncestorsCells();
            pairOfAncestorsCellsMap.put(gridPlaceOfPair, pairOfAncestorsCells);

            cellOne = cellsMapOfAncestorOne.get(gridPlaceOne);
            pairOfAncestorsCells.setOne(cellOne);
        }

        for (GridPlace gridPlaceTwo : cellsMapOfAncestorTwo.keySet()) {

            GridPlace gridPlaceOfPair = new GridPlace(gridPlaceTwo.i, gridPlaceTwo.j);
            PairOfAncestorsCells pairOfAncestorsCells = pairOfAncestorsCellsMap.get(gridPlaceOfPair);
            if (pairOfAncestorsCells == null) {
                pairOfAncestorsCells = new PairOfAncestorsCells();
                pairOfAncestorsCellsMap.put(gridPlaceOfPair, pairOfAncestorsCells);
            }

            cellTwo = cellsMapOfAncestorOne.get(gridPlaceTwo);
            pairOfAncestorsCells.setTwo(cellTwo);
        }

        for (GridPlace gridPlace : pairOfAncestorsCellsMap.keySet()) {
            GridPlace gridPlaceOffspring = new GridPlace(gridPlace.i, gridPlace.j);
            Cell offspringCell = null;

            if (new Random().nextFloat() < 0.5f) {
                Cell one = pairOfAncestorsCellsMap.get(gridPlace).getOne();
                if (one != null) {
                    offspringCell = one.copy();
                }
            } else {
                Cell two = pairOfAncestorsCellsMap.get(gridPlace).getTwo();
                if (two != null) {
                    offspringCell = two.copy();
                }
            }

            if (offspringCell != null) {
                offspringCellsMap.put(gridPlaceOffspring, offspringCell);
            }
        }

        return offspringCellsMap;
    }

    private Map<GridPlace, Cell> generateCellsMapOf(Specimen specimen) {
        Map<GridPlace, Cell> cellsMap = new HashMap<>();
        for (SpecimenComponent component : specimen.getComponents()) {
            if (component instanceof Cell) {
                Cell cell = (Cell) component;
                cellsMap.put(cell.getGridPlace(), cell);
            }
        }
        return cellsMap;
    }


    protected Map<GridPlace, Cell> cellsMapFilling() {
        Map<GridPlace, Cell> cellsMapping = new HashMap<>();
        Cell cell = new Neuron(0f); //todo backlog: DNA here
        GridPlace gridPlace = new GridPlace(0, 0);
        cell.setGridPlace(gridPlace);
        cellsMapping.put(gridPlace, cell);

        for (int i = 0; i < CELLS_TOTAL - 1; i++) {

            int size = cellsMapping.size();
            int randomCellIndex = new Random().nextInt(size);

            gridPlace = cellsMapping.keySet().stream()
                .skip(randomCellIndex)
                .findFirst()
                .get();

            GridPlace randomPlace = getRandomPlaceNextTo(gridPlace);

            float randomCellType = new Random().nextFloat();

            if (0f < randomCellType && randomCellType < MUSCLES_PORTION) {
                cell = new Muscle(Cell.CELL_SIZE * (1.5f - new Random().nextFloat())); //todo refactor: make hardcoded value as constant
            }

            if (MUSCLES_PORTION < randomCellType && randomCellType < MUSCLES_PORTION + OSCILLATORS_PORTION) {
                cell = new Oscillator(new Random().nextInt(150)); //todo refactor: make hardcoded value as constant
            }

            if (MUSCLES_PORTION + OSCILLATORS_PORTION < randomCellType && randomCellType < 1f) {
                cell = new Neuron(new Random().nextFloat());
            }
            cell.setGridPlace(randomPlace);
            cellsMapping.put(randomPlace, cell);
        }
        return cellsMapping;
    }

    private Map<GridPlaces, Connection> generateConnections(Map<GridPlace, Cell> cellsMapping) {
        Map<GridPlaces, Connection> connections = new HashMap<>();
        Cell inputCell, outPutCell;
        Connection connection;
        for (int i = 0; i < CONNECTIONS_TOTAL; i++) {
            inputCell = getRandomCellOf(cellsMapping); //todo backlog: DNA here
            outPutCell = getRandomCellOf(cellsMapping); //todo backlog: DNA here
            connection = new Connection(new Random().nextFloat()); //todo backlog: DNA here
            connection.setInput(inputCell);
            connection.setOutput(outPutCell);

            GridPlace inputCellGridPlace = inputCell.getGridPlace();
            GridPlace outputCellGridPlace = outPutCell.getGridPlace();
            GridPlaces gridPlaces = new GridPlaces(inputCellGridPlace, outputCellGridPlace); //todo refactor: make this line shorter
            connection.setGridPlaces(gridPlaces);

            inputCell.getOutputConnections().add(connection);
            outPutCell.getInputConnections().add(connection);
            connections.put(gridPlaces, connection);
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

    private void linkNodesBetweenNodes(Map<GridPlace, Node> nodesMapping) { //todo bugfix: redundant links appears
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

    private void setEuclidPositionsToNodes(Map<GridPlace, Node> nodesMapping, PVector initialPosition) {
        int i, j;
        float posX, posY;
        Node node;
        for (GridPlace gridPlace : nodesMapping.keySet()) {
            node = nodesMapping.get(gridPlace);

            posX = initialPosition.x + new Random().nextFloat();
            posY = initialPosition.y + new Random().nextFloat();

            if (IF_ARRANGE_NODES_NEEDED) {
                posX += (((float) gridPlace.i + 1f) / 2f - 1f) * GRID_MASH_SIZE;
                posY += (((float) gridPlace.j + 1f) / 2f - 1f) * GRID_MASH_SIZE;
            }

            node.setPosition(new PVector(posX, posY));
        }
    }

    private Specimen placeComponentsInSpecimen(
        Map<GridPlace, Cell> cellsMapping,
        Map<GridPlace, Node> nodesMapping,
        Map<GridPlaces, Connection> connections) {
        Specimen specimen = new Specimen();

        for (Cell cell : cellsMapping.values()) {
            specimen.getComponents().add(cell);
        }

        for (Node node : nodesMapping.values()) {
            specimen.getComponents().add(node);
        }

        for (Connection connection : connections.values()) {
            specimen.getComponents().add(connection);
        }

        return specimen;
    }

}





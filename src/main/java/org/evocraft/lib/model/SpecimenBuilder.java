package org.evocraft.lib.model;

import java.util.HashMap;
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
        Map<Integer, SpecimenComponent> components = new HashMap<>();
        generateCellsIn(components);
        generateConnectionsIn(components);
        return makeSpecimenOf(components, initialPosition);
    }

    public Specimen buildOffspringSpecimenOf(Specimen specimenLeft, Specimen specimenRight, PVector initialPosition) { //todo refactor: instead of position argument make separated method moveSpecimenToPosition(Specimen, Position)
        Map<Integer, SpecimenComponent> offspringComponents = mapOffspringComponents(specimenLeft, specimenRight);
        return makeSpecimenOf(offspringComponents, initialPosition);
    }

    protected Map<Integer, SpecimenComponent> mapOffspringComponents(Specimen ancestorLeft, Specimen ancestorRight) {
        Map<Integer, SpecimenComponent> offspringComponents = new HashMap<>();
        Map<Integer, SpecimenComponent> componentsOfAncestorLeft = mapComponentsFrom(ancestorLeft);
        Map<Integer, SpecimenComponent> componentsOfAncestorRight = mapComponentsFrom(ancestorRight);
        Map<Integer, PairOfAncestorsComponents> pairsOfAncestorsComponents = new HashMap<>();
        mapComponentsPair(pairsOfAncestorsComponents, componentsOfAncestorLeft, Pair.LEFT);
        mapComponentsPair(pairsOfAncestorsComponents, componentsOfAncestorRight, Pair.RIGHT);

        for (Integer key : pairsOfAncestorsComponents.keySet()) {

            PairOfAncestorsComponents pairOfAncestorsComponents = pairsOfAncestorsComponents.get(key);
            SpecimenComponent componentLeft = pairOfAncestorsComponents.getLeft();
            SpecimenComponent componentRight = pairOfAncestorsComponents.getRight();

            SpecimenComponent offspringComponent = null;

            if (new Random().nextFloat() < 0.5f) {
                if (componentLeft != null) {
                    offspringComponent = componentLeft.copy();
                }
            } else {
                if (componentRight != null) {
                    offspringComponent = componentRight.copy();
                }
            }

            if (offspringComponent != null) {
                offspringComponents.put(key, offspringComponent);
            }
        }

        return offspringComponents;
    }

    private void mapComponentsPair(
            Map<Integer, PairOfAncestorsComponents> pairsOfAncestorsComponents,
            Map<Integer, SpecimenComponent> componentsOfAncestor,
            Pair ancestorNumber) {

        for (SpecimenComponent component : componentsOfAncestor.values()) {
            int key = component.hashCode();
            PairOfAncestorsComponents pairOfAncestorsComponents = pairsOfAncestorsComponents.get(key);
            if (pairOfAncestorsComponents == null) {
                pairOfAncestorsComponents = new PairOfAncestorsComponents();
                pairsOfAncestorsComponents.put(key, pairOfAncestorsComponents);
            }
            switch (ancestorNumber) {
                case LEFT:
                    pairOfAncestorsComponents.setLeft(component);
                    break;
                case RIGHT:
                    pairOfAncestorsComponents.setRight(component);
                    break;
            }
        }
    }

    private Map<Integer, SpecimenComponent> mapComponentsFrom(Specimen specimen) {
        Map<Integer, SpecimenComponent> components = new HashMap<>();

        for (SpecimenComponent component : specimen.getComponents()) {
            if (component instanceof Cell || component instanceof Connection) {
                components.put(component.hashCode(), component);
            }
        }

        return components;
    }

    private Specimen makeSpecimenOf(Map<Integer, SpecimenComponent> components, PVector initialPosition) {
        addNodesTo(components);
        bindCellsBetweenCells(components);
        bindCellsBetweenNodes(components);
        bindNodesBetweenNodes(components);
        setEuclidPositionsToNodes(components, initialPosition);
        return placeComponentsInSpecimen(components);
    }

    protected void generateCellsIn(Map<Integer, SpecimenComponent> components) {

        Cell cell = null;
        GridPlace gridPlace = new GridPlace(0, 0);

        for (int i = 0; i < CELLS_TOTAL - 1; i++) {

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

            if (cell != null) {
                cell.setGridPlace(gridPlace);
                components.put(cell.hashCode(), cell);
            }

            int size = components.size();
            int randomCellIndex = new Random().nextInt(size);

            SpecimenComponent randomCell = components.values().stream()
                    .skip(randomCellIndex)
                    .findFirst()
                    .get();

            gridPlace = getRandomPlaceNextTo(((Cell) randomCell).getGridPlace());
        }
    }

    private void generateConnectionsIn(Map<Integer, SpecimenComponent> components) {
        Map<Integer, SpecimenComponent> cells = components;
        Cell inputCell, outPutCell;
        Connection connection;

        for (int i = 0; i < CONNECTIONS_TOTAL; i++) {
            connection = new Connection(new Random().nextFloat()); //todo backlog: DNA here

            inputCell = getRandomCellOf(cells); //todo backlog: DNA here
            outPutCell = getRandomCellOf(cells); //todo backlog: DNA here

            connection.setInput(inputCell);
            connection.setOutput(outPutCell);

            connection.setInputGridPlace(inputCell.getGridPlace());
            connection.setOutputGridPlace(outPutCell.getGridPlace());

            inputCell.getOutputConnections().add(connection);
            outPutCell.getInputConnections().add(connection);

            components.put(connection.hashCode(), connection);
        }
    }

    private Cell getRandomCellOf(Map<Integer, SpecimenComponent> cells) {
        int size = cells.size();
        int randomCellIndex = new Random().nextInt(size);

        SpecimenComponent randomCell = cells.values().stream()
                .skip(randomCellIndex)
                .findFirst()
                .get();

        return (Cell) randomCell;
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

    protected void addNodesTo(Map<Integer, SpecimenComponent> components) {
        int i, j;
        GridPlace nodeGridPlace, cellGridPlace;
        for (SpecimenComponent component : components.values()) {
            if (component instanceof Cell) {
                Cell cell = (Cell) components;
                cellGridPlace = cell.getGridPlace();
                for (Direction direction : Direction.values()) {
                    switch (direction) {
                        case UP:
                            i = cellGridPlace.i - 1;
                            j = cellGridPlace.j - 1;
                            nodeGridPlace = new GridPlace(i, j);
                            components.put(nodeGridPlace.hashCode(), new Node());
                            break;
                        case LEFT:
                            i = cellGridPlace.i + 1;
                            j = cellGridPlace.j - 1;
                            nodeGridPlace = new GridPlace(i, j);
                            components.put(nodeGridPlace.hashCode(), new Node());
                            break;
                        case DOWN:
                            i = cellGridPlace.i + 1;
                            j = cellGridPlace.j + 1;
                            nodeGridPlace = new GridPlace(i, j);
                            components.put(nodeGridPlace.hashCode(), new Node());
                            break;
                        case RIGHT:
                            i = cellGridPlace.i - 1;
                            j = cellGridPlace.j + 1;
                            nodeGridPlace = new GridPlace(i, j);
                            components.put(nodeGridPlace.hashCode(), new Node());
                            break;
                    }
                }
            }
        }
    }

    protected void bindCellsBetweenCells(Map<Integer, SpecimenComponent> components) {
        int adjacentCellI = 0, adjacentCellJ = 0;
        Cell cell;
        GridPlace cellGridPlace, adjacentCellGridPlace;
        Cell adjacentCell;
        for (SpecimenComponent component : components.values()) {
            if (component instanceof Cell) {
                cell = (Cell) component;
                cellGridPlace=cell.getGridPlace();
                for (Direction direction : Direction.values()) {
                    switch (direction) {
                        case UP:
                            adjacentCellI = cellGridPlace.i;
                            adjacentCellJ = cellGridPlace.j - 2;
                            bindAdjacentCells(cellGridPlace,adjacentCellJ);
                            break;
                        case RIGHT:
                            adjacentCellI = cellGridPlace.i + 2;
                            adjacentCellJ = cellGridPlace.j;
                            bindAdjacentCells(cellGridPlace,adjacentCellJ);
                            break;
                        case DOWN:
                            adjacentCellI = cellGridPlace.i;
                            adjacentCellJ = cellGridPlace.j + 2;
                            bindAdjacentCells(cellGridPlace,adjacentCellJ);
                            break;
                        case LEFT:
                            adjacentCellI = cellGridPlace.i - 2;
                            adjacentCellJ = cellGridPlace.j;
                            bindAdjacentCells(cellGridPlace,adjacentCellJ);
                            break;
                    }

//todo refactor: here is method linkCellsBetweenCells
                    adjacentCellGridPlace = new GridPlace(adjacentCellI, adjacentCellJ);
                    adjacentCell = cellsMapping.get(adjacentCellGridPlace);
                    if (adjacentCell != null) {
                        cell.getAdjacentCells().add(adjacentCell);
                    }
                }
            }
        }
    }

    protected void bindCellsBetweenNodes(Map<Integer, SpecimenComponent> components) {
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

    private void bindNodesBetweenNodes(Map<Integer, SpecimenComponent> nodesMapping) { //todo bugfix: redundant links appears
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

    private void setEuclidPositionsToNodes(Map<Integer, SpecimenComponent> nodesMapping, PVector initialPosition) {
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

    private Specimen placeComponentsInSpecimen(Map<Integer, SpecimenComponent> components) {
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





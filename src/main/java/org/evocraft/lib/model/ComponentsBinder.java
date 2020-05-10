package org.evocraft.lib.model;

import java.util.Map;


public class ComponentsBinder {

    public static void bindCellsAndConnections(Map<Integer, Cell> cells, Map<Integer, Connection> connections) {
        int inputTileHash, outputTileHash;
        Cell inputCell, outputCell;

        for (Connection connection : connections.values()) {

            inputTileHash = connection.getInputTileIndex().hashCode();
            outputTileHash = connection.getOutputTileIndex().hashCode();

            inputCell = cells.get(inputTileHash);
            outputCell = cells.get(outputTileHash);

            connection.setInput(inputCell);
            connection.setOutput(outputCell);

            inputCell.getOutputConnections().add(connection);
            outputCell.getInputConnections().add(connection);
        }
    }

    public static void bindCellsAndMembranes(Map<Integer, Cell> cells, Map<Integer, Membrane> membranes) {

    }

    public static void bindCellsAndNodes(Map<Integer, Cell> cells, Map<Integer, Node> nodes) {
        int adjacentNodeI = 0, adjacentNodeJ = 0;
        TileIndex cellTileIndex;
        TileIndex adjacentNodeTileIndex;
        Node adjacentNode;
        for (Cell cell : cells.values()) {
            cellTileIndex = cell.getTileIndex();
            for (Direction direction : Direction.values()) {

                switch (direction) {
                    case UP:
                        adjacentNodeI = cellTileIndex.i - 1;
                        adjacentNodeJ = cellTileIndex.j - 1;
                        break;
                    case RIGHT:
                        adjacentNodeI = cellTileIndex.i + 1;
                        adjacentNodeJ = cellTileIndex.j - 1;
                        break;
                    case DOWN:
                        adjacentNodeI = cellTileIndex.i + 1;
                        adjacentNodeJ = cellTileIndex.j + 1;
                        break;
                    case LEFT:
                        adjacentNodeI = cellTileIndex.i - 1;
                        adjacentNodeJ = cellTileIndex.j + 1;
                        break;
                }

                adjacentNodeTileIndex = new TileIndex(adjacentNodeI, adjacentNodeJ);
                adjacentNode = nodes.get(adjacentNodeTileIndex.hashCode());
                cell.getAdjacentNodes().add(adjacentNode);
            }

        }
    }

    public static void bindMembranesAndNodes(Map<Integer, Membrane> membranes, Map<Integer, Node> nodes) {
        //todo find existing code (former nodes to nodes binding)
    }


    private void bindNodesBetweenNodes(Map<Integer, SpecimenComponent> components) { //todo bugfix: redundant links appears
        int adjacentNodeI = 0, adjacentNodeJ = 0;
        TileIndex nodeTileIndex;
        TileIndex adjacentNodeTileIndex;
        Node adjacentNode;
        Node node;
        int checkCellPresenceOneI = 0, checkCellPresenceOneJ = 0;
        int checkCellPresenceTwoI = 0, checkCellPresenceTwoJ = 0;
        TileIndex checkCellPresenceOne, checkCellPresenceTwo;
        Cell forCheckPresenceOne, forCheckPresenceTwo;
        for (SpecimenComponent component : components.values()) {
            if (component instanceof Node) {
                node = (Node) component;
                nodeTileIndex = node.getTileIndex();
                for (Direction direction : Direction.values()) {

                    switch (direction) {
                        case UP:
                            adjacentNodeI = nodeTileIndex.i;
                            adjacentNodeJ = nodeTileIndex.j - 2;
                            checkCellPresenceOneI = nodeTileIndex.i - 1;
                            checkCellPresenceOneJ = nodeTileIndex.i - 1;
                            checkCellPresenceTwoI = nodeTileIndex.i + 1;
                            checkCellPresenceTwoJ = nodeTileIndex.i - 1;
                            break;
                        case RIGHT:
                            adjacentNodeI = nodeTileIndex.i + 2;
                            adjacentNodeJ = nodeTileIndex.j;
                            checkCellPresenceOneI = nodeTileIndex.i + 1;
                            checkCellPresenceOneJ = nodeTileIndex.i - 1;
                            checkCellPresenceTwoI = nodeTileIndex.i + 1;
                            checkCellPresenceTwoJ = nodeTileIndex.i + 1;
                            break;
                        case DOWN:
                            adjacentNodeI = nodeTileIndex.i;
                            adjacentNodeJ = nodeTileIndex.j + 2;
                            checkCellPresenceOneI = nodeTileIndex.i + 1;
                            checkCellPresenceOneJ = nodeTileIndex.i + 1;
                            checkCellPresenceTwoI = nodeTileIndex.i - 1;
                            checkCellPresenceTwoJ = nodeTileIndex.i + 1;
                            break;
                        case LEFT:
                            adjacentNodeI = nodeTileIndex.i - 2;
                            adjacentNodeJ = nodeTileIndex.j;
                            checkCellPresenceOneI = nodeTileIndex.i - 1;
                            checkCellPresenceOneJ = nodeTileIndex.i + 1;
                            checkCellPresenceTwoI = nodeTileIndex.i - 1;
                            checkCellPresenceTwoJ = nodeTileIndex.i - 1;
                            break;
                    }

                    adjacentNodeTileIndex = new TileIndex(adjacentNodeI, adjacentNodeJ);
                    adjacentNode = (Node) components.get(adjacentNodeTileIndex.hashCode());

                    if (adjacentNode != null) {
                        checkCellPresenceOne = new TileIndex(checkCellPresenceOneI, checkCellPresenceOneJ);
                        forCheckPresenceOne = (Cell) components.get(checkCellPresenceOne.hashCode());
                        checkCellPresenceTwo = new TileIndex(checkCellPresenceTwoI, checkCellPresenceTwoJ);
                        forCheckPresenceTwo = (Cell) components.get(checkCellPresenceTwo.hashCode());

                        if (forCheckPresenceOne != null || forCheckPresenceTwo != null) {
                            // node.getAdjacentMembranes().add( adjacentNode);
                        }
                    }
                }
            }
        }
    }


}

package org.evocraft.lib.model;

import java.util.Map;


public class ComponentsBinder {

    public static void bindCellsAndConnections(Map<Integer, Cell> cells, Map<Integer, Connection> connections) {
        int inputTileHash, outputTileHash;
        Cell inputCell, outputCell;

        for (Connection connection : connections.values()) {

            TileIndex inputTileIndex = connection.getInputTileIndex();
            TileIndex outputTileIndex = connection.getOutputTileIndex();

            if (inputTileIndex == null || outputTileIndex == null) {
                break;
            }

            inputTileHash = inputTileIndex.hashCode();
            outputTileHash = outputTileIndex.hashCode();

            inputCell = cells.get(inputTileHash);
            outputCell = cells.get(outputTileHash);

            connection.setInput(inputCell);
            connection.setOutput(outputCell);

            inputCell.getOutputConnections().add(connection);
            outputCell.getInputConnections().add(connection);
        }
    }

    public static void bindCellsAndMembranes(Map<Integer, Cell> cells, Map<Integer, Membrane> membranes) {

        for (Cell cell : cells.values()) {

            if (cell instanceof Muscle) {
                Muscle muscle = (Muscle) cell;
                TileIndex muscleTileIndex = muscle.getTileIndex();
                int i = 0, j = 0;

                for (Direction direction : Direction.values()) {

                    switch (direction) {
                        case UP:
                            i = muscleTileIndex.i;
                            j = muscleTileIndex.j - 1;
                            break;
                        case RIGHT:
                            i = muscleTileIndex.i + 1;
                            j = muscleTileIndex.j;
                            break;
                        case DOWN:
                            i = muscleTileIndex.i;
                            j = muscleTileIndex.j + 1;
                            break;
                        case LEFT:
                            i = muscleTileIndex.i - 1;
                            j = muscleTileIndex.j;
                            break;
                    }

                    TileIndex adjacentMembraneTileIndex = new TileIndex(i, j);
                    Membrane adjacentMembrane = membranes.get(adjacentMembraneTileIndex.hashCode());
                    muscle.getAdjacentMembranes().add(adjacentMembrane);
                }
            }
        }
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
        TileIndex membraneTileIndex;
        int i, j;

        for (Membrane membrane : membranes.values()) {
            membraneTileIndex = membrane.getTileIndex();

            for (Direction direction : Direction.values()) {

                switch (direction) {
                    case UP:
                        i = membraneTileIndex.i;
                        j = membraneTileIndex.j - 1;
                        setInitialNodeInMembrane(i, j, membrane, nodes);
                        break;
                    case RIGHT:
                        i = membraneTileIndex.i + 1;
                        j = membraneTileIndex.j;
                        setTerminalNodeInMembrane(i, j, membrane, nodes);
                        break;
                    case DOWN:
                        i = membraneTileIndex.i;
                        j = membraneTileIndex.j + 1;
                        setTerminalNodeInMembrane(i, j, membrane, nodes);
                        break;
                    case LEFT:
                        i = membraneTileIndex.i - 1;
                        j = membraneTileIndex.j;
                        setInitialNodeInMembrane(i, j, membrane, nodes);
                        break;
                }
            }
        }
    }

    protected static void setInitialNodeInMembrane(int i, int j, Membrane membrane, Map<Integer, Node> nodes) { //todo refactor: too much params
        TileIndex nodeTileIndex = new TileIndex(i, j);
        Node node = nodes.get(nodeTileIndex.hashCode());
        if (node != null) {
            membrane.setInitialNode(node);
        }
    }

    protected static void setTerminalNodeInMembrane(int i, int j, Membrane membrane, Map<Integer, Node> nodes) { //todo refactor: too much params
        TileIndex nodeTileIndex = new TileIndex(i, j);
        Node node = nodes.get(nodeTileIndex.hashCode());
        if (node != null) {
            membrane.setTerminalNode(node);
        }
    }

}

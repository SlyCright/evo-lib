package org.evocraft.lib.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import processing.core.PVector;

public class NodesBuilder {

    public static Map<Integer, Node> generateNodes(Map<Integer, Cell> cells) {
        Map<Integer, Node> nodes = createNodesWithZeroPosition(cells);
        setEuclidPositionsToNodes(nodes, cells);
        return nodes;
    }

    protected static Map<Integer, Node> createNodesWithZeroPosition(Map<Integer, Cell> cells) {
        Map<Integer, Node> nodes = new HashMap<>();
        int i, j;
        TileIndex cellTileIndex;

        for (Cell cell : cells.values()) {
            cellTileIndex = cell.getTileIndex();

            for (Direction direction : Direction.values()) {

                switch (direction) {
                    case UP:
                        i = cellTileIndex.i - 1;
                        j = cellTileIndex.j - 1;
                        putNodeWithIndexesIntoMap(i, j, nodes);
                        break;
                    case LEFT:
                        i = cellTileIndex.i + 1;
                        j = cellTileIndex.j - 1;
                        putNodeWithIndexesIntoMap(i, j, nodes);
                        break;
                    case DOWN:
                        i = cellTileIndex.i + 1;
                        j = cellTileIndex.j + 1;
                        putNodeWithIndexesIntoMap(i, j, nodes);
                        break;
                    case RIGHT:
                        i = cellTileIndex.i - 1;
                        j = cellTileIndex.j + 1;
                        putNodeWithIndexesIntoMap(i, j, nodes);
                        break;
                }
            }
        }
        return nodes;
    }

    protected static void putNodeWithIndexesIntoMap(int i, int j, Map<Integer, Node> nodes) {
        TileIndex nodeTileIndex = new TileIndex(i, j);
        Node node = new Node();
        node.setTileIndex(nodeTileIndex);
        nodes.put(nodeTileIndex.hashCode(), node);
    }

    private static void setEuclidPositionsToNodes(Map<Integer, Node> nodes, Map<Integer, Cell> cells) {
        int i, j;
        float posX = 0f, posY = 0f;
        Node node;
        TileIndex tileIndex;
        for (SpecimenComponent component : nodes.values()) {
            if (component instanceof Node) {
                node = (Node) component;
                tileIndex = node.getTileIndex();

                posX = new Random().nextFloat() - 0.5f;
                posY = new Random().nextFloat() - 0.5f;

                posX += (((float) tileIndex.i + 1f) / 2f - 1f) * Membrane.LENGTH;
                posY += (((float) tileIndex.j + 1f) / 2f - 1f) * Membrane.LENGTH;

                node.setPosition(new PVector(posX, posY));
            }
        }
    }

}

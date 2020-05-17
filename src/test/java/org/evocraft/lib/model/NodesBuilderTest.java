package org.evocraft.lib.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NodesBuilderTest {

    @Test
    void putNodeWithIndexesIntoMap() {
        Map<Integer, Node> nodes = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            NodesBuilder.putNodeWithIndexesIntoMap(i, i, nodes);
        }

        for (int i = 0; i < 3; i++) {
            NodesBuilder.putNodeWithIndexesIntoMap(i, i, nodes);
        }

        assertEquals(3, nodes.size());

        for (int i = 0; i < 3; i++) {
            Node node = nodes.get(new TileIndex(i, i).hashCode());
            assertNotNull(node);
        }
    }

    @Test
    void createNodesWithZeroPosition() {
        Map<Integer, Cell> cells = new HashMap<>();
        TileIndex tileIndex = new TileIndex(0, 0);
        Muscle cell = new Muscle(0f,false);
        cell.setTileIndex(tileIndex);
        cells.put(tileIndex.hashCode(), cell);
        Map<Integer, Node> nodes = NodesBuilder.generateNodes(cells);

        assertEquals(1, cells.size());
        assertEquals(4, nodes.size());

        for (int i = 0; i < 3; i++) {
            tileIndex = new TileIndex(i*2, i*2);
            cell = new Muscle(0f,false);
            cell.setTileIndex(tileIndex);
            cells.put(tileIndex.hashCode(), cell);
        }
        assertEquals(3, cells.size());
        nodes = NodesBuilder.generateNodes(cells);

        assertEquals(10, nodes.size());

    }
}
package org.evocraft.lib.model;

import org.junit.jupiter.api.Test;

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
}
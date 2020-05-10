package org.evocraft.lib.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;
import processing.core.PVector;

class SpecimenTest {

    @Test
    void calculatePosition() {
        Specimen specimen = new Specimen();
        Map<Integer, Node> nodes = new HashMap<>();
        float midX = 0f, midY = 0f;
        int nodesTotal = 3;
        for (int i = 0; i < nodesTotal; i++) {
            float x = new Random().nextFloat();
            float y = new Random().nextFloat();
            midX += x;
            midY += y;
            Node node = new Node();
            node.setPosition(new PVector(x, y));
            node.setTileIndex(new TileIndex(i, i));
            nodes.put(node.getTileIndex().hashCode(), node);
        }
        midX /= (float) nodesTotal;
        midY /= (float) nodesTotal;
        specimen.setNodes(nodes);
        float epsilon = Float.MIN_VALUE;

        PVector position = specimen.calculatePosition();

        assertTrue(Math.abs(midX - position.x) < epsilon);
        assertTrue(Math.abs(midY - position.y) < epsilon);
    }
}
package org.evocraft.lib.model;

import processing.core.PVector;

import java.util.Map;

public class MembraneBuilder {

    public static Membrane buildNew(Node fromNode, float angle) {
      //  TileIndex tileIndexOne =fromNode.getTileIndices().get(0);
        float length=Membrane.LENGTH;
        PVector direction=PVector.fromAngle(angle);
        PVector membraneVector=direction.copy().mult(length);

        return null;
    }

    public static Membrane buildNew(Membrane adjacentToMembrane, float angle) {
        return null;
    }

    public static Map<Integer, Membrane> generateMembranes(Map<Integer, Cell> cells) {
        return null;
    }
}

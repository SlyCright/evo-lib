package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;

import java.util.ArrayList;

@Getter
@Setter
public class Specimen implements Actionable {

    private Map<Integer, Cell> cells = new HashMap<>();
    private Map<Integer, Connection> connections = new HashMap<>();

    private Map<Integer, Membrane> membranes = new HashMap<>();
    private Map<Integer, Node> nodes = new HashMap<>();

    private ArrayList<SpecimenComponent> components = new ArrayList<>();

    private PVector positionOnCreation;

    @Override
    public void act() {
        for (SpecimenComponent specimenComponent : this.components) {
            specimenComponent.act();
        }
    }

    public PVector calculatePosition() {
        PVector position = new PVector(0f, 0f);
        for (Node node : this.nodes.values()) {
            position.add(node.getPosition());
        }
        int nodesTotal = this.nodes.size();
        position.div((float) nodesTotal);
        return position;
    }
}

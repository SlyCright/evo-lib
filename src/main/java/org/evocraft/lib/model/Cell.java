package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;

@Getter
@Setter
public abstract class Cell extends SpecimenComponent {

    final private float STIFFNESS = 0.01f; //todo backlog: should depend of DNA

    protected final float diagonalCellSize = (float) Math.sqrt(2.0) * Membrane.LENGTH;

    private List<Node> adjacentNodes = new ArrayList<>(4);

    private PVector position = new PVector(0, 0);

    protected List<Connection> inputConnections = new ArrayList<>();
    protected List<Connection> outputConnections = new ArrayList<>();

    @Override
    public void act() {
        calculatePosition();
        applyForcesToNodes();
    }

    protected void calculatePosition() {
        float posX = 0f, posY = 0f;
        for (Node node : adjacentNodes) {
            posX += node.getPosition().x;
            posY += node.getPosition().y;
        }
        posX /= (float) adjacentNodes.size();
        posY /= (float) adjacentNodes.size();

        position = new PVector(posX, posY);
    }

    private void applyForcesToNodes() {
        for (Node node : adjacentNodes) {
            PVector nodePosition = node.getPosition().copy();
            PVector cellPosition = this.getPosition().copy();
            PVector forceDirection = PVector.sub(nodePosition, cellPosition);
            forceDirection.normalize();
            float distanceToNode = position.dist(nodePosition);
            float thisCellDiagonalSize = this.getDiagonalSize();
            float forceValue = -1 * STIFFNESS * (distanceToNode - thisCellDiagonalSize / 2f);
            PVector force = forceDirection.copy();
            force.mult(forceValue);
            node.applyForce(force);
        }
    }

    public float getSize() {
        return Membrane.LENGTH;
    }

    public float getDiagonalSize() {
        return diagonalCellSize;
    }

}

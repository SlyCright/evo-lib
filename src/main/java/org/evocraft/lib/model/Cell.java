package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import processing.core.PVector;

@Data
public class Cell implements SpecimenComponent {

    final private float CELL_SIZE = 75f;
    final private float STIFFNESS = 0.01f;

    private List<Node> nodes = new ArrayList<>(4);
    private PVector position = new PVector(0, 0);

    @Override
    public void act() {
        calculatePosition();
        applyForcesToNodes();
    }

    protected void calculatePosition() {
        float posX = 0f, posY = 0f;
        for (Node node : nodes) {
            posX += node.getPosition().x;
            posY += node.getPosition().y;
        }
        posX /= (float) nodes.size();
        posY /= (float) nodes.size();

        position = new PVector(posX, posY);
    }

    private void applyForcesToNodes() {
        for (Node node : nodes) {
            PVector nodePosition = node.getPosition().copy();
            PVector cellPosition = this.getPosition().copy();
            PVector forceDirection = PVector.sub(nodePosition, cellPosition);
            forceDirection.normalize();
            float distanceToNode = position.dist(nodePosition);
            float forceValue = -1 * STIFFNESS * (distanceToNode - CELL_SIZE / 2f);
            PVector force = forceDirection.copy();
            force.mult(forceValue);
            node.applyForce(force);
        }
    }

}

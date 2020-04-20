package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import processing.core.PVector;

@Data
public class Node implements SpecimenComponent {

    final private float DRAG_FRICTION_FACTOR = 0.05f;
    final private float MEMBRANE_STIFFNESS = 0.025f;

    protected PVector position = new PVector(0.0f, 0.0f);
    protected PVector velocity = new PVector(0.0f, 0.0f);
    protected PVector acceleration = new PVector(0.0f, 0.0f);

    private List<Cell> adjacentCells = new ArrayList<>();
    private List<Node> adjacentNodes = new ArrayList<>(4);

    @Override
    public void act() {
//todo refactor: make separated method
        for (Node adjacentNode : this.adjacentNodes) {
            PVector adjacentNodePosition = adjacentNode.getPosition().copy();
            PVector nodePosition = this.getPosition().copy();
            PVector forceDirection = PVector.sub(adjacentNodePosition, nodePosition);
            forceDirection.normalize();
            float distanceToAdjacentNode = this.position.dist(adjacentNodePosition);

            float membraneSize = 0f;
            for (Cell cell : adjacentCells) {
                membraneSize += cell.getSize();
            }
            for (Cell cell : adjacentNode.getAdjacentCells()) {
                membraneSize += cell.getSize();
            }

            membraneSize /= (adjacentCells.size() + adjacentNode.getAdjacentCells().size());

            float forceValue = MEMBRANE_STIFFNESS * (distanceToAdjacentNode - membraneSize);
            PVector force = forceDirection.copy();
            force.mult(forceValue);
            this.applyForce(force);
        }

//todo refactor: make separated method
        PVector dragForce = calculateDragForce();
        applyForce(dragForce);
        velocity.add(acceleration);
        position.add(velocity);
        acceleration.set(0f, 0f);
    }


    public void applyForce(PVector force) {
        acceleration.add(force);
    }

    private PVector calculateDragForce() {
        PVector currentVelocity = velocity.copy();
        float velocitySqValue = currentVelocity.magSq();
        PVector velocityDirection = currentVelocity.copy();
        velocityDirection.normalize();
        return velocityDirection.mult(-1 * DRAG_FRICTION_FACTOR * velocitySqValue);
    }

}

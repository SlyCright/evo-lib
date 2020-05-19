package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;

@Getter
@Setter
public abstract class Cell extends SpecimenComponent {

    final private float STIFFNESS = 0.005f; //todo backlog: should depend of DNA

    protected final float diagonalCellSize = (float) Math.sqrt(2.0) * Membrane.LENGTH;

    protected List<Node> adjacentNodes = new ArrayList<>(4);

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
            float thisCellDiagonalSize = this.getDiagonalCellSize();
            float forceValue = -1 * STIFFNESS * (distanceToNode - thisCellDiagonalSize / 2f);
            PVector force = forceDirection.copy();
            force.mult(forceValue);
            node.applyForce(force);
            Node oppositeNode = findOppositeNode(node);
            oppositeNode.applyForce(force.mult(-1));
        }
    }

    protected Node findOppositeNode(Node node) { //todo refactor: dosen't need calculate every tick. Should be clculated once and stored in the field of Cell
        Map<Integer, Node> cellNodes = new HashMap<>();
        for (Node adjacentNode : adjacentNodes) {
            cellNodes.put(adjacentNode.hashCode(), adjacentNode);
        }

        int i = node.getTileIndex().i, j = node.getTileIndex().j;

        for (Direction direction : Direction.values()) {

            switch (direction) {

                case UP:
                    i -= 2;
                    j -= 2;
                    break;

                case RIGHT:
                    i += 2;
                    j -= 2;
                    break;

                case DOWN:
                    i += 2;
                    j += 2;
                    break;

                case LEFT:
                    i -= 2;
                    j += 2;
                    break;
            }

            int key = new TileIndex(i, j).hashCode();
            Node oppositeNode = cellNodes.get(key);
            if (oppositeNode != null) {
                return oppositeNode;
            }
        }

        return null;
    }

    public abstract Cell copy();
}

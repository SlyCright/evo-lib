package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import processing.core.PVector;

@Data
public class Cell implements SpecimenComponent {

    final static public float CELL_SIZE = 50f;
    final private float STIFFNESS = 0.01f;

    private float cellsInteractionStiffness = 0.25f * STIFFNESS;

    private float diagonalCellSize = (float) Math.sqrt(2.0) * CELL_SIZE;

    private List<Node> adjacentNodes = new ArrayList<>(4);
    private List<Cell> adjacentCells = new ArrayList<>(4);
    private PVector position = new PVector(0, 0);

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

//todo refactor: separate method
        for (Node node : adjacentNodes) {
            PVector nodePosition = node.getPosition().copy();
            PVector cellPosition = this.getPosition().copy();
            PVector forceDirection = PVector.sub(nodePosition, cellPosition);
            forceDirection.normalize();
            float distanceToNode = position.dist(nodePosition);
            float forceValue = -1 * STIFFNESS * (distanceToNode - diagonalCellSize / 2f);
            PVector force = forceDirection.copy();
            force.mult(forceValue);
            node.applyForce(force);
        }
  /*
//todo refactor: separate method
        for (Cell adjacentCell : adjacentCells) {
            PVector adjacentCellPosition = adjacentCell.getPosition().copy();
            PVector cellPosition = this.getPosition().copy();
            PVector forceDirection = PVector.sub(adjacentCellPosition, cellPosition);
            forceDirection.normalize();
            float distanceToAdjacentCell = position.dist(adjacentCellPosition);
            float forceValue = cellsInteractionStiffness * (distanceToAdjacentCell - CELL_SIZE);
            PVector force = forceDirection.copy();
            force.mult(forceValue);
            for (Node adjacentNode : this.getAdjacentNodes()) {
                adjacentNode.applyForce(force);
            }
        }
*/
    }

}

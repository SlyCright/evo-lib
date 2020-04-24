package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import processing.core.PVector;

@Data
@Builder
@NoArgsConstructor
public abstract class Cell extends Activateable {

    final static public float CELL_SIZE = 50f;
    final private float STIFFNESS = 0.01f;

    private float cellsInteractionStiffness = 0.25f * STIFFNESS;

    protected final float DIAGONAL_CELL_SIZE = (float) Math.sqrt(2.0) * CELL_SIZE;

    private List<Node> adjacentNodes = new ArrayList<>(4);
    private List<Cell> adjacentCells = new ArrayList<>(4);

    private PVector position = new PVector(0, 0);

    protected List<Connection> inputConnections = new ArrayList<>();
    protected List<Connection> outputConnections = new ArrayList<>();

    private GridPlace gridPlace;

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
            float thisCellDiagonalSize = this.getDiagonalSize();
            float forceValue = -1 * STIFFNESS * (distanceToNode - thisCellDiagonalSize / 2f);
            PVector force = forceDirection.copy();
            force.mult(forceValue);
            node.applyForce(force);
        }

  /*
//todo refactor: separate method. This way to calculate itercellar forces. If use should be designed by DNA
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

    public float getSize() {
        return CELL_SIZE;
    }

    public float getDiagonalSize() {
        return DIAGONAL_CELL_SIZE;
    }

    public abstract Cell copy();
}

package org.evocraft.lib.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Muscle extends Cell {

    private final float sizeWhenActivated;
    private final float diagonalSizeWhenActivated;

    Muscle(float sizeWhenActivated) {
        this.sizeWhenActivated = sizeWhenActivated;
        this.diagonalSizeWhenActivated = (float) Math.sqrt(2.0) * sizeWhenActivated;
    }

    public void act() {
        this.isActive = calculateIfActive(inputConnections);
        super.act();
    }

    protected boolean calculateIfActive(List<Connection> inputConnections) {

        boolean isActive = false;

        for (Connection connection : inputConnections) {
            if (connection.isActive()) {
                isActive = true;
                break;
            }
        }

        return isActive;
    }

    public float getSize() {
        float size;
        if (isActive) {
            size = sizeWhenActivated;
        } else {
            size = CELL_SIZE;
        }
        return size;
    }

    public float getDiagonalSize() {
        float diagonalSize;
        if (isActive) {
            diagonalSize = diagonalSizeWhenActivated;
        } else {
            diagonalSize = DIAGONAL_CELL_SIZE;
        }
        return diagonalSize;
    }

    @Override
    public Muscle copy() {
        Muscle muscle = new Muscle(this.sizeWhenActivated);
        muscle.setGridPlace(this.getGridPlace());
        return muscle;
    }
}

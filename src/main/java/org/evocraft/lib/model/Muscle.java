package org.evocraft.lib.model;

import java.util.List;
import java.util.Random;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Muscle extends Cell {

    private final float SIZE_WHEN_ACTIVATED = CELL_SIZE * (1.5f - new Random().nextFloat()); //todo backlog: DNA here
    private final float DIAGONAL_SIZE_WHEN_ACTIVATED = (float) Math.sqrt(2.0) * SIZE_WHEN_ACTIVATED;

    //todo refactor: Muscle(float sizeWhenActivated);

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
            size = SIZE_WHEN_ACTIVATED;
        } else {
            size = CELL_SIZE;
        }
        return size;
    }

    public float getDiagonalSize() {
        float diagonalSize;
        if (isActive) {
            diagonalSize = DIAGONAL_SIZE_WHEN_ACTIVATED;
        } else {
            diagonalSize = DIAGONAL_CELL_SIZE;
        }
        return diagonalSize;
    }

}

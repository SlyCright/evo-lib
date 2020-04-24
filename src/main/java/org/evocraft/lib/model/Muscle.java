package org.evocraft.lib.model;

import java.util.List;
import java.util.Random;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Muscle extends Cell {

    private float sizeWhenActivated = CELL_SIZE * (1.5f - new Random().nextFloat()); //todo backlog: DNA here
    private float diagonalSizeWhenActivated = (float) Math.sqrt(2.0) * sizeWhenActivated;

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
    public Cell copy() {
        return Muscle.builder()
            .
            .build();
    }

}

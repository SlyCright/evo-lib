package org.evocraft.lib.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Random;

@Data
@EqualsAndHashCode(callSuper = true)
public class Muscle extends Cell {

    private final int TICKS_PER_CHANGE_ACTIVATION = 34 + new Random().nextInt(34); //todo refactor: shouldn't be here. This field for oscillator
    private int currentTick = 0;
    private boolean isActive = true;

    private final float SIZE_WHEN_ACTIVATED = CELL_SIZE * (1.5f - new Random().nextFloat()); //todo backlog: DNA here
    private final float DIAGONAL_SIZE_WHEN_ACTIVATED = (float) Math.sqrt(2.0) * SIZE_WHEN_ACTIVATED;

    public void act() {
        super.act();
        currentTick++;
        if (currentTick > TICKS_PER_CHANGE_ACTIVATION) {
            isActive = !isActive;
            currentTick = 0;
        }
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

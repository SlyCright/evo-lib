package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Muscle extends Cell {

    private final float sizeWhenActivated;
    private final float diagonalSizeWhenActivated;

    private List<Membrane> adjacentMembranes = new ArrayList<>(4);

    Muscle(float sizeWhenActivated) {
        this.sizeWhenActivated = sizeWhenActivated;
        this.diagonalSizeWhenActivated = (float) Math.sqrt(2.0) * sizeWhenActivated;
    }

    public void act() {
        this.isActive = calculateIfActive(inputConnections);
        float currentSize = this.isActive ? this.sizeWhenActivated : Membrane.LENGTH;
        applySizeToMembranes(currentSize);
        super.act();
    }


    protected void applySizeToMembranes(float currentSize) {
        for (Membrane membrane : adjacentMembranes) {
            membrane.applyLength(this.hashCode(), currentSize);
        }
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

    @Override
    public Cell copy() {
        return Copier.copy(this);
    }

}

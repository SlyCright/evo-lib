package org.evocraft.lib.model;

import java.util.ArrayList;
import lombok.Data;
import processing.core.PVector;

@Data
public class Specimen implements Actionable {

    private ArrayList<SpecimenComponent> components = new ArrayList<>();

    @Override
    public void act() {
        for (SpecimenComponent specimenComponent : components) {
            specimenComponent.act();
        }
    }

    public PVector getPosition() {
        float posX = 0f, posY = 0f;
        int cellsTotal = 0;
        for (SpecimenComponent component : this.components) {
            if (component instanceof Cell) {
                PVector cellPosition = ((Cell) component).getPosition().copy();
                posX += cellPosition.x;
                posY += cellPosition.y;
                cellsTotal++;
            }
        }
        posX /= cellsTotal;
        posY /= cellsTotal;
        return new PVector(posX, posY);
    }

}

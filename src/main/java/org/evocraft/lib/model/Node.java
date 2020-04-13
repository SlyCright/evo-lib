package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import processing.core.PVector;

@Data
public class Node implements SpecimenComponent {

    protected PVector position = new PVector(0.0f, 0.0f);
    protected PVector velocity = new PVector(0.0f, 0.0f);
    protected PVector acceleration = new PVector(0.0f, 0.0f);

    private List<Cell> cells = new ArrayList<>();


    @Override
    public void act() {

    }

}

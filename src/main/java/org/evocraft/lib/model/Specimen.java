package org.evocraft.lib.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Specimen implements Actionable {

    private ArrayList<SpecimenComponent> specimenComponents = new ArrayList<>();

    @Override
    public void act() {
        for (SpecimenComponent specimenComponent : specimenComponents) {
            specimenComponent.act();
        }
    }
}

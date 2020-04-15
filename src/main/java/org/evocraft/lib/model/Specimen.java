package org.evocraft.lib.model;

import java.util.ArrayList;
import lombok.Data;

@Data
public class Specimen {

    private ArrayList<SpecimenComponent> specimenComponents = new ArrayList<>();

}

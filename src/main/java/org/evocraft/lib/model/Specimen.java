package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Specimen {

    private ArrayList<SpecimenComponent> specimenComponents = new ArrayList<>();

}

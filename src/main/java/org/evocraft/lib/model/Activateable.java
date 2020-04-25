package org.evocraft.lib.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Activateable implements SpecimenComponent{

    protected boolean isActive = false;

}

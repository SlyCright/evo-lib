package org.evocraft.lib.model;

import lombok.Data;

@Data
public abstract class Activateable implements SpecimenComponent{

    protected boolean isActive = false;

    @Override
   abstract public void act() ;

}

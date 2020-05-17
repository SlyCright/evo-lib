package org.evocraft.lib.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class SpecimenComponent implements Actionable {

    protected TileIndex tileIndex=new TileIndex(0,0); //todo refactor: not all inheritances uses this field
    protected boolean doesReverseSignal=false; //todo refactor: not all inheritances uses this field
    protected boolean isActive = false; //todo refactor: not all inheritances uses this field

    // todo refactor: make constructor SpecimenComponent(TileIndex tileIndex), make field TileIndex final

    public abstract SpecimenComponent copy();

    public int hashCode() {
        return tileIndex.hashCode();
    }
}
package org.evocraft.lib.model;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Setter
@Getter
public abstract class SpecimenComponent implements Actionable {

    protected boolean isActive = false; //todo refactor: not all inheritances uses this field
    protected TileIndex tileIndex = new TileIndex(0, 0);// = new TileIndex(0, 0); //todo refactor: not all inheritances uses this field

    // todo refactor: make constructor SpecimenComponent(TileIndex tileIndex)

    public abstract SpecimenComponent copy();

    public int hashCode() {
        return tileIndex.hashCode();
    }
}
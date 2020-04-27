package org.evocraft.lib.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class GridPlaces {

    public final GridPlace inputCellGridPlace, outputCellGridPlace;

    public GridPlaces(GridPlace inputCellGridPlace, GridPlace outputCellGridPlace) {
        this.inputCellGridPlace = inputCellGridPlace;
        this.outputCellGridPlace = outputCellGridPlace;
    }

}

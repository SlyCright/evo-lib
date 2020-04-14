package org.evocraft.lib.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
//@AllArgsConstructor
final class GridPlace {

    final int i, j;

    GridPlace(int i, int j) {
        this.i = i;
        this.j = j;
    }

}

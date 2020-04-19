package org.evocraft.lib.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
final class GridPlace {

    final int i, j;

    GridPlace(int i, int j) {
        this.i = i;
        this.j = j;
    }
}

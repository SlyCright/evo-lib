package org.evocraft.lib.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
final class TileIndex {

   public final int i, j;

    TileIndex(int i, int j) {
        this.i = i;
        this.j = j;
    }
}

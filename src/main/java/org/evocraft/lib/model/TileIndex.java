package org.evocraft.lib.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class TileIndex {

   public final int i, j;

   public TileIndex(int i, int j) {
        this.i = i;
        this.j = j;
    }
}

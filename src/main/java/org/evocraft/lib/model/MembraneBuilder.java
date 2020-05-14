package org.evocraft.lib.model;

import java.util.HashMap;
import java.util.Map;

public class MembraneBuilder {

    public static Map<Integer, Membrane> generateMembranes(Map<Integer, Cell> cells) {
        Map<Integer, Membrane> membranes = new HashMap<>();
        TileIndex cellTileIndex;
        int membraneI, membraneJ;
        for (Cell cell : cells.values()) {
            cellTileIndex = cell.getTileIndex();
            for (Direction direction : Direction.values()) {

                switch (direction) {
                    case UP:
                        membraneI = cellTileIndex.i;
                        membraneJ = cellTileIndex.j - 1;
                        putMembraneIntoMap(membraneI, membraneJ, membranes);
                        break;
                    case RIGHT:
                        membraneI = cellTileIndex.i + 1;
                        membraneJ = cellTileIndex.j;
                        putMembraneIntoMap(membraneI, membraneJ, membranes);
                        break;
                    case DOWN:
                        membraneI = cellTileIndex.i;
                        membraneJ = cellTileIndex.j + 1;
                        putMembraneIntoMap(membraneI, membraneJ, membranes);
                        break;
                    case LEFT:
                        membraneI = cellTileIndex.i - 1;
                        membraneJ = cellTileIndex.j;
                        putMembraneIntoMap(membraneI, membraneJ, membranes);
                        break;
                }
            }
        }

        return membranes;
    }

    private static void putMembraneIntoMap(int membraneI, int membraneJ, Map<Integer, Membrane> membranes) {
        TileIndex membraneTileIndex = new TileIndex(membraneI, membraneJ);
        Membrane membrane = new Membrane();
        membrane.setTileIndex(membraneTileIndex);
        membranes.put(membrane.hashCode(), membrane);
    }

}

package org.evocraft.lib.model;

import java.util.Map;
import java.util.Random;

public class Mutator {

    private static final float DELETION_CHANCE = 0.003f;
    private static final float ADDITION_CHANCE = 0.05f;

    public static void mutateCells(Map<Integer, Cell> cells) {

        for (Integer key : cells.keySet()) {
            if (new Random().nextFloat() < DELETION_CHANCE) {
                cells.remove(key);
            }
        }

        while (new Random().nextFloat() < ADDITION_CHANCE) {
            CellsBuilder.insertRandomCell(cells);
        }

    }

    public static void mutateConnections(Map<Integer, Connection> connections, Map<Integer, Cell> cells) {

        for (Integer key : connections.keySet()) {
            if (new Random().nextFloat() < DELETION_CHANCE) {
                connections.remove(key);
            }
        }

        while (new Random().nextFloat() < ADDITION_CHANCE) {
            ConnectionBuilder.insertRandomConnection(connections, cells);
        }
    }
}

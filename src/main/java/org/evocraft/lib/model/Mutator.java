package org.evocraft.lib.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Mutator {

    private static final float DELETION_CHANCE = 0.00075f;
    private static final float ADDITION_CHANCE = 0.075f;

    public static void mutateCells(Map<Integer, Cell> cells) {

        Iterator<Integer> iterator = cells.keySet().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            if (new Random().nextFloat() < DELETION_CHANCE) {
                iterator.remove();
            }
        }

        while (new Random().nextFloat() < ADDITION_CHANCE) {
            CellsBuilder.insertRandomCell(cells);
        }

    }

    public static void mutateConnections(Map<Integer, Connection> connections, Map<Integer, Cell> cells) {

        Iterator<Integer> iterator = connections.keySet().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            if (new Random().nextFloat() < DELETION_CHANCE) {
                iterator.remove();
            }
        }

        while (new Random().nextFloat() < 0.33f) { //todo refactor: hardcoded
            ConnectionBuilder.insertRandomConnection(connections, cells);
        }
    }

}

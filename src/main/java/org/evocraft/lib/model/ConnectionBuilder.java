package org.evocraft.lib.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ConnectionBuilder {

    public static Map<Integer, Connection> generateConnections(Map<Integer, Cell> cells, int generateTimes) {
        Map<Integer, Connection> connections = new HashMap<>();

        for (int i = 0; i < generateTimes; i++) {
            insertRandomConnection(connections, cells);
        }

        return connections;
    }

    protected static void insertRandomConnection(Map<Integer, Connection> connections, Map<Integer, Cell> cells) {
        float randomWeight = new Random().nextFloat();
        Connection connection = new Connection(randomWeight);
        Cell inputCell;
        Cell outPutCell;

        if (cells.size() > 1) {

            do {
                inputCell = getRandomCellOf(cells);
                outPutCell = getRandomCellOf(cells);
            } while (inputCell.hashCode() == outPutCell.hashCode());

            connection.setInputTileIndex(inputCell.getTileIndex());
            connection.setOutputTileIndex(outPutCell.getTileIndex());
            connections.put(connection.hashCode(), connection);
        }
    }

    protected static Cell getRandomCellOf(Map<Integer, Cell> cells) {
        int randomCellIndex = new Random().nextInt(cells.size());
        return cells.values().stream()
                .skip(randomCellIndex)
                .findFirst()
                .get();
    }

}

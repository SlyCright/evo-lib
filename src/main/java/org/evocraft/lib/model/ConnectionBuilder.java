package org.evocraft.lib.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ConnectionBuilder {

    public static Map<Integer, Connection> generateConnections(Map<Integer, Cell> cells, int connectionsTotal) {
        Map<Integer, Connection> connections = new HashMap<>();
        Cell inputCell, outPutCell;
        Connection connection;

        for (int i = 0; i < connectionsTotal; i++) {
            float randomWeight=new Random().nextFloat();
            connection = new Connection(randomWeight);

            inputCell = getRandomCellOf(cells);
            outPutCell = getRandomCellOf(cells);

            connection.setInputTileIndex(inputCell.getTileIndex());
            connection.setOutputTileIndex(outPutCell.getTileIndex());

            connections.put(connection.hashCode(), connection);
        }
        return connections;
    }

    protected static Cell getRandomCellOf(Map<Integer, Cell> cells) {
        int randomCellIndex = new Random().nextInt(cells.size());
        return cells.values().stream()
                .skip(randomCellIndex)
                .findFirst()
                .get();
    }

}

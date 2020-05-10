package org.evocraft.lib.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionBuilderTest {

    @Test
    void generateConnections() {
        int cellsTotal = 12;
        int connectionsTotal = cellsTotal / 2;
        Map<Integer, Cell> cells = CellsBuilder.generateCells(cellsTotal);

        Map<Integer, Connection> connections = ConnectionBuilder.generateConnections(cells, connectionsTotal);

        assertNotNull(connections);
        assertTrue(connections.size() > 0);
        assertTrue(connections.size() <= connectionsTotal);
    }

    @Test
    void getRandomCellOf() {
        int cellsTotal = 12;
        Map<Integer, Cell> cells = CellsBuilder.generateCells(cellsTotal);

        boolean randomChoseIsOk = false;

        for (int i = 0; i < cellsTotal; i++) {
            Cell cell_i = ConnectionBuilder.getRandomCellOf(cells);
            for (int j = 0; j < 1000; j++) {
                Cell cell_j = ConnectionBuilder.getRandomCellOf(cells);
                if (cell_i.getTileIndex().hashCode() == cell_j.getTileIndex().hashCode()) {
                    randomChoseIsOk = true;
                    break;
                }
            }
        }

        assertTrue(randomChoseIsOk);

    }
}
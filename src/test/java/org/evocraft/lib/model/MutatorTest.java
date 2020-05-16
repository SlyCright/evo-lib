package org.evocraft.lib.model;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MutatorTest {

    @Test
    void mutateConnections() {
        Map<Integer, Cell> cells = CellsBuilder.generateCells(20);
        Map<Integer, Connection> connections = ConnectionBuilder.generateConnections(cells, 20);

        System.out.println("connections.size() before = " + connections.size());

        Mutator.mutateConnections(connections, cells);

        for (Connection connection : connections.values()) {
            assertNotNull(connection);
        }
        System.out.println("connections.size() = after" + connections.size());
    }
}
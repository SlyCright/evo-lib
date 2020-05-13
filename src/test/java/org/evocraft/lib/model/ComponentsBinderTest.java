package org.evocraft.lib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class ComponentsBinderTest {

    @RepeatedTest(10)
    void bindCellsAndConnections() {
        Map<Integer, Cell> cells = CellsBuilder.generateCells(3); //todo make particulars cells and connections and check binds
        Map<Integer, Connection> connections = ConnectionBuilder.generateConnections(cells, 100);

        int sizeIfHashCanCollide = connections.size();
        boolean ifSizeOK = (sizeIfHashCanCollide == 5) || (sizeIfHashCanCollide == 6);
        assertTrue(ifSizeOK);

        ComponentsBinder.bindCellsAndConnections(cells, connections);

        for (Cell cell : cells.values()) {
            int sizeInput = cell.getInputConnections().size();
            assertTrue(1 <= sizeInput && sizeInput <= 2);
            int sizeOutput = cell.getOutputConnections().size();
            assertTrue(1 <= sizeOutput && sizeOutput <= 2);

        }

        for (Connection connection : connections.values()) {
            assertNotNull(connection.getInput());
            assertNotNull(connection.getOutput());
        }
    }

    @Test
    void bindCellsAndNodes() {

        int cellsTotal = 44;
        Map<Integer, Cell> cells = CellsBuilder.generateCells(cellsTotal);
        assertEquals(cellsTotal, cells.size());//todo move to another test

        Map<Integer, Node> nodes = NodesBuilder.generateNodes(cells);

        for (Cell cell : cells.values()) {
            assertEquals(0, cell.getAdjacentNodes().size());
        }

        ComponentsBinder.bindCellsAndNodes(cells, nodes);

        for (Cell cell : cells.values()) {
            assertEquals(4, cell.getAdjacentNodes().size());
        }
    }
}
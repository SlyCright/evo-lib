package org.evocraft.lib.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ComponentsBinderTest {

    @Test
    void bindCellsAndConnections() {
        Map<Integer, Cell> cells = CellsBuilder.generateCells(3);
        Map<Integer, Connection> connections = ConnectionBuilder.generateConnections(cells, 100);

        assertEquals(6, connections.size());

        ComponentsBinder.bindCellsAndConnections(cells, connections);

        for (Cell cell : cells.values()) {
            assertEquals(2, cell.getInputConnections().size());
            assertEquals(2, cell.getOutputConnections().size());
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
        assertEquals(cellsTotal * 4, nodes.size());//todo move to another test

        for (Cell cell : cells.values()) {
            assertEquals(0, cell.getAdjacentNodes().size());
        }

        ComponentsBinder.bindCellsAndNodes(cells, nodes);

        for (Cell cell : cells.values()) {
            assertEquals(4, cell.getAdjacentNodes().size());
        }


        assertTrue(false);
    }
}
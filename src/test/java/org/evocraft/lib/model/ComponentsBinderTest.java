package org.evocraft.lib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ComponentsBinderTest {

    @Test
    void bindCellsAndConnections() {
        Map<Integer, Cell> cells = CellsBuilder.generateCells(3); //todo make particulars cells and connections and check binds
        Map<Integer, Connection> connections = ConnectionBuilder.generateConnections(cells, 10000);

        for (Connection connection : connections.values()) {
            TileIndex from=connection.getInputTileIndex();
            TileIndex to=connection.getOutputTileIndex();
            System.out.println("from:"+from.i+","+from.j+" to:"+to.i+","+to.j);
        }

        assertEquals(9, connections.size());

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

        for (Cell cell : cells.values()) {
            assertEquals(0, cell.getAdjacentNodes().size());
        }

        ComponentsBinder.bindCellsAndNodes(cells, nodes);

        for (Cell cell : cells.values()) {
            assertEquals(4, cell.getAdjacentNodes().size());
        }
    }
}
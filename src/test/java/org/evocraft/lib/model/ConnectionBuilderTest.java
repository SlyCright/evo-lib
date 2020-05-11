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

        cellsTotal = 3;

        cells = CellsBuilder.generateCells(cellsTotal);
        Connection connectionOne = new Connection(0f);
        Connection connectionTwo = new Connection(0f);
        for (int i = 0; i < 1000; i++) {
            Cell inputCellOne = ConnectionBuilder.getRandomCellOf(cells);
            Cell outputCellOne = ConnectionBuilder.getRandomCellOf(cells);
            connectionOne.setInputTileIndex(inputCellOne.getTileIndex());
            connectionOne.setOutputTileIndex(outputCellOne.getTileIndex());
            for (int j = 0; j < 1000; j++) {
                Cell inputCellTwo = ConnectionBuilder.getRandomCellOf(cells);
                Cell outputCellTwo = ConnectionBuilder.getRandomCellOf(cells);
                connectionTwo.setInputTileIndex(inputCellTwo.getTileIndex());
                connectionTwo.setOutputTileIndex(outputCellTwo.getTileIndex());

                int hashInputOne = inputCellOne.getTileIndex().hashCode(); System.out.println("hashInputOne: "+hashInputOne);
                int hashInputTwo = inputCellTwo.getTileIndex().hashCode();System.out.println("hashInputTwo: "+hashInputTwo);
                int hashOutputOne = outputCellOne.getTileIndex().hashCode();System.out.println("hashOutputOne: "+hashOutputOne);
                int hashOutputTwo = outputCellTwo.getTileIndex().hashCode();System.out.println("hashOutputTwo: "+hashOutputTwo);

                if (hashInputOne != hashInputTwo
                        || hashOutputOne != hashOutputTwo) {
                    int hashOne = connectionOne.hashCode();
                    int hashTwo = connectionTwo.hashCode();
                    System.out.println("one i;" + connectionOne.getInput().getTileIndex().i +
                            " one j:" + connectionOne.getInput().getTileIndex().j +
                            " one hash;" + connectionOne.hashCode());
                    System.out.println("two i;" + connectionTwo.getInput().getTileIndex().i +
                            " two j:" + connectionTwo.getInput().getTileIndex().j +
                            " two hash;" + connectionTwo.hashCode());
                    assertFalse(hashOne == hashTwo);
                }
            }
        }


    }

    @Test
    void getRandomCellOf() {
        int cellsTotal = 12;
        Map<Integer, Cell> cells = CellsBuilder.generateCells(cellsTotal);

        boolean randomChoseIsOk = false;

        for (int i = 0; i < cellsTotal; i++) {
            Cell cell_i = ConnectionBuilder.getRandomCellOf(cells);
            assertNotNull(cell_i);
            for (int j = 0; j < 1000; j++) {
                Cell cell_j = ConnectionBuilder.getRandomCellOf(cells);
                assertNotNull(cell_j);
                if (cell_i.getTileIndex().hashCode() == cell_j.getTileIndex().hashCode()) {
                    randomChoseIsOk = true;
                    break;
                }
            }
        }

        assertTrue(randomChoseIsOk);

    }

}
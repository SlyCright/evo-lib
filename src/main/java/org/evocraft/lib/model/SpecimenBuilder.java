package org.evocraft.lib.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
public class SpecimenBuilder {

    final static private int CELLS_TOTAL = 12;
    final static private int CONNECTIONS_TOTAL = 6;

    public static ArrayList<Specimen> generateSpecies(int specimensTotal) {
        ArrayList<Specimen> species = new ArrayList<>(specimensTotal);
        for (int i = 0; i < specimensTotal; i++) {
            species.add(generateSpecimen());
        }
        return species;
    }

    protected static Specimen generateSpecimen() {
        Map<Integer, Cell> cells = CellsBuilder.generateCells(CELLS_TOTAL);
        Map<Integer, Connection> connections = ConnectionBuilder.generateConnections(cells, CONNECTIONS_TOTAL);

        return createSpecimenOfCellsAndConnections(cells, connections);
    }

    public static Specimen createSpecimenOfCellsAndConnections(Map<Integer, Cell> cells, Map<Integer, Connection> connections) {
        Map<Integer, Membrane> membranes = MembraneBuilder.generateMembranes(cells);
        Map<Integer, Node> nodes = NodesBuilder.generateNodes(cells);

        ComponentsBinder.bindCellsAndConnections(cells, connections);
        ComponentsBinder.bindCellsAndMembranes(cells, membranes);
        ComponentsBinder.bindCellsAndNodes(cells, nodes);
        ComponentsBinder.bindMembranesAndNodes(membranes, nodes);

        Specimen specimen = new Specimen();

        insertCellsIntoSpecimen(specimen, cells);
        insertConnectionsIntoSpecimen(specimen, connections);
        insertMembranesIntoSpecimen(specimen, membranes);
        insertNodesIntoSpecimen(specimen, nodes);

        return specimen;
    }

    private static void insertCellsIntoSpecimen(Specimen specimen, Map<Integer, Cell> cells) {

    }

    private static void insertConnectionsIntoSpecimen(Specimen specimen, Map<Integer, Connection> connections) {
//todo connection valid check: binded cell isn't null
    }

    private static void insertMembranesIntoSpecimen(Specimen specimen, Map<Integer, Membrane> membranes) {

    }

    private static void insertNodesIntoSpecimen(Specimen specimen, Map<Integer, Node> nodes) {

    }
}





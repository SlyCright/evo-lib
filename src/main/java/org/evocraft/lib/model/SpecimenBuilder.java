package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecimenBuilder {

    final static private int CELLS_TOTAL = 12;
    final static private int CONNECTIONS_GENERATE_TIMES = CELLS_TOTAL * 10;

    public static ArrayList<Specimen> generateSpecies(int specimensTotal) {
        ArrayList<Specimen> species = new ArrayList<>(specimensTotal);
        for (int i = 0; i < specimensTotal; i++) {
            species.add(generateSpecimen());
        }
        return species;
    }

    protected static Specimen generateSpecimen() {
        return generateSpecimen(CELLS_TOTAL, CONNECTIONS_GENERATE_TIMES);
    }

    protected static Specimen generateSpecimen(int cellsTotal, int connectionsGenerateTimes) {
        Map<Integer, Cell> cells = CellsBuilder.generateCells(cellsTotal);
        Map<Integer, Connection> connections = ConnectionBuilder.generateConnections(cells, connectionsGenerateTimes);

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

        specimen.setCells(cells);
        specimen.setConnections(connections);
        specimen.setMembranes(membranes);
        specimen.setNodes(nodes);

        specimen.getComponents().addAll(cells.values());
        insertConnectionsIntoSpecimen(specimen, connections);
        specimen.getComponents().addAll(membranes.values());
        specimen.getComponents().addAll(nodes.values());

        specimen.setPositionOnCreation(specimen.calculatePosition().copy());

        return specimen;
    }

    private static void insertConnectionsIntoSpecimen(Specimen specimen, Map<Integer, Connection> connections) {
        specimen.getComponents()
            .addAll(connections.values().stream()
                .filter(c -> c.getInput() != null && c.getOutput() != null)
                .collect(Collectors.toList()));
    }

}





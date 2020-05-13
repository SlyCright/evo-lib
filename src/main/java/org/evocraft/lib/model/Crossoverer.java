package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Crossoverer {

    private static final float ANCESTORS_PORTION_WHICH_GIVES_OFFSPRINGS = 1f / 3f;
    private static final int SEXES_TOTAL = 2;

    public static ArrayList<Specimen> crossOverBestFittedOf(ArrayList<Specimen> ancestors) {
        int offspringsTotal = ancestors.size();

        killWorstAncestors(ancestors);

        ArrayList<Specimen> offsprings = new ArrayList<>(offspringsTotal);

        for (int i = 0; i < offspringsTotal; i++) {

            List<Specimen> parents = new ArrayList<>();
            for (int j = 0; j < SEXES_TOTAL; j++) {
                parents.add(getRandomSpecimenOf(ancestors));
            }

            Specimen offspring = buildOffspringOf(parents);
            offsprings.add(offspring);
        }

        return offsprings;
    }

    protected static void killWorstAncestors(ArrayList<Specimen> ancestors) {
        int ancestorsTotal = Math.round((float) ancestors.size() * ANCESTORS_PORTION_WHICH_GIVES_OFFSPRINGS);
        for (int i = ancestors.size() - 1; i > ancestorsTotal; i--) {
            ancestors.remove(i);
        }
    }

    private static Specimen getRandomSpecimenOf(ArrayList<Specimen> specimens) {
        int size = specimens.size();
        int randomIndex;
        randomIndex = new Random().nextInt(size);
        Specimen randomSpecimen = specimens.stream()
            .skip(randomIndex)
            .findFirst()
            .get();
        return randomSpecimen;
    }

    public static Specimen buildOffspringOf(List<Specimen> parents) {
        List<Cell> parentsCells = new ArrayList<>();
        List<Connection> parentsConnections = new ArrayList<>();

        for (Specimen parent : parents) {
            parentsCells.addAll(parent.getCells().values());
            parentsConnections.addAll(parent.getConnections().values());
        }

        Map<Integer, Cell> offspringCells = generateOffspringCells(parentsCells);
        Map<Integer, Connection> offSpringConnections = mapOffspringConnections(parentsConnections);

        return SpecimenBuilder.createSpecimenOfCellsAndConnections(offspringCells, offSpringConnections);
    }

    private static Map<Integer, Cell> generateOffspringCells(List<Cell> listedParentsCells) {
        Map<Integer, ArrayList<Cell>> mappedParentsCells = mapParentsCells(listedParentsCells);
        Map<Integer, Cell> offspringCells = mapOffspringCells(mappedParentsCells);
        Mutator.mutate(offspringCells);
        return offspringCells;
    }

    private static Map<Integer, Cell> mapOffspringCells(Map<Integer, ArrayList<Cell>> mappedParentsCells) {
        Map<Integer, Cell> offspringCells = new HashMap<>();

        for (Integer key : mappedParentsCells.keySet()) {
            float randomFloat = new Random().nextFloat() * (float) SEXES_TOTAL;
            long randomIndex = Math.round(Math.floor(randomFloat));
            List<Cell> parentsCells = mappedParentsCells.get(key);
            Cell parentCell = parentsCells.get((int) randomIndex);
            Cell offspringCell=parentCell.copy();
            offspringCells.put(offspringCell.hashCode(), offspringCell);
        }

        return offspringCells;
    }

    protected static Map<Integer, ArrayList<Cell>> mapParentsCells(List<Cell> parentsCells) {
        Map<Integer, ArrayList<Cell>> mappedParentsCells = new HashMap<>();

        for (Cell parentCell : parentsCells) {
            int key = parentCell.hashCode();
            ArrayList<Cell> cellsForKey = mappedParentsCells.get(key);

            if (cellsForKey == null) {
                cellsForKey = new ArrayList<>(SEXES_TOTAL);
                cellsForKey.add(parentCell);
                mappedParentsCells.put(key, cellsForKey);
            } else {
                cellsForKey.add(parentCell);
            }
        }

        return mappedParentsCells;
    }

}

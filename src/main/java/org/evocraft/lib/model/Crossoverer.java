package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Crossoverer {

    private static final float SPECIMENS_PORTION_WHICH_BECOMES_ANCESTOR = 1f / 3f;
    private static final int SEXES_TOTAL = 2;

    public static ArrayList<Specimen> crossOverBestFittedOf(ArrayList<Specimen> ancestors) {
        int offspringsTotal = ancestors.size();

        killWorst(ancestors);

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

    protected static void killWorst(ArrayList<Specimen> specimens) {
        int ancestorsTotal = Math.round((float) specimens.size() * SPECIMENS_PORTION_WHICH_BECOMES_ANCESTOR);
        for (int i = specimens.size() - 1; i > ancestorsTotal; i--) {
            specimens.remove(i);
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

    protected static Specimen buildOffspringOf(List<Specimen> parents) {
        List<SpecimenComponent> parentsComponents = new ArrayList<>(); //todo refactor: use List<Copyable> instead of List<SpecimenComponent>

        for (Specimen parent : parents) {
            parentsComponents.addAll(parent.getCells().values());
            parentsComponents.addAll(parent.getConnections().values());
        }

        List<SpecimenComponent> offSpringComponents = generateOffspringComponents(parentsComponents);

        Map<Integer, Cell> offspringCells = mapOffspringCells(offSpringComponents);
        Map<Integer, Connection> offSpringConnections = mapOffspringConnections(offSpringComponents);

        Mutator.mutateCells(offspringCells);
        Mutator.mutateConnections(offSpringConnections, offspringCells);

        return SpecimenBuilder.createSpecimenOfCellsAndConnections(offspringCells, offSpringConnections);
    }

    protected static List<SpecimenComponent> generateOffspringComponents(List<SpecimenComponent> parentsComponents) {
        Map<Integer, List<SpecimenComponent>> mappedParentsComponents = mapParentsComponents(parentsComponents);
        return listOffspringComponents(mappedParentsComponents);
    }

    protected static Map<Integer, List<SpecimenComponent>> mapParentsComponents(List<SpecimenComponent> parentsComponents) {
        Map<Integer, List<SpecimenComponent>> mappedParentsComponents = new HashMap<>();

        for (SpecimenComponent parentComponent : parentsComponents) {
            int key = parentComponent.hashCode();
            List<SpecimenComponent> componentsByKey = mappedParentsComponents.get(key);

            if (componentsByKey == null) {
                componentsByKey = new ArrayList<>(SEXES_TOTAL);
                componentsByKey.add(parentComponent);
                mappedParentsComponents.put(key, componentsByKey);
            } else {
                componentsByKey.add(parentComponent);
            }
        }

        return mappedParentsComponents;
    }

    protected static List<SpecimenComponent> listOffspringComponents(Map<Integer, List<SpecimenComponent>> mappedParentsComponents) {
        List<SpecimenComponent> offspringComponents = new ArrayList<>();

        for (Integer key : mappedParentsComponents.keySet()) {
            float randomFloat = new Random().nextFloat() * (float) SEXES_TOTAL;
            long randomIndex = Math.round(Math.floor(randomFloat));
            List<SpecimenComponent> parentsComponents = mappedParentsComponents.get(key);
            if (randomIndex < parentsComponents.size()) {
                SpecimenComponent parentComponent = parentsComponents.get((int) randomIndex);
                SpecimenComponent offspringComponent = parentComponent.copy();
                offspringComponents.add(offspringComponent);
            }
        }

        return offspringComponents;
    }

    protected static Map<Integer, Cell> mapOffspringCells(List<SpecimenComponent> offSpringComponents) {
        Map<Integer, Cell> offspringCells = new HashMap<>();
        for (SpecimenComponent component : offSpringComponents) {
            if (component instanceof Cell) {
                Cell cell = (Cell) component;
                offspringCells.put(cell.hashCode(), cell);
            }
        }
        return offspringCells;
    }

    protected static Map<Integer, Connection> mapOffspringConnections(List<SpecimenComponent> offSpringComponents) {
        Map<Integer, Connection> offspringConnections = new HashMap<>();
        for (SpecimenComponent component : offSpringComponents) {
            if (component instanceof Connection) {
                Connection connection = (Connection) component;
                offspringConnections.put(connection.hashCode(), connection);
            }
        }
        return offspringConnections;
    }

}

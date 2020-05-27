package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Crossoverer {

    private static final float SPECIMENS_PORTION_WHICH_BECOMES_ANCESTOR = 1f / 3f;
    public static final int SEXES_TOTAL = 2;

    private static final boolean IF_ELITISM_IS_ON = true;
    private static final float ELITE_PORTION = 0.1f;

    public static ArrayList<Specimen> crossOverBestFittedOf(ArrayList<Specimen> ancestors) {
        int offspringsTotal = ancestors.size();
        int eliteTotal = Math.round((float) offspringsTotal * ELITE_PORTION);

        ArrayList<Specimen> elite = saveElite(ancestors, eliteTotal);

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

    protected static ArrayList<Specimen> saveElite(List<Specimen> ancestors, int eliteTotal) {
        List<Specimen> elite=new ArrayList<>();

        for (int i = 0; i < eliteTotal; i++) {
            Specimen specimen = ancestors.get(i).copy();
        }
        return elite;
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

        Map<Integer, Cell> offspringCells = mapCellsFromComponentList(offSpringComponents);

        Map<Integer, Connection> offSpringConnections = mapConnectionsFromComponentList(offSpringComponents);

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

    protected static Map<Integer, Cell> mapCellsFromComponentList(List<SpecimenComponent> offSpringComponents) { //todo refactor: use stream here
        Map<Integer, Cell> mappedCells = new HashMap<>();
        for (SpecimenComponent component : offSpringComponents) {
            if (component instanceof Cell) {
                Cell cell = (Cell) component;
                mappedCells.put(cell.hashCode(), cell);
            }
        }
        return mappedCells;
    }

    protected static Map<Integer, Connection> mapConnectionsFromComponentList(List<SpecimenComponent> listedComponents) { //todo refactor: use stream here
        Map<Integer, Connection> mappedConnections = new HashMap<>();

        for (SpecimenComponent component : listedComponents) {
            if (component instanceof Connection) {
                Connection connection = (Connection) component;
                mappedConnections.put(connection.hashCode(), connection);
            }
        }

        return mappedConnections;
    }

}

package org.evocraft.lib.model;

import java.util.*;

public class Crossoverer {

    private static final float ANCESTORS_PORTION_WHICH_GIVES_OFFSPRINGS = 1f / 3f;
    private static final int SEXES_TOTAL = 2;

    public static ArrayList<Specimen> crossOverBestFittedOf(ArrayList<Specimen> ancestors) {
        int offspringsTotal = ancestors.size();

        killWorstAncestors(ancestors);

        ArrayList<Specimen> offsprings = new ArrayList<>(offspringsTotal);

        for (int i = 0; i < offspringsTotal; i++) {

            List<Specimen> parents = new ArrayList();
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

        Map<Integer, Cell> offspringCells = mapOffspringCells(parentsCells);
        Map<Integer, Connection> offSpringConnections = mapOffspringConnections(parentsConnections);

        return SpecimenBuilder.createSpecimenOfCellsAndConnections(offspringCells, offSpringConnections);
    }

    private static Map<Integer, Cell> mapOffspringCells(List<Cell> parentsCells) {
        Map<Integer, ArrayList<Cell>> mappedParentsCells = mapParentsCells(parentsCells);
        Map<Integer, Cell> offspringCells = mapOffspringCells(mappedParentsCells);
        Mutator.mutate(offspringCells);
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


    private static Map<Integer, Connection> mapOffspringConnections(Map<Integer, Connection> specimenLeft, Map<Integer, Connection> specimenRight) {
        return null;
    }

    protected static Map<Integer, SpecimenComponent> mapOffspringComponents(Specimen ancestorLeft, Specimen ancestorRight) {


        mapComponentsPair(pairsOfAncestorsComponents, componentsOfAncestorLeft, Pair.LEFT);
        mapComponentsPair(pairsOfAncestorsComponents, componentsOfAncestorRight, Pair.RIGHT);

        for (Integer key : pairsOfAncestorsComponents.keySet()) {

            PairOfAncestorsComponents pairOfAncestorsComponents = pairsOfAncestorsComponents.get(key);
            SpecimenComponent componentLeft = pairOfAncestorsComponents.getLeft();
            SpecimenComponent componentRight = pairOfAncestorsComponents.getRight();

            SpecimenComponent offspringComponent = null;

            if (new Random().nextFloat() < 0.5f) {
                if (componentLeft != null) {
                    offspringComponent = Copier.copy(componentLeft);
                }
            } else {
                if (componentRight != null) {
                    offspringComponent = Copier.copy(componentRight);
                }
            }

            if (offspringComponent != null) {
                offspringComponents.put(key, offspringComponent);
            }
        }

        return offspringComponents;
    }

    private static void mapComponentsPair(
            Map<Integer, PairOfAncestorsComponents> pairsOfAncestorsComponents,
            Map<Integer, SpecimenComponent> componentsOfAncestor,
            Pair ancestorNumber) {

        for (SpecimenComponent component : componentsOfAncestor.values()) {
            int key = component.hashCode();
            PairOfAncestorsComponents pairOfAncestorsComponents = pairsOfAncestorsComponents.get(key);
            if (pairOfAncestorsComponents == null) {
                pairOfAncestorsComponents = new PairOfAncestorsComponents();
                pairsOfAncestorsComponents.put(key, pairOfAncestorsComponents);
            }
            switch (ancestorNumber) {
                case LEFT:
                    pairOfAncestorsComponents.setLeft(component);
                    break;
                case RIGHT:
                    pairOfAncestorsComponents.setRight(component);
                    break;
            }
        }
    }

    private static Map<Integer, SpecimenComponent> mapComponentsFrom(Specimen specimen) {
        Map<Integer, SpecimenComponent> components = new HashMap<>();

        for (SpecimenComponent component : specimen.getComponents()) {
            if (component instanceof Cell || component instanceof Connection) {
                components.put(component.hashCode(), component);
            }
        }

        return components;
    }
}

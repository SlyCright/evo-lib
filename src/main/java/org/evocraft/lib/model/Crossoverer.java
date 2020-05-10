package org.evocraft.lib.model;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Crossoverer {

    private static final float ANCESTORS_PORTION_WHICH_GIVES_OFFSPRINGS = 1f / 3f;

    public static ArrayList<Specimen> crossOverBestFittedOf(ArrayList<Specimen> ancestors) {
        int offspringsTotal = ancestors.size();

        int ancestorsTotal = Math.round((float) ancestors.size() * ANCESTORS_PORTION_WHICH_GIVES_OFFSPRINGS);
        for (int i = ancestors.size() - 1; i > ancestorsTotal; i--) {
            ancestors.remove(i);
        }
        SpecimenBuilder specimenBuilder = new SpecimenBuilder();

        ArrayList<Specimen> offsprings = new ArrayList<>(offspringsTotal);

        for (int i = 0; i < offspringsTotal; i++) {
            Specimen ancestorOne = getRandomSpecimenOf(ancestors);
            Specimen ancestorTwo = getRandomSpecimenOf(ancestors);
            Specimen offspring = buildOffspringSpecimenOf(ancestorOne, ancestorTwo);
            offsprings.add(offspring);
        }

        return offsprings;
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

    public static Specimen buildOffspringSpecimenOf(Specimen specimenLeft, Specimen specimenRight) { //todo refactor: instead of position argument make separated method moveSpecimenToPosition(Specimen, Position)
        Map<Integer, SpecimenComponent> offspringComponents = mapOffspringComponents(specimenLeft, specimenRight);
        return SpecimenBuilder.createSpecimenOfCellsAndConnections(null,null);
    }

    protected static Map<Integer, SpecimenComponent> mapOffspringComponents(Specimen ancestorLeft, Specimen ancestorRight) {
        Map<Integer, SpecimenComponent> offspringComponents = new HashMap<>();
        Map<Integer, SpecimenComponent> componentsOfAncestorLeft = mapComponentsFrom(ancestorLeft);
        Map<Integer, SpecimenComponent> componentsOfAncestorRight = mapComponentsFrom(ancestorRight);
        Map<Integer, PairOfAncestorsComponents> pairsOfAncestorsComponents = new HashMap<>();
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

package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;

@Getter
@Setter
public class World implements Actionable {

    public static final int SPECIMENS_TOTAL = 120;
    private final int EPOCH_LASTING_TICKS = 1_000;
    private final float INITIAL_X_OF_SPECIMEN = 999f / 2f, INITIAL_Y_OF_SPECIMEN = 666f / 2f;

    private ArrayList<Specimen> species;
    private EpochTicker epochTicker = new EpochTicker(EPOCH_LASTING_TICKS);
    private PVector initialPositionOfSpecimens = new PVector(INITIAL_X_OF_SPECIMEN, INITIAL_Y_OF_SPECIMEN);

    private volatile Specimen bestSpecimen;

    public World() {
        species = SpecimenBuilder.generateSpecies(SPECIMENS_TOTAL);
        moveSpeciesToInitialPosition(species, initialPositionOfSpecimens);
    }

    public void moveSpeciesToInitialPosition(ArrayList<Specimen> species, PVector initialPositionOfSpecimens) {
        for (Specimen specimen : species) {
            for (Node node : specimen.getNodes().values()) {
                node.getPosition().add(initialPositionOfSpecimens);
            }
        }
    }

    @Override
    public void act() {
        Environment.interactWith(species);
        calculateNextTickFor(species);
        epochTicker.act();

        if (epochTicker.isEpochEnded()) {
            species = createNextGenerationOf(species);
            moveSpeciesToInitialPosition(species, initialPositionOfSpecimens);
            epochTicker.restartEpoch();
        }
    }

    protected ArrayList<Specimen> createNextGenerationOf(ArrayList<Specimen> ancestors) {
        sortByFitness(ancestors);
        this.setBestSpecimen(species.get(0));
        return Crossoverer.crossOverBestFittedOf(ancestors);
    }

    protected void sortByFitness(List<Specimen> species) {

        Comparator<Specimen> specimenComparator = (s1, s2) -> {

            float fitness1 = calculateFitnessOf(s1);
            float fitness2 = calculateFitnessOf(s2);

            return Float.compare(fitness2, fitness1);
        };

        species.sort(specimenComparator);
    }

    private float calculateFitnessOf(Specimen specimen) {
        PVector specimenPosition = specimen.calculatePosition().copy();
        PVector initialPosition = specimen.getPositionOnCreation().copy();
        return PVector.dist(specimenPosition, initialPosition);
    }

    private void calculateNextTickFor(List<Specimen> species) {
        for (Specimen specimen : species) {
            specimen.act();
        }
    }

}

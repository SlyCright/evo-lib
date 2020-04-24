package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Data;
import processing.core.PVector;

@Data
public class World implements Actionable {

    private final int SPECIMENS_TOTAL = 5;
    private final int EPOCH_LASTING_TICKS = 1000;
    final private float INITIAL_X_OF_SPECIMEN = 999f / 2f, INITIAL_Y_OF_SPECIMEN = 666f / 2f;

    private ArrayList<Specimen> species;
    private EpochTicker epochTicker = new EpochTicker(EPOCH_LASTING_TICKS);
    private PVector initialPositionOfSpecimens=new PVector(INITIAL_X_OF_SPECIMEN,INITIAL_Y_OF_SPECIMEN);

    public World() {
        species = createSpecies(SPECIMENS_TOTAL);
    }

    private ArrayList<Specimen> createSpecies(int specimensTotal) {
        SpecimenBuilder specimenBuilder = new SpecimenBuilder();
        ArrayList<Specimen> species = new ArrayList<>(specimensTotal);

        for (int i = 0; i < specimensTotal; i++) {
            Specimen specimen = specimenBuilder.buildSpecimenAt(initialPositionOfSpecimens);
            species.add(specimen);
        }

        return species;
    }

    @Override
    public void act() {
        epochTicker.act();
        if (epochTicker.isEpochEnded()) {
            species = createNextGenerationOf(species);
            epochTicker.restartEpoch();
        }
        calculateNextTickFor(species);
    }

    private ArrayList<Specimen> createNextGenerationOf(ArrayList<Specimen> ancestors) {
        sortByFitness(ancestors);
        ArrayList<Specimen> offsprings = crossOverBestFittedOf(ancestors);
        mutate(offsprings);
        return offsprings;
    }

    private void sortByFitness(List<Specimen> species) {

        Comparator<Specimen> specimenComparator = (s1, s2) -> {

            float fitness1 = calculateFitnessOf(s1);
            float fitness2 = calculateFitnessOf(s2);

            return Float.compare(fitness1, fitness2);
        };

        species.sort(specimenComparator);
    }

    private float calculateFitnessOf(Specimen specimen) {
        PVector speoimenPosition = specimen.getPosition().copy();
        PVector initialPosition = this.initialPositionOfSpecimens.copy();
        float fitness = PVector.dist(speoimenPosition, initialPosition);
        return fitness;
    }

    private ArrayList<Specimen> crossOverBestFittedOf(ArrayList<Specimen> ancestors) {
        //todo some code here

        if (ancestors.size() > 1) {
            ancestors.remove(0);
        }
        return ancestors;
    }

    private void mutate(List<Specimen> offsprings) {
        //todo some code here
    }

    private void calculateNextTickFor(List<Specimen> species) {
        for (Specimen specimen : species) {
            specimen.act();
        }
    }

}

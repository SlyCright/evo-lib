package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;

@Getter
@Setter
public class World implements Actionable {

    private final int SPECIMENS_TOTAL = 6;
    private final int EPOCH_LASTING_TICKS = 1000;
    private final float INITIAL_X_OF_SPECIMEN = 999f / 2f, INITIAL_Y_OF_SPECIMEN = 666f / 2f;
    private final float ANCESTORS_PORTION_WHICH_GIVES_OFFSPRINGS = 1f / 3f;

    private ArrayList<Specimen> species;
    private EpochTicker epochTicker = new EpochTicker(EPOCH_LASTING_TICKS);
    private PVector initialPositionOfSpecimens = new PVector(INITIAL_X_OF_SPECIMEN, INITIAL_Y_OF_SPECIMEN);

    public World() {
        species = createSpecies(SPECIMENS_TOTAL);
    }

    private ArrayList<Specimen> createSpecies(int specimensTotal) {
        SpecimenBuilder specimenBuilder = new SpecimenBuilder();
        ArrayList<Specimen> species = new ArrayList<>(specimensTotal);

        for (int i = 0; i < specimensTotal; i++) {
            Specimen specimen = specimenBuilder.buildZeroGenerationSpecimenAt(initialPositionOfSpecimens);
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

            return Float.compare(fitness2, fitness1);
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
            Specimen offspring = specimenBuilder.buildOffspringSpecimenOf(ancestorOne, ancestorTwo, initialPositionOfSpecimens);
            offsprings.add(offspring);
        }

        return offsprings;
    }

    private Specimen getRandomSpecimenOf(ArrayList<Specimen> specimens) {
        int size = specimens.size();
        int randomIndex;
        randomIndex = new Random().nextInt(size);
        Specimen randomSpecimen = specimens.stream()
            .skip(randomIndex)
            .findFirst()
            .get();
        return randomSpecimen;
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

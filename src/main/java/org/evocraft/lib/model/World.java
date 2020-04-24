package org.evocraft.lib.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class World implements Actionable {

    private final int SPECIMENS_TOTAL = 5;
    private final int EPOCH_LASTING_TICKS = 1000;

    private ArrayList<Specimen> species;
    private EpochTicker epochTicker = new EpochTicker(EPOCH_LASTING_TICKS);

    public World() {
        species = createSpecies(SPECIMENS_TOTAL);
    }

    private ArrayList<Specimen> createSpecies(int specimensTotal) {
        SpecimenBuilder specimenBuilder = new SpecimenBuilder();
        ArrayList<Specimen> species = new ArrayList<>(specimensTotal);

        for (int i = 0; i < specimensTotal; i++) {
            Specimen specimen = specimenBuilder.buildSpecimen();
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

    private void calculateNextTickFor(List<Specimen> species) {
        for (Specimen specimen : species) {
            specimen.act();
        }
    }

    private ArrayList<Specimen> createNextGenerationOf(ArrayList<Specimen> ancestors) {
        sortByFitness(ancestors);
        ArrayList<Specimen> offsprings = crossOverBestFittedOf(ancestors);
        mutate(offsprings);
        return offsprings;
    }

    private void sortByFitness(List<Specimen> species) {
        //todo some code here
    }

    private ArrayList<Specimen> crossOverBestFittedOf(List<Specimen> ancestors) {
        //todo some code here
        return null;
    }

    private void mutate(List<Specimen> offsprings) {
        //todo some code here
    }
}

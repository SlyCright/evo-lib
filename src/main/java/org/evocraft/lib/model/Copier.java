package org.evocraft.lib.model;

public class Copier {
    public static SpecimenComponent copy(SpecimenComponent component) {
        return null;
    }

    public Neuron copy(Neuron neuron) {
        Neuron neuronCopy = new Neuron(neuron.getThreshold());
        neuron.setTileIndex(neuron.getTileIndex());
        return neuronCopy;
    }

    public Oscillator copy(Oscillator oscillator) {
        Oscillator oscillatorCopy = new Oscillator(oscillator.getACTIVATION_PERIOD_TICKS());
        oscillatorCopy.setTileIndex(oscillator.getTileIndex());
        return oscillatorCopy;
    }
}

package org.evocraft.lib.model;

public class Copier {

    public static Muscle copy(Muscle muscle) {
        Muscle muscleCopy = new Muscle(muscle.getSizeWhenActivated());
        muscleCopy.setTileIndex(muscle.getTileIndex());
        return muscleCopy;
    }

    public static Neuron copy(Neuron neuron) {
        Neuron neuronCopy = new Neuron(neuron.getThreshold());
        neuronCopy.setTileIndex(neuron.getTileIndex());
        return neuronCopy;
    }

    public static Oscillator copy(Oscillator oscillator) {
        Oscillator oscillatorCopy = new Oscillator(oscillator.getACTIVATION_PERIOD_TICKS());
        oscillatorCopy.setTileIndex(oscillator.getTileIndex());
        return oscillatorCopy;
    }

    public static Connection copy(Connection connection) {
        Connection connectionCopy = new Connection(connection.getWeight());
        connectionCopy.setInputTileIndex(connection.getInputTileIndex());
        connectionCopy.setOutputTileIndex(connection.getOutputTileIndex());
        return connectionCopy;
    }
}

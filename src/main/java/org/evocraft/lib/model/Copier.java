package org.evocraft.lib.model;

public class Copier {

    public static Muscle copy(Muscle muscle) {
        Muscle muscleCopy = new Muscle(
                muscle.getSizeWhenActivated(),
                muscle.isDoesReverseSignal());
        muscleCopy.setTileIndex(muscle.getTileIndex());
        return muscleCopy;
    }

    public static Neuron copy(Neuron neuron) {
        Neuron neuronCopy = new Neuron(
                neuron.getThreshold(),
                neuron.isDoesReverseSignal());
        neuronCopy.setTileIndex(neuron.getTileIndex());
        return neuronCopy;
    }

    public static Oscillator copy(Oscillator oscillator) {
        Oscillator oscillatorCopy = new Oscillator(
                oscillator.getActivationPeriodInTicks(),
                oscillator.getPeriodStartShift(),
                oscillator.isDoesReverseSignal());
        oscillatorCopy.setTileIndex(oscillator.getTileIndex());
        return oscillatorCopy;
    }

    public static Connection copy(Connection connection) {
        Connection connectionCopy = new Connection(
                connection.getWeight(),
                connection.isDoesReverseSignal());
        connectionCopy.setInputTileIndex(connection.getInputTileIndex());
        connectionCopy.setOutputTileIndex(connection.getOutputTileIndex());
        return connectionCopy;
    }
}

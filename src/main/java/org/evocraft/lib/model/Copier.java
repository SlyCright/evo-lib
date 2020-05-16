package org.evocraft.lib.model;

public class Copier {

    public static Muscle copy(Muscle muscle) {
        System.out.println("muscle.hashCode() = " + muscle.hashCode());
        Muscle muscleCopy = new Muscle(muscle.getSizeWhenActivated());
        muscle.setTileIndex(muscle.getTileIndex());
        System.out.println("muscleCopy.hashCode() = " + muscleCopy.hashCode());
        return muscleCopy;
    }

    public static Neuron copy(Neuron neuron) {
        System.out.println("________________________________________________");
        System.out.println("neuron.hashCode() = " + neuron.hashCode());
        System.out.println("neuron.getThreshold() = " + neuron.getThreshold());
        System.out.println("neuron.getTileIndex().hashCode() = " + neuron.getTileIndex().hashCode());
        int i = neuron.getTileIndex().i;
        int j = neuron.getTileIndex().j;
        Neuron neuronCopy = new Neuron(neuron.getThreshold());
        TileIndex tileIndexCopy = new TileIndex(i, j);
        System.out.println("tileIndexCopy.hashCode() = " + tileIndexCopy.hashCode());
        neuron.setTileIndex(tileIndexCopy);
        System.out.println("neuronCopy.hashCode() = " + neuronCopy.hashCode());
        System.out.println("neuronCopy.getThreshold() = " + neuronCopy.getThreshold());
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        return neuronCopy;
    }

    public static Oscillator copy(Oscillator oscillator) {
        System.out.println("oscillator.hashCode() = " + oscillator.hashCode());
        Oscillator oscillatorCopy = new Oscillator(oscillator.getACTIVATION_PERIOD_TICKS());
        oscillatorCopy.setTileIndex(oscillator.getTileIndex());
        System.out.println("oscillatorCopy.hashCode() = " + oscillatorCopy.hashCode());
        return oscillatorCopy;
    }

    public static Connection copy(Connection connection) {
        System.out.println("connection.hashCode() = " + connection.hashCode());
        Connection connectionCopy = new Connection(connection.getWeight());
        connection.setInputTileIndex(connection.getInputTileIndex());
        connection.setOutputTileIndex(connection.getOutputTileIndex());
        System.out.println("connectionCopy.hashCode() = " + connectionCopy.hashCode());
        return connectionCopy;
    }
}

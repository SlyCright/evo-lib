package org.evocraft.lib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.RepeatedTest;

class NeuronTest {

    @RepeatedTest(10)
    void calculateIfActive() {
        Float threshold = new Random().nextFloat();
        Neuron neuron = new Neuron(threshold);
        ArrayList<Connection> connections = new ArrayList<>();
        int connectionsTotal = new Random().nextInt(100);
        float sumWeight = 0f;

        for (int i = 0; i < connectionsTotal; i++) {
            float weight = new Random().nextFloat();
            sumWeight += weight;
            connections.add(new Connection(weight));
        }

        for (Connection connection : connections) {
            Cell cell = new Muscle();
            cell.setActive(true);
            connection.setInput(cell);
        }

        float signal = sumWeight / connectionsTotal;
        boolean shouldBeIsActive = signal > threshold;

        boolean isActive = neuron.calculateIfActive(connections);

        assertEquals(shouldBeIsActive, isActive);
    }

}
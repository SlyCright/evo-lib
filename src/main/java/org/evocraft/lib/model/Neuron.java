package org.evocraft.lib.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@EqualsAndHashCode(callSuper = true)
public class Neuron extends Cell {

    private final float threshold;

    public Neuron(float threshold) {
        this.threshold = threshold;
    }

    @Override
    public void act() {
        isActive = calculateIfActive(inputConnections);
        super.act();
    }


    protected boolean calculateIfActive(List<Connection> inputConnections) { //todo backlog: depends of DNA here should be other types pf signal calculations. Like sigmoid function or something
        float signal = 0f;
        for (Connection connection : inputConnections) {
            signal += connection.getSignal();
        }
        signal /= inputConnections.size();

        boolean isActive = signal > threshold;

        return isActive;
    }

    @Override
    public Cell copy() {
        return Copier.copy(this);
    }

}

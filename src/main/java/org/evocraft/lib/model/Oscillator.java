package org.evocraft.lib.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Oscillator extends Cell { //todo backlog: add some more types of oscillator: with signal of low of sin, for example, or Aperiodic

    private final int ACTIVATION_PERIOD_TICKS;
    private int currentTick = 0;

    Oscillator(int activationPeriodTicks) {
        this.ACTIVATION_PERIOD_TICKS = activationPeriodTicks;
    }

    public void act() {
        currentTick++;

        if (currentTick > ACTIVATION_PERIOD_TICKS) {
            isActive = !isActive;
            currentTick = 0;
        }

        super.act();
    }

    @Override
    public Oscillator copy() {
        Oscillator oscillator = new Oscillator(this.ACTIVATION_PERIOD_TICKS);
        oscillator.setGridPlace(this.getGridPlace());
        return oscillator;
    }

}

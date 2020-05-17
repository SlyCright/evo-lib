package org.evocraft.lib.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Oscillator extends Cell { //todo backlog: add some more types of oscillator: with signal of low of sin, for example, or Aperiodic

    private final int activationPeriodInTicks;
    private int currentTick;
    private float periodStartShift;

    Oscillator(int activationPeriodInTicks, float periodStartShift, boolean doesReverseSignal) {
        super.doesReverseSignal = doesReverseSignal;
        this.activationPeriodInTicks = activationPeriodInTicks;
        this.periodStartShift=periodStartShift;

        currentTick = Math.round((float) activationPeriodInTicks * periodStartShift);

        if (doesReverseSignal) {
            isActive = !isActive;
        }
    }

    public void act() {
        currentTick++;

        if (currentTick > activationPeriodInTicks) {
            isActive = !isActive;
            currentTick = 0;
        }

        super.act();
    }

    @Override
    public Cell copy() {
        return Copier.copy(this);
    }
}

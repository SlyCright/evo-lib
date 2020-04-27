package org.evocraft.lib.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EpochTicker implements Actionable {

    private final int EPOCH_LASTING_TICKS;
    private int currentTick;
    private boolean epochEnded = false;

    public EpochTicker(int epochLastingTicks) {
        this.EPOCH_LASTING_TICKS = epochLastingTicks;
        this.currentTick = EPOCH_LASTING_TICKS;
    }

    @Override
    public void act() {
        if (!epochEnded) {
            currentTick--;
            epochEnded = currentTick < 0;
        }
    }

    public void restartEpoch() {
        this.currentTick = EPOCH_LASTING_TICKS;
        this.epochEnded = false;
    }
}

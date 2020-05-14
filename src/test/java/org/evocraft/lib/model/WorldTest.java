package org.evocraft.lib.model;

import org.junit.jupiter.api.Test;

class WorldTest {

    @Test
    void act() {
        World world = new World();
        EpochTicker epochTicker = world.getEpochTicker();

        for (int i = 0; i < epochTicker.getEPOCH_LASTING_TICKS()*3 + 10; i++) {
            world.act();
        }
    }

}
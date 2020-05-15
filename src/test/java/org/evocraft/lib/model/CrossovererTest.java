package org.evocraft.lib.model;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CrossovererTest {

    @Test
    void crossOverBestFittedOf() {
        World world = new World();
        for (int i = 0; i < 1000; i++) {
            world.act();
        }
        ArrayList<Specimen> ancestors = world.getSpecies();
        world.sortByFitness(ancestors);

        ArrayList<Specimen> offsprings = Crossoverer.crossOverBestFittedOf(ancestors);

        for (Specimen offspring : offsprings) {
            Connection notNullConnection = null;
            for (SpecimenComponent component : offspring.getComponents()) {
                if (component instanceof Connection) {
                    notNullConnection = (Connection) component;
                }
            }
            assertNotNull(notNullConnection);
        }
    }

    @RepeatedTest(10)
    void buildOffspringOf() {
        List<Specimen> parents = SpecimenBuilder.generateSpecies(2);

        Specimen offspring = Crossoverer.buildOffspringOf(parents);

        Connection notNullConnection=null;
        for (SpecimenComponent component : offspring.getComponents()) {
            if(component instanceof Connection){
                notNullConnection= (Connection) component;
            }
        }
        assertNotNull(notNullConnection);

    }
}
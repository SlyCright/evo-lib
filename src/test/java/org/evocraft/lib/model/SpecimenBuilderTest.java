package org.evocraft.lib.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.RepeatedTest;

class SpecimenBuilderTest {

    @RepeatedTest(10)
    public void getRandomPlaceNextTo() {
        SpecimenBuilder specimenBuilder = new SpecimenBuilder();

        GridPlace gridPlace = new GridPlace(0, 0);

        GridPlace result = specimenBuilder.getRandomPlaceNextTo(gridPlace);

        int sum = result.i + result.j;

        assertTrue(sum == 2 || sum == -2);
    }

    @RepeatedTest(10)
    void cellMapFilling() throws NoSuchFieldException, IllegalAccessException {
        SpecimenBuilder specimenBuilder = new SpecimenBuilder();

        Map<GridPlace, Cell> gridPlaceCellMap = specimenBuilder.cellsMapFilling();

        assertTrue(gridPlaceCellMap.size() > 1);
    }

}
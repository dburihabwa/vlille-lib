package com.burihabwa;

import com.burihabwa.vlille.StationStore;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by dorian on 9/16/14.
 */
public class TestStationStore extends TestCase {

    @Test/**
     * Tests if the getStation method throws an IllegalArgumentException when given a id lower than 0.
     */
    public void testNegativeStationId() {
        int negativeId = -1;
        StationStore stationStore = new StationStore();
        try {
            stationStore.getStation(negativeId);
            Assert.fail("Should not have reached this line!");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    /**
     * Tests if the getStation method throws an IllegalArgumentException when given a id equal to 0.
     */
    public void testZeroStationId() {
        int zeroStationId = 0;
        StationStore stationStore = new StationStore();
        try {
            stationStore.getStation(zeroStationId);
            Assert.fail("Should not have reached this line!");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

}

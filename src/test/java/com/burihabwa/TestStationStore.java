package com.burihabwa;

import com.burihabwa.vlille.Station;
import com.burihabwa.vlille.StationStore;
import junit.framework.Assert;
import junit.framework.TestSuite;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by dorian on 9/16/14.
 */
public class TestStationStore extends TestSuite {

    private String xmlSample = "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
            "<station>\n" +
            "  <adress>AVENUE LEON JOUHAUX (QUAI DE LA CITADELLE) </adress>\n" +
            "  <status>0</status>\n" +
            "  <bikes>4</bikes>\n" +
            "  <attachs>36</attachs>\n" +
            "  <paiement>AVEC_TPE</paiement>\n" +
            "  <lastupd>13 secondes</lastupd>\n" +
            "</station>";

    /**
     * Tests if the getStation method throws an IllegalArgumentException when given a id lower than 0.
     */
    @Test
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

    @Test
    /**
     * Tests of the parse method throws an IllegalArgumentException when given an null argument in place of a XML string
     */
    public void testParseNullData() throws IOException, SAXException, ParserConfigurationException {
        String data = null;
        StationStore stationStore = new StationStore();
        try {
            stationStore.parse(data);
            Assert.fail("Should not have reached this line!");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Tests if the parse method throws a SAXParseException when given an empty XML string as is expected
     */
    @Test
    public void testParseEmptyData() throws IOException, SAXException, ParserConfigurationException {
        String data = "";
        StationStore stationStore = new StationStore();
        try {
            stationStore.parse(data);
            Assert.fail("Should not have reached this line!");
        } catch (SAXParseException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testParseMissingAddress() throws IOException, SAXException, ParserConfigurationException {
        String data = "<?xml version=\"1.0\" encoding=\"utf-16\"?>" +
                "<station>" +
                "  <status>0</status>" +
                "  <bikes>7</bikes>" +
                "  <attachs>33</attachs>" +
                "  <paiement>AVEC_TPE</paiement>" +
                "  <lastupd>32 secondes</lastupd>" +
                "</station>";
        StationStore stationStore = new StationStore();
        Station station = stationStore.parse(data);
        Assert.assertNull(station.getAddress());
    }

    @Test
    public void testParseMissingBikes() throws IOException, SAXException, ParserConfigurationException {
        String data = "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
                "<station>\n" +
                "  <adress>AVENUE LEON JOUHAUX (QUAI DE LA CITADELLE) </adress>\n" +
                "  <status>0</status>\n" +
                "  <attachs>36</attachs>\n" +
                "  <paiement>AVEC_TPE</paiement>\n" +
                "  <lastupd>13 secondes</lastupd>\n" +
                "</station>";
        StationStore stationStore = new StationStore();
        Station station = stationStore.parse(data);
        Assert.assertEquals(station.getBikes(), 0);
    }

    @Test
    public void testParseMissingEmptySlots() throws IOException, SAXException, ParserConfigurationException {
        String data = "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
                "<station>\n" +
                "  <adress>AVENUE LEON JOUHAUX (QUAI DE LA CITADELLE) </adress>\n" +
                "  <status>0</status>\n" +
                "  <bikes>7</bikes>" +
                "  <paiement>AVEC_TPE</paiement>\n" +
                "  <lastupd>13 secondes</lastupd>\n" +
                "</station>";
        StationStore stationStore = new StationStore();
        Station station = stationStore.parse(data);
        Assert.assertEquals(station.getEmptySockets(), 0);
    }

    @Test
    public void testParseMissingLastUpd() throws IOException, SAXException, ParserConfigurationException {
        String data = "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
                "<station>\n" +
                "  <adress>AVENUE LEON JOUHAUX (QUAI DE LA CITADELLE) </adress>\n" +
                "  <status>0</status>\n" +
                "  <bikes>7</bikes>" +
                "  <paiement>AVEC_TPE</paiement>\n" +
                "</station>";
        StationStore stationStore = new StationStore();
        Station station = stationStore.parse(data);
        Assert.assertNull(station.getLastUpdate());
    }

}

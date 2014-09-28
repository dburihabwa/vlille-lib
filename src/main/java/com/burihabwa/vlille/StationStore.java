package com.burihabwa.vlille;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Station store that retrieves {@link com.burihabwa.vlille.Station} information from the vlille website.
 */
public class StationStore {
    private static final String ALL_STATIONS_URL = "http://vlille.fr/stations/xml-stations.aspx";
    private static final String STATION_URL = "http://vlille.fr/stations/xml-station.aspx?borne=";

    private Map<Integer, Station> stationsById = new TreeMap<Integer, Station>();

    public StationStore() {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        String data = "";
        try {
            url = new URL(ALL_STATIONS_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            is = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) > 0) {
                data += new String(buffer, 0, bytesRead);
            }
            setStations(data);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void setStations(String data) throws IOException, ParserConfigurationException, SAXException {
        if (data == null) {
            throw new IllegalArgumentException("data argument cannot be null!");
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(data.getBytes("UTF-16"));
        Document document = documentBuilder.parse(is);

        NodeList markerNodes = document.getDocumentElement().getElementsByTagName("marker");
        if (markerNodes != null && markerNodes.getLength() >= 1) {
            for (int i = 0; i < markerNodes.getLength(); i++) {
                Node marker = markerNodes.item(i);
                NamedNodeMap attributes = marker.getAttributes();

                int id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue(), 10);
                String name = attributes.getNamedItem("name").getNodeValue();
                double longitude = Double.parseDouble(attributes.getNamedItem("lng").getNodeValue());
                double latitude = Double.parseDouble(attributes.getNamedItem("lat").getNodeValue());

                Station station = new Station();
                station.setId(id);
                station.setName(name);
                station.setLongitude(longitude);
                station.setLatitude(latitude);
                stationsById.put(id, station);
            }
        }
        is.close();
    }

    public Collection<Integer> getStationIds() {
        return stationsById.keySet();
    }

    public Collection<Station> getStations() {
        return stationsById.values();
    }

    /**
     * Returns a station state.
     *
     * @param id The id of the station.
     * @return A station
     */
    public Station getStation(final int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id argument cannot be lower or equal to 0 (id = " + id + ")");
        }
        Station station = stationsById.get(id);
        if (station == null) {
            throw new IllegalArgumentException("id argument must be a valid station id");
        }
        URL url = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        String data = "";
        try {
            url = new URL(STATION_URL + id);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            is = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) > 0) {
                data += new String(buffer, 0, bytesRead);
            }
            station = parse(data, station);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return station;
        } catch (IOException e) {
            e.printStackTrace();
            return station;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return station;
        } catch (SAXException e) {
            e.printStackTrace();
            return station;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        stationsById.put(station.getId(), station);
        return station;
    }

    /**
     * Parses XML data and retrieves a {@link com.burihabwa.vlille.Station} state.
     *
     * @param data An XML document
     * @return a {@link com.burihabwa.vlille.Station} state
     * @throws ParserConfigurationException If XML parser is not set properly
     * @throws IOException                  If the data cannot be read properly
     * @throws SAXException                 If data is not a valid XML Document
     */
    public Station parse(final String data, Station station) throws ParserConfigurationException, IOException, SAXException {
        if (data == null) {
            throw new IllegalArgumentException("data argument cannot be null!");
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(data.getBytes("UTF-16"));
        Document document = documentBuilder.parse(is);

        NodeList addressNodes = document.getDocumentElement().getElementsByTagName("adress");
        String address = null;
        if (addressNodes != null && addressNodes.getLength() > 0) {
            address = addressNodes.item(0).getTextContent();
        }
        NodeList bikesList = document.getDocumentElement().getElementsByTagName("bikes");
        int bikes = 0;
        if (bikesList != null && bikesList.getLength() > 0) {
            bikes = Integer.parseInt(bikesList.item(0).getTextContent(), 10);
        }
        int attachs = 0;
        NodeList attachsList = document.getDocumentElement().getElementsByTagName("attachs");
        if (attachsList != null && attachsList.getLength() > 0) {
            attachs = Integer.parseInt(attachsList.item(0).getTextContent(), 10);
        }
        long lastupd = 0L;
        NodeList lastUpdateList = document.getDocumentElement().getElementsByTagName("lastupd");
        Calendar lastUpdate = null;
        if (lastUpdateList != null && lastUpdateList.getLength() > 0) {
            lastupd = Long.parseLong(document.getDocumentElement().getElementsByTagName("lastupd").item(0).getTextContent().split("\\s")[0], 10) * 1000;
            lastUpdate = new GregorianCalendar();
            lastUpdate.setTimeInMillis(lastUpdate.getTimeInMillis() - lastupd);
        }
        boolean creditCardTerminal = false;
        NodeList crediCardTerminalList = document.getDocumentElement().getElementsByTagName("paiement");
        if (crediCardTerminalList != null && crediCardTerminalList.getLength() > 0) {
            creditCardTerminal = true;
        }

        station.setAddress(address);
        station.setBikes(bikes);
        station.setFreeSockets(attachs);
        station.setLastUpdate(lastUpdate);
        station.setCreditCardTerminal(creditCardTerminal);


        is.close();
        return station;
    }
}

package com.burihabwa.vlille;

import org.w3c.dom.Document;
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
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by dorian on 9/15/14.
 */
public class StationStore {
    private static final String BASE_URL = "http://vlille.fr/stations/xml-station.aspx?borne=";

    public Station getStation(final int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id argument cannot be lower or equal to 0 (id = " + id + ")");
        }
        Station station = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            url = new URL(BASE_URL + id);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            is = connection.getInputStream();
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String data = new String(buffer);
            is.close();
            station = parse(data);
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
        station.setId(id);
        return station;
    }

    public Station parse(final String data) throws ParserConfigurationException, IOException, SAXException {
        if (data == null) {
            throw new IllegalArgumentException("data argument cannot be null!");
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_16));
        Document document = documentBuilder.parse(is);
        is.close();

        NodeList addressNodes = document.getDocumentElement().getElementsByTagName("adress");
        String address = null;
        if (addressNodes != null &&  addressNodes.getLength() > 0) {
            address = addressNodes.item(0).getTextContent();
        }
        NodeList bikesList = document.getDocumentElement().getElementsByTagName("bikes");
        int bikes = 0;
        if (bikesList != null &&  bikesList.getLength() > 0) {
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

        Station station = new Station();
        station.setAddress(address);
        station.setBikes(bikes);
        station.setEmptySockets(attachs);
        station.setLastUpdate(lastUpdate);

        return station;
    }
}

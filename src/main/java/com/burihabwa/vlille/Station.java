package com.burihabwa.vlille;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Vlille station that holds bikes and provides docking sockets for vlille users.
 */
public class Station implements Serializable {
    private long serialVersionUID = 1L;
    private int id = 0;
    private String name;
    private String address;
    private double longitude;
    private double latitude;
    private int bikes = 0;
    private int freeSockets = 0;
    private Calendar lastUpdate;
    private boolean creditCardTerminal;

    public Station() {
    }

    /**
     * Usual constructor with data all set.
     *
     * @param id                 the id of the station
     * @param address            the address of the station
     * @param bikes              the number of bikes in the station
     * @param freeSockets        the number of free sockets in the station
     * @param lastUpdate         the last time the station information was updated
     * @param creditCardTerminal whether the station has credit card terminal or not
     */
    public Station(int id, String name, String address, double longitude, double latitude, int bikes, int freeSockets, Calendar lastUpdate, boolean creditCardTerminal) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.bikes = bikes;
        this.freeSockets = freeSockets;
        this.lastUpdate = lastUpdate;
        this.creditCardTerminal = creditCardTerminal;
    }

    /**
     * Returns the id of the station
     *
     * @return id of the station
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the station
     *
     * @param id the new id of the station
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the address of the station
     *
     * @return address of the station
     */
    public String getAddress() {
        return address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Sets the new address of the station.
     *
     * @param address the new address of the station
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the number of bikes in the station.
     *
     * @return The number of bikes in the station
     */
    public int getBikes() {
        return bikes;
    }

    /**
     * Sets the number of bike in the station.
     *
     * @param bikes The new number of bikes in the station
     */
    public void setBikes(int bikes) {
        this.bikes = bikes;
    }

    /**
     * Returns the number of free sockets in the station.
     *
     * @return Number of free sockets in the station
     */
    public int getFreeSockets() {
        return freeSockets;
    }

    /**
     * Sets the number of free sockets in the station.
     *
     * @param freeSockets The new number of free sockets
     */
    public void setFreeSockets(int freeSockets) {
        this.freeSockets = freeSockets;
    }

    /**
     * Returns the last time the station information was updated.
     *
     * @return The last time the station information was updated
     */
    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the last time the station information was updated.
     *
     * @param lastUpdate the last time the station information was updated
     */
    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Returns true if the station has credit card terminal.
     *
     * @return true if the station has credit card terminal, false otherwise
     */
    public boolean hasCreditCardTerminal() {
        return creditCardTerminal;
    }

    /**
     * Sets the state of the credit card terminal.
     *
     * @param creditCardTerminal the new state of the credit card terminal
     */
    public void setCreditCardTerminal(boolean creditCardTerminal) {
        this.creditCardTerminal = creditCardTerminal;
    }

    @Override
    public String toString() {
        String str = "(";
        str += "id = " + id + ", ";
        if (name != null) {
            str += "name = " + name + ", ";
        }
        if (address != null) {
            str += "address = " + address + ", ";
        }
        str += "bikes = " + bikes + ", ";
        str += "freeSockets = " + freeSockets + ", ";
        str += "credit card terminal = " + creditCardTerminal;
        str += ")";
        return str;
    }
}

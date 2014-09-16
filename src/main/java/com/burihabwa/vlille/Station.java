package com.burihabwa.vlille;

import java.util.Calendar;

/**
 * Created by dorian on 9/15/14.
 */
public class Station {
    private int id = 0;
    private String address;
    private int bikes = 0;
    private int emptySockets = 0;
    private Calendar lastUpdate;
    private boolean creditCardTerminal;

    public Station() {
    }

    public Station(int id, String address, int bikes, int emptySockets, Calendar lastUpdate, boolean creditCardTerminal) {
        super();
        this.id = id;
        this.address = address;
        this.bikes = bikes;
        this.emptySockets = emptySockets;
        this.lastUpdate = lastUpdate;
        this.creditCardTerminal = creditCardTerminal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBikes() {
        return bikes;
    }

    public void setBikes(int bikes) {
        this.bikes = bikes;
    }

    public int getEmptySockets() {
        return emptySockets;
    }

    public void setEmptySockets(int emptySockets) {
        this.emptySockets = emptySockets;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean hasCreditCardTerminal() {
        return creditCardTerminal;
    }

    public void setCreditCardTerminal(boolean creditCardTerminal) {
        this.creditCardTerminal = creditCardTerminal;
    }
}

package com.ihoment.module;

/**
 * Created by xieyingwu on 2017/6/13.
 * address model from service
 */

public class Address {
    public int id;
    public String name;
    public String location;
    public String latlng;
    public String call;

    public Address() {
    }

    public Address(String call) {
        this.call = call;
    }

    public Address(int id, String name, String location, String latlng, String call) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.latlng = latlng;
        this.call = call;
    }

    public boolean copy(Address newAddress) {
        if (newAddress == null) return false;
        if (id != newAddress.id) return false;
        name = newAddress.name;
        location = newAddress.location;
        latlng = newAddress.latlng;
        call = newAddress.call;
        return true;
    }
}

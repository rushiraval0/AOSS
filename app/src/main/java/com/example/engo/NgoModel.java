package com.example.engo;

public class NgoModel {

    public String ngo_id , ngo_name , address , type;


    public NgoModel() {
    }

    public NgoModel(String ngo_id, String ngo_name, String address, String type) {
        this.ngo_id = ngo_id;
        this.ngo_name = ngo_name;
        this.address = address;
        this.type = type;
    }

    public String getNgo_id() {
        return ngo_id;
    }

    public void setNgo_id(String ngo_id) {
        this.ngo_id = ngo_id;
    }

    public String getNgo_name() {
        return ngo_name;
    }

    public void setNgo_name(String ngo_name) {
        this.ngo_name = ngo_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

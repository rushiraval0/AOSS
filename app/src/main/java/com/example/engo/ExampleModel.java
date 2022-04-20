package com.example.engo;

class ExampleModel
{

    String e_id,ngo_id,e_name,e_time,e_pic,e_startdate,e_enddate,e_sponser,ngo_name;


    public ExampleModel() {
    }



    public ExampleModel(String e_id, String ngo_id, String e_name, String e_time, String e_pic, String e_startdate, String e_enddate, String e_sponser , String ngo_name) {
        this.e_id = e_id;
        this.ngo_id = ngo_id;
        this.e_name = e_name;
        this.e_time = e_time;
        this.e_pic = e_pic;
        this.e_startdate = e_startdate;
        this.e_enddate = e_enddate;
        this.e_sponser = e_sponser;
        this.ngo_name = ngo_name;
    }

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getNgo_id() {
        return ngo_id;
    }

    public void setNgo_id(String ngo_id) {
        this.ngo_id = ngo_id;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getE_time() {
        return e_time;
    }

    public void setE_time(String e_time) {
        this.e_time = e_time;
    }

    public String getE_pic() {
        return e_pic;
    }

    public void setE_pic(String e_pic) {
        this.e_pic = e_pic;
    }

    public String getE_startdate() {
        return e_startdate;
    }

    public void setE_startdate(String e_startdate) {
        this.e_startdate = e_startdate;
    }

    public String getE_enddate() {
        return e_enddate;
    }

    public void setE_enddate(String e_enddate) {
        this.e_enddate = e_enddate;
    }

    public String getE_sponser() {
        return e_sponser;
    }

    public void setE_sponser(String e_sponser) {
        this.e_sponser = e_sponser;
    }
    public String getNgo_name() {
        return ngo_name;
    }

    public void setNgo_name(String ngo_name) {
        this.ngo_name = ngo_name;
    }
}


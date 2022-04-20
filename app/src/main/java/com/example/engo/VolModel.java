package com.example.engo;

public class VolModel {


    public String s_id,u_id,user_name,userphone,join_date,leave_date,description,staff_status,ngo_id;

    public VolModel() {
    }

    public VolModel(String s_id, String u_id, String user_name, String userphone, String join_date, String leave_date, String description, String staff_status, String ngo_id) {
        this.s_id = s_id;
        this.u_id = u_id;
        this.user_name = user_name;
        this.userphone = userphone;
        this.join_date = join_date;
        this.leave_date = leave_date;
        this.description = description;
        this.staff_status = staff_status;
        this.ngo_id = ngo_id;
    }


    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getLeave_date() {
        return leave_date;
    }

    public void setLeave_date(String leave_date) {
        this.leave_date = leave_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStaff_status() {
        return staff_status;
    }

    public void setStaff_status(String staff_status) {
        this.staff_status = staff_status;
    }

    public String getNgo_id() {
        return ngo_id;
    }

    public void setNgo_id(String ngo_id) {
        this.ngo_id = ngo_id;
    }
}





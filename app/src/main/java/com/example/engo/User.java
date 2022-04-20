package com.example.engo;

/**
 * Created by Belal on 9/5/2017.
 */


//this is very simple class and it only contains the user attributes, a constructor and the getters
// you can easily do this by right click -> generate -> constructor and getters
public class User {

    String u_id,u_email,u_password,u_status,u_role,u_name,profile_pic,phone_no,proof,flag;

    public User(String u_id, String u_email, String u_password, String u_status, String u_role, String u_name, String profile_pic, String phone_no, String proof, String flag) {
        this.u_id = u_id;
        this.u_email = u_email;
        this.u_password = u_password;
        this.u_status = u_status;
        this.u_role = u_role;
        this.u_name = u_name;
        this.profile_pic = profile_pic;
        this.phone_no = phone_no;
        this.proof = proof;
        this.flag = flag;
    }

    public User() {
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public String getU_status() {
        return u_status;
    }

    public void setU_status(String u_status) {
        this.u_status = u_status;
    }

    public String getU_role() {
        return u_role;
    }

    public void setU_role(String u_role) {
        this.u_role = u_role;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}

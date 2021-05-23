package com.uni.haifa.kgco_op;

import java.util.Date;

public class Parent {
    private int id;
    private String userName;
    private String email;
    private String password;
    private Date licenseDate;

    public Parent(int id, String userName, String email, String password, Date licenseDate){
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.licenseDate = licenseDate;
    }

    public Parent() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", licenseDate=" + licenseDate +
                '}';
    }
}

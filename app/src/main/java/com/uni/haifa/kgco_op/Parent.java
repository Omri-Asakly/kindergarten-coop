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
}

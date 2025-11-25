package com.example.fruit_store;

public class UserModel {

    public String fullName;
    public String email;
    public String status;
    public String registeredDate;

    public UserModel() {}

    public UserModel(String fullName, String email, String status, String registeredDate) {
        this.fullName = fullName;
        this.email = email;
        this.status = status;
        this.registeredDate = registeredDate;
    }
}

package com.example.fooddrink.Model;

public class User {
    private String username;
    private String password;
    private String address;
    private Integer userID;

    public Integer getUserID() {
        return userID;
    }

    public String getPhone() {
        return phone;
    }

    private String phone;

    public User() {
    }

    public User(String name, String password, String address) {
        this.username = name;
        this.password = password;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

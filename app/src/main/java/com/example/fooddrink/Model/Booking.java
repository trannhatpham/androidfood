package com.example.fooddrink.Model;

public class Booking {

    public String Datetime;
    public String MenuId;
    public String address;
    public String employee;
    public String userId;
    public Integer isFinish;
    public String numberPhone;
    public String listCart;
    public Integer price;
    public Integer stageId;

    public Booking(String datetime, String menuId, String address, String employee, Integer isFinish, String numberPhone, Integer price, String userName) {
        this.Datetime = datetime;
        this.MenuId = menuId;
        this.address = address;
        this.employee = employee;
        this.isFinish = isFinish;
        this.numberPhone = numberPhone;
        this.price = price;
        this.userName = userName;
    }

    public Booking(){}

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String userName;

}

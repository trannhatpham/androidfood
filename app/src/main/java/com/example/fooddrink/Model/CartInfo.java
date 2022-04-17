package com.example.fooddrink.Model;

public class CartInfo {
    public Integer cartID;
    public Integer foodID;

    public Integer getCartID() {
        return cartID;
    }

    public Integer getFoodID() {
        return foodID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer categoryID;

    public void setCartID(Integer cartID) {
        this.cartID = cartID;
    }

    public void setFoodID(Integer foodID) {
        this.foodID = foodID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer count;
    public Integer price;
    public Integer userID;
}

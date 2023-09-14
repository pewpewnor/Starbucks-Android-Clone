package com.example.starbucks2.models;

import java.util.ArrayList;

public class Order {
    private int id;
    private String products;
    private int totalPrice;

    public Order(int id, String products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.id = id;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}

package com.example.fruit_store;

import java.io.Serializable;

public class Fruit  implements Serializable {
    private String id;
    private String name;
    private double price;
    private int stock;
    private String imageURL;

    private int selectedQuantity = 1;
    public Fruit() {

    }

    public Fruit(String id, String name, double price, int stock, String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public int getSelectedQuantity() {
        return selectedQuantity;
    }
    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;

    }

}

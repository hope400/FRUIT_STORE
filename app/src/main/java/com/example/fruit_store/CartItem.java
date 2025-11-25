package com.example.fruit_store;

public class CartItem {

    String id;
    String name;
    double price;
    String imageURL;
    int quantity;

    public CartItem() {}

    public CartItem(String id, String name, double price, String imageURL, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.quantity = quantity;
    }


    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getImageURL() { return imageURL; }
    public int getQuantity() { return quantity; }
}

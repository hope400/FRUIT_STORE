package com.example.fruit_store;

import java.io.Serializable;

public class Order implements Serializable {

    String orderId;
    String userId;
    String paymentMethod;
    String date;
    double total;

    String fruitId;
    String fruitName;
    double price;
    String imageURL;


    public Order() {}


    public Order(String orderId, String userId, String paymentMethod, String date,
                 double total, String fruitId, String fruitName, double price, String imageURL) {

        this.orderId = orderId;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.total = total;

        this.fruitId = fruitId;
        this.fruitName = fruitName;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getDate() { return date; }
    public double getTotal() { return total; }

    public String getFruitId() { return fruitId; }
    public String getFruitName() { return fruitName; }
    public double getPrice() { return price; }
    public String getImageURL() { return imageURL; }

    public double getFruitPrice() { return price; }
}

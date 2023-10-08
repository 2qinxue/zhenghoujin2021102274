package com.jnu.myrecycle.data;

public class Book {

    public final String name;
    public final double price;
    public final int imageId;
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImageId() {
        return imageId;
    }

    public Book(String name, double price, int imageId) {
        this.name = name;
        this.price = price;
        this.imageId = imageId;
    }
}

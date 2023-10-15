package com.jnu.myrecycle.data;

public class Book {

    public String name;
    public double price;
    public int imageId;
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
    public void setName(String newName) {
        this.name = newName;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }
}

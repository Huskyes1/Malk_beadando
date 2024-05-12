package com.example.webshop;

public class ShopItems {
    private String name;
    private int price;
    private int count;
    private String imgSRC;

    public ShopItems(String name, int price, int count, String imgSRC) {
        name = name;
        this.price = price;
        count = count;
        this.imgSRC = imgSRC;
    }

    public ShopItems() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImgSRC() {
        return imgSRC;
    }

    public void setImgSRC(String imgSRC) {
        this.imgSRC = imgSRC;
    }
}

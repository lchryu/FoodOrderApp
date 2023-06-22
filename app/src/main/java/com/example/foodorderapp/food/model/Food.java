package com.example.foodorderapp.food.model;


public class Food {
    public Food() {
    }

    private int id;
    private String name;
    private int price;
    private int imgResource;

    public Food(int id, String name, int price, int imgResource) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgResource = imgResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }
}

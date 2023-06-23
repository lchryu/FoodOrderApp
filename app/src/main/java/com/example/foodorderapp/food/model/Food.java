package com.example.foodorderapp.food.model;


import androidx.annotation.Nullable;

import java.io.Serializable;

public class Food implements Serializable {
    public Food() {
    }

    private int id;
    private String name;
    private int price;
    private int imgResource;
    private String desc;

    public Food(int id, String name, int price, int imgResource) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgResource = imgResource;
    }

    public Food(int id, String name, int price, int imgResource, String desc) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgResource = imgResource;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Food other = (Food) obj;
        return id == other.id && name.equals(other.name) && price == other.price
                && imgResource == other.imgResource && desc.equals(other.desc);
    }
}

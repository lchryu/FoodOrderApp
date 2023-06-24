package com.example.foodorderapp.cart.model;

import com.example.foodorderapp.food.model.Food;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Food food;
    private int quantity;

    public CartItem(Food food, int quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public CartItem() {
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getTotalPrice() {
        return food.getPrice() * quantity;
    }
}

package com.example.foodorderapp.cart.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class Cart {
    public List<CartItem> listCartItem;
    public Timestamp timestamp;

    public Cart() {
    }

    public List<CartItem> getListCartItem() {
        return listCartItem;
    }

    public void setListCartItem(List<CartItem> listCartItem) {
        this.listCartItem = listCartItem;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

package com.example.foodorderapp.food.model;

import com.example.foodorderapp.cart.model.CartDAO;

import java.util.ArrayList;
import java.util.List;

public class FoodRepository {
    private static List<Food> foodList = new ArrayList<>();

    public FoodRepository(List<Food> lst) {
        for (Food f : lst) {
            foodList.add(f);
        }
    }

    public FoodRepository() {
    }

    public static List<Food> getFoodList() {
        return foodList;
    }

    public static void setFoodList(List<Food> foodList) {
        FoodRepository.foodList = foodList;
    }

    public static boolean addFood(Food f) {
        if (!foodList.contains(f)) {
            foodList.add(f);
            CartDAO.updateCartItems(f);
            System.out.println("đã thêm vào giỏ hàng");
            return true;
        }
        else {
            System.out.println("Đã tồn tại");
            return false;
        }
    }
    public static void removeFood(Food f) {
        foodList.remove(f);
//        CartDAO.removeCartItem(f);
    }


    public static Food getFood(Integer id) {
        for (Food f : foodList) {
            if (id == f.getId()) {
                return f;
            }
        }
        return null;
    }
}

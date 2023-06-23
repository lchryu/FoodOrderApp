package com.example.foodorderapp.cart.model;

import com.example.foodorderapp.food.model.Food;
import com.example.foodorderapp.food.model.FoodRepository;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public static List<CartItem> listCartItem;

    static {
        listCartItem = new ArrayList<>();
//        updateCartItems();
    }

    /**
     * Khi Food.Repository.addFood(f) --> tự động gọi tới updateCartItems để update listCartItem
     * listCartItem --> đổ vào --> CartAdapter tại CartActivity
     * Nếu món ăn chưa tồn tại trong giỏ hàng, sẽ tạo một CartItem mới và thêm vào giỏ hàng.
     * Nếu món ăn đã tồn tại, sẽ tăng quantity lên 1.
     */
    public static void updateCartItems(Food food) {
        List<Food> foodList = FoodRepository.getFoodList();

        boolean exists = false;

        // Kiểm tra xem food đã tồn tại trong listCartItem chưa
        for (CartItem cartItem : listCartItem) {
            if (cartItem.getFood() == food) {
                exists = true;
                // Nếu tồn tại và quantity chưa đạt giới hạn 10 --> tăng quantity lên 1
                if (cartItem.getQuantity() < 10) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                }
                return;
            }
        }

        // Nếu food chưa tồn tại, tạo CartItem mới và thêm vào listCartItem
        if (!exists) {
            CartItem cartItem = new CartItem(food, 1);
            listCartItem.add(cartItem);
        }
    }

    public static void addToCart(CartItem cartItem) {
        for (CartItem item : listCartItem) {
            if (item.getFood().equals(cartItem.getFood())) {
                // Món ăn đã tồn tại trong giỏ hàng và quantity chưa đạt giới hạn 10 --> tăng quantity lên 1
                if (item.getQuantity() < 10) {
                    item.setQuantity(item.getQuantity() + 1);
                }
                return;
            }
        }
        // Món ăn chưa có trong giỏ hàng, thêm mới với quantity là 1
        listCartItem.add(cartItem);
    }

    public static void removeFromCart(CartItem cartItem) {
        for (CartItem item : listCartItem) {
            if (item.getFood().equals(cartItem.getFood())) {
                // Tìm thấy món ăn trong giỏ hàng, kiểm tra nếu quantity <= 1 thì xóa khỏi giỏ hàng, ngược lại giảm quantity xuống 1
                if (item.getQuantity() <= 1) {
                    listCartItem.remove(item);
                    FoodRepository.removeFood(cartItem.getFood()); // đồng thời xoá item khỏi FoodRepository do quantity < 0
                } else {
                    item.setQuantity(item.getQuantity() - 1);
                }
                return;
            }
        }
    }

    public static void clearCart() {
        listCartItem.clear();
    }

    public static List<CartItem> getCartItems() {
        return listCartItem;
    }


    /**
     * Tính tổng giá trị đơn hàng (tổng giá của tất cả các món hàng trong giỏ hàng).
     *
     * @return Tổng giá trị đơn hàng
     */
    public static int totalPrice() {
        int total = 0;

        for (CartItem cartItem : listCartItem) {
            total += cartItem.getTotalPrice();
        }

        return total;
    }
    /**
     * Trả về tổng giá trị đơn hàng dưới dạng chuỗi định dạng tiền tệ.
     *
     * @return Tổng giá trị đơn hàng dưới dạng chuỗi tiền tệ
     */
    public static String getTotalPrice() {
        int total = totalPrice();
        String formattedPrice = formatPrice(total);
        return formattedPrice;
    }

    public static String formatPrice(int price) {
        String formattedPrice = String.valueOf(price);

        // Chèn dấu chấm phân cách hàng nghìn vào chuỗi giá trị
        int length = formattedPrice.length();
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (int i = length - 1; i >= 0; i--) {
            builder.append(formattedPrice.charAt(i));
            count++;
            if (count % 3 == 0 && i > 0) {
                builder.append(".");
            }
        }

        // Đảo ngược chuỗi để đưa về đúng thứ tự
        String reversedPrice = builder.reverse().toString();

        // Trả về chuỗi kết quả với định dạng tiền tệ
        return reversedPrice + " VNĐ";
    }
    public static int getTax(){
        return (int) (totalPrice() * 0.1);
    }

    public static int finalPrice() {
        return totalPrice() + getTax();
    }
}

package com.example.foodorderapp.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderapp.R;
import com.example.foodorderapp.Utility;
import com.example.foodorderapp.cart.adapter.CartAdapter;
import com.example.foodorderapp.cart.model.Cart;
import com.example.foodorderapp.cart.model.CartDAO;
import com.example.foodorderapp.cart.model.CartItem;
import com.example.foodorderapp.food.model.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rcvCart;
    private CartAdapter cartAdapter;
    private TextView tvCartTotalPrice;
    private TextView tvTax;
    private Button btnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rcvCart = findViewById(R.id.rcvCart);
        tvCartTotalPrice = findViewById(R.id.tvCartTotalPrice);
        tvTax = findViewById(R.id.tvTax);
        btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this, "Bạn đã thanh toán " + CartDAO.formatPrice(CartDAO.finalPrice()), Toast.LENGTH_SHORT).show();
                SaveOrder();
            }
        });

        rcvCart.setLayoutManager(new LinearLayoutManager(this));




        // adapter
        cartAdapter = new CartAdapter(this);
        cartAdapter.setData(CartDAO.getCartItems());

        rcvCart.setAdapter(cartAdapter);

        tvCartTotalPrice.setText(CartDAO.getTotalPrice());
        tvTax.setText(CartDAO.formatPrice(CartDAO.getTax()));
    }

    private void SaveOrder() {
        Cart cart = new Cart();
        cart.setListCartItem(CartDAO.getCartItems());
        cart.setTimestamp(Timestamp.now());

        saveOrderToFirebase(cart);
    }

    private void saveOrderToFirebase(Cart cart) {
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForCarts().document();
        documentReference.set(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utility.ShowToast(CartActivity.this, "Dữ liệu đã được lưu vào firebase");
                    CartDAO.clearCart();
                    finish();
                } else {
                    Utility.ShowToast(CartActivity.this, "saveOrderToFirebase(Cart cart) đang không hoạt động");
                }
            }
        });
    }

    private List<CartItem> convertFoodListToCartItems(List<Food> foodList) {
        // Convert Food list to CartItem list
        // You need to implement this logic based on your requirements
        // Here's a sample implementation that assumes quantity of each item is 1
        // Modify this implementation as per your actual logic

        List<CartItem> cartItems = new ArrayList<>();
        for (Food food : foodList) {
            CartItem cartItem = new CartItem(food, 1);
            cartItems.add(cartItem);
        }
        return cartItems;
    }
    private List<CartItem> convertFoodListToCartItems(Map<Food, Integer> cartList) {
        // Convert CartDAO cartList to List<CartItem>
        List<CartItem> cartItems = new ArrayList<>();
        for (Map.Entry<Food, Integer> entry : cartList.entrySet()) {
            Food food = entry.getKey();
            int quantity = entry.getValue();
            CartItem cartItem = new CartItem(food, quantity);
            cartItems.add(cartItem);
        }
        return cartItems;
    }
    public void updateCartTotalPrice() {
        tvCartTotalPrice.setText(String.valueOf(CartDAO.getTotalPrice()));
        tvTax.setText(CartDAO.formatPrice(CartDAO.getTax()));
    }
}
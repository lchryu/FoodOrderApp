package com.example.foodorderapp.history_purchasing.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderapp.R;
import com.example.foodorderapp.cart.model.Cart;
import com.example.foodorderapp.cart.model.CartDAO;
import com.example.foodorderapp.cart.model.CartItem;
import com.example.foodorderapp.food.model.Food;
import com.example.foodorderapp.history_purchasing.adapter.HistoryPurchasingDetailAdapter;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryPurchasingDetailActivity extends AppCompatActivity {
    ArrayList<CartItem> cartItems;
    RecyclerView rcvHistoryPurchasingDetail;
    HistoryPurchasingDetailAdapter historyPurchasingDetailAdapter;
    TextView tvTotalBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_purchasing_detail);
        rcvHistoryPurchasingDetail = findViewById(R.id.rcvHistoryPurchasingDetail);
        historyPurchasingDetailAdapter = new HistoryPurchasingDetailAdapter(HistoryPurchasingDetailActivity.this);
        // nhận dữ liệu
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable serializable = bundle.getSerializable("cartItems");
            if (serializable instanceof ArrayList) {
                cartItems = (ArrayList<CartItem>) serializable;
            }
            Toast.makeText(this, "" + cartItems.size(), Toast.LENGTH_SHORT).show();
        }
        rcvHistoryPurchasingDetail.setLayoutManager(new LinearLayoutManager(this));
        historyPurchasingDetailAdapter.setData(cartItems);
        rcvHistoryPurchasingDetail.setAdapter(historyPurchasingDetailAdapter);
//        debug: in ra số item được gửi qua detail activity
//        for (CartItem cartItem: cartItems) {
//            System.out.println("from HistoryPurchasing = " + cartItem.getFood().getName());
//        }

        tvTotalBill = findViewById(R.id.tvTotalBill);
        CalculateTotalBill();


    }

    private void CalculateTotalBill() {
        int sum = 0;
        for (CartItem cartItem : cartItems) {
            sum += cartItem.getQuantity() * cartItem.getFood().getPrice();
        }
        sum += sum * 0.1;

        int roundedSum = (int) sum; // Ép kiểu sum thành int

        tvTotalBill.setText(CartDAO.formatPrice(roundedSum));

    }
}
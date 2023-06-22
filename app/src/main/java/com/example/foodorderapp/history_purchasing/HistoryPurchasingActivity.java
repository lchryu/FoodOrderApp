package com.example.foodorderapp.history_purchasing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.foodorderapp.R;
import com.example.foodorderapp.Utility;
import com.example.foodorderapp.cart.CartActivity;
import com.example.foodorderapp.cart.model.Cart;
import com.example.foodorderapp.history_purchasing.adapter.HistoryPurchasingAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HistoryPurchasingActivity extends AppCompatActivity {
    RecyclerView rcvHistoryPurchasing;
    HistoryPurchasingAdapter historyPurchasingAdapter;
    List<Cart> listCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_purchasing);


        // code  new
        listCart = new ArrayList<>();
        rcvHistoryPurchasing = findViewById(R.id.rcvHistoryPurchasing);

        historyPurchasingAdapter = new HistoryPurchasingAdapter(this);


        getListCart();

        System.out.println("List Cart size = " + listCart.size());

        rcvHistoryPurchasing.setLayoutManager(new LinearLayoutManager(HistoryPurchasingActivity.this));
        rcvHistoryPurchasing.setAdapter(historyPurchasingAdapter);
    }

    int dem = 1;

    private void getListCart() {
        CollectionReference collectionReference = Utility.getCollectionReferenceForCarts();
        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Load thành công lịch sử mua hàng :D", Toast.LENGTH_SHORT).show();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Cart cart = document.toObject(Cart.class);
                    listCart.add(cart);
                    System.out.println("cart[" + listCart.size() + "].size() = " + cart.listCartItem.size());
                }
                // sắp xếp lịch sử mua hàng theo thời gian
                Collections.sort(listCart, new Comparator<Cart>() {
                    @Override
                    public int compare(Cart cart1, Cart cart2) {
                        return cart2.getTimestamp().compareTo(cart1.getTimestamp());
                    }
                });

                // set data và notify cho adapter
                historyPurchasingAdapter.setData(listCart);
            } else {
                Utility.ShowToast(HistoryPurchasingActivity.this, "Lỗi khi lấy dữ liệu từ Firebase");
            }
        });
    }
}
//public class HistoryPurchasingActivity extends AppCompatActivity {
//    RecyclerView rcvHistoryPurchasing;
//    HistoryPurchasingAdapter historyPurchasingAdapter;
//    List<Cart> listCart;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_history_purchasing);
//
//        listCart = new ArrayList<>();
//        rcvHistoryPurchasing = findViewById(R.id.rcvHistoryPurchasing);
//
//        historyPurchasingAdapter = new HistoryPurchasingAdapter(this);
//
//        // Gọi getListCart và đợi cho nó hoàn thành
//        getListCart(() -> {
//            historyPurchasingAdapter.setData(listCart);
//            System.out.println("List Cart size = " + listCart.size());
//
//            rcvHistoryPurchasing.setLayoutManager(new LinearLayoutManager(HistoryPurchasingActivity.this));
//            rcvHistoryPurchasing.setAdapter(historyPurchasingAdapter);
//        });
//    }
//
//    int dem = 1;
//
//    private void getListCart(Runnable callback) {
//        CollectionReference collectionReference = Utility.getCollectionReferenceForCarts();
//        collectionReference.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    Cart cart = document.toObject(Cart.class);
//                    listCart.add(cart);
//                    System.out.println("cart[" + listCart.size() + "].size() = " + cart.listCartItem.size());
//                }
//                // Xử lý danh sách cartList theo nhu cầu của bạn
//
//                // Gọi callback khi getListCart hoàn thành
//                if (callback != null) {
//                    callback.run();
//                }
//            } else {
//                Utility.ShowToast(HistoryPurchasingActivity.this, "Lỗi khi lấy dữ liệu từ Firebase");
//            }
//        });
//    }
//}

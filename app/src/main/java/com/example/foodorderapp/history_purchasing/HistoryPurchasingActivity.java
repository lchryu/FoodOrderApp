package com.example.foodorderapp.history_purchasing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.foodorderapp.account.LoginActivity;
import com.example.foodorderapp.R;
import com.example.foodorderapp.utility.Utility;
import com.example.foodorderapp.cart.model.Cart;
import com.example.foodorderapp.history_purchasing.adapter.HistoryPurchasingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryPurchasingActivity extends AppCompatActivity {
    RecyclerView rcvHistoryPurchasing;
    HistoryPurchasingAdapter historyPurchasingAdapter;
    List<Cart> listCart;
    ImageView menuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_purchasing);

        listCart = new ArrayList<>();
        rcvHistoryPurchasing = findViewById(R.id.rcvHistoryPurchasing);
        historyPurchasingAdapter = new HistoryPurchasingAdapter(this);

        menuBtn = findViewById(R.id.menu_btn);
        menuBtn.setOnClickListener(v->showMenu());

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
    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(HistoryPurchasingActivity.this, menuBtn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle()=="Logout") {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(HistoryPurchasingActivity.this, LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
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

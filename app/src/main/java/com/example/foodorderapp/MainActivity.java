package com.example.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.foodorderapp.account.LoginActivity;
import com.example.foodorderapp.cart.CartActivity;
import com.example.foodorderapp.food.adapter.FoodAdapter;
import com.example.foodorderapp.food.model.Food;
import com.example.foodorderapp.food.model.FoodRepository;
import com.example.foodorderapp.history_purchasing.activity.HistoryPurchasingActivity;
import com.example.foodorderapp.utility.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvFood;
    private FoodAdapter foodAdapter;
    private FloatingActionButton btnFloating;
    ImageButton menuBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reference variable
        rcvFood = findViewById(R.id.rcvFood);
        foodAdapter = new FoodAdapter(this);
        btnFloating = findViewById(R.id.floatingBtn);
        menuBtn = findViewById(R.id.menu_btn);
        menuBtn.setOnClickListener((v)->showMenu() );


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvFood.setLayoutManager(gridLayoutManager);

        foodAdapter.setData(getListFood());
        rcvFood.setAdapter(foodAdapter);
        rcvFood.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    btnFloating.hide();
                }
                else {
                    btnFloating.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FoodRepository.getFoodList().size() == 0) {
                    Utility.ShowToast(MainActivity.this, "Bạn chưa thêm bất cứ sản phẩm nào vào giỏ hàng!");
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, menuBtn);
        popupMenu.getMenu().add("Logout");
        popupMenu.getMenu().add("History Purchasing");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle()=="Logout") {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    return true;
                }
                if(menuItem.getTitle()=="History Purchasing") {
                    startActivity(new Intent(MainActivity.this, HistoryPurchasingActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    private List<Food> getListFood() {
        List<Food>lstFood = new ArrayList<>();
//        lstFood.add(new Food(1,  "Burger", 50000, R.drawable.burger));
//        lstFood.add(new Food(2,  "Ice cream", 20000, R.drawable.ice_cream));
//        lstFood.add(new Food(3,  "Pasta", 75000, R.drawable.pasta));
//        lstFood.add(new Food(4,  "Pizza", 80000, R.drawable.pizza));
//        lstFood.add(new Food(5,  "Sushi", 100000, R.drawable.sushi));
//        lstFood.add(new Food(6,  "Co ca cola", 20000, R.drawable.coca));
        lstFood.add(new Food(1, "Burger", 50000, R.drawable.burger, "Bánh mì kẹp thịt bò ngon miệng được phủ lên các loại rau tươi và pho mát tan chảy, được đựng trong 1 chiếc bánh mì mềm."));
        lstFood.add(new Food(2, "Kem", 20000, R.drawable.ice_cream, "Kem mịn và đậm đà với nhiều hương vị và topping khác nhau."));
        lstFood.add(new Food(3, "Pasta", 75000, R.drawable.pasta, "Mì ý chín vừa, được nấu cùng một số sốt đậm đà, các loại gia vị và topping tuỳ chọn."));
        lstFood.add(new Food(4, "Pizza", 80000, R.drawable.pizza, "Pizza Ý truyền thống với lớp vỏ giòn tan, phủ đầy pho mát, sốt cà chua và nhiều loại topping khác nhau."));
        lstFood.add(new Food(5, "Sushi", 100000, R.drawable.sushi, "Những cuộn sushi nhỏ gọn và tinh tế, gồm cơm trộn giấm kèm theo hải sản, rau sống hoặc các thành phần khác."));
        lstFood.add(new Food(6, "Coca-Cola", 20000, R.drawable.coca, "Đồ uống có gas thơm ngon với hỗn hợp độc đáo của nhiều hương vị, rất thích hợp để giải khát."));


        return lstFood;
    }

}
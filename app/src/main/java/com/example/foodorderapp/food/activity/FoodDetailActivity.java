package com.example.foodorderapp.food.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderapp.R;
import com.example.foodorderapp.cart.model.CartDAO;
import com.example.foodorderapp.food.model.Food;
import com.example.foodorderapp.food.model.FoodRepository;
import com.example.foodorderapp.utility.Utility;

public class FoodDetailActivity extends AppCompatActivity {
    Food food;
    ImageView foodImage;
    TextView foodName, foodPrice, foodDesc;
    Button btnAddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        // nhận dữ liệu được put từ FoodAdapter
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            food = (Food) bundle.getSerializable("food");
        }

        // reference variable
        foodImage = findViewById(R.id.food_image);
        foodName = findViewById(R.id.food_name);
        foodDesc = findViewById(R.id.food_description);
        foodPrice = findViewById(R.id.food_price);
        btnAddToCart = findViewById(R.id.add_to_cart_button);

        foodImage.setImageResource(food.getImgResource());
        foodName.setText(food.getName());
        foodDesc.setText(food.getDesc());
        foodPrice.setText(CartDAO.formatPrice(food.getPrice()));

        btnAddToCart.setOnClickListener(v->addToCartFromDetailFood());

    }

    private void addToCartFromDetailFood() {
        boolean checkAdd = FoodRepository.addFood(food);
        if (checkAdd) {
            Utility.ShowToast(FoodDetailActivity.this, "Thêm vào giỏ hàng thành công");
        } else {
            Utility.ShowToast(FoodDetailActivity.this, "Đã tồn tại trong giỏ hàng");
        }

        // in ra console phục vụ debug
        for (int i = 0; i < FoodRepository.getFoodList().size(); i++) {
            System.out.println("FoodRepository.getFoodList[" + i + "] = " + FoodRepository.getFoodList().get(i).getName());
        }
        System.out.println("--------------------------------------------------");
//        Toast.makeText(FoodDetailActivity.this, "cart.size = " + FoodRepository.getFoodList().size(), Toast.LENGTH_SHORT).show();
        Utility.ShowToast(FoodDetailActivity.this, "Số món ăn trong giỏ hàng: " + FoodRepository.getFoodList().size());
    }
}
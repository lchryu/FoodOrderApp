package com.example.foodorderapp.food.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.cart.model.CartDAO;
import com.example.foodorderapp.food.activity.FoodDetailActivity;
import com.example.foodorderapp.utility.Utility;
import com.example.foodorderapp.food.model.Food;
import com.example.foodorderapp.food.model.FoodRepository;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private Context mContext;
    private List<Food> mListFood;

    public FoodAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Food> list) {
        this.mListFood = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = mListFood.get(position);
        if (food == null) {
            return;
        }
        holder.imgFood.setImageResource(food.getImgResource());
        holder.tvName.setText(food.getName());
        holder.tvPrice.setText(CartDAO.formatPrice(food.getPrice()));
        holder.imvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonClick(view, food);
            }
        });
        holder.layout_item_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FoodDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", food);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    private void addButtonClick(View view, Food food) {
        boolean checkAdd = FoodRepository.addFood(food);
        if (checkAdd) {
            Utility.ShowToast(mContext, "Thêm vào giỏ hàng thành công");
        } else {
            Utility.ShowToast(mContext, "Đã tồn tại trong giỏ hàng");
        }

        // in ra console phục vụ debug
        for (int i = 0; i < FoodRepository.getFoodList().size(); i++) {
            System.out.println("FoodRepository.getFoodList[" + i + "] = " + FoodRepository.getFoodList().get(i).getName());
        }
        System.out.println("--------------------------------------------------");
//        Toast.makeText(mContext, "cart.size = " + FoodRepository.getFoodList().size(), Toast.LENGTH_SHORT).show();
        Utility.ShowToast(mContext, "Số món ăn trong giỏ hàng: " + FoodRepository.getFoodList().size());
    }

    @Override
    public int getItemCount() {
        if (mListFood != null) {
            return mListFood.size();
        }
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFood;
        private TextView tvName;
        private TextView tvPrice;
        private ImageView imvAdd;
        private CardView layout_item_food;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imvAdd = itemView.findViewById(R.id.imvAdd);
            layout_item_food = itemView.findViewById(R.id.layout_item_food);
        }
    }
}

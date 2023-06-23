package com.example.foodorderapp.cart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.cart.CartActivity;
import com.example.foodorderapp.cart.model.CartDAO;
import com.example.foodorderapp.cart.model.CartItem;
import com.example.foodorderapp.food.model.FoodRepository;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context mContext;
    private List<CartItem> mCartItems;

    public CartAdapter(Context context) {
        mContext = context;
    }
    public void setData(List<CartItem>lst) {
        this.mCartItems = lst;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = mCartItems.get(position);
        if (cartItem == null) {
            return;
        }
        holder.imgFood.setImageResource(cartItem.getFood().getImgResource());
        holder.tvName.setText(cartItem.getFood().getName());
        holder.tvPrice.setText(CartDAO.formatPrice(cartItem.getFood().getPrice()));
        holder.tvQuantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.tvTotalPrice.setText(CartDAO.formatPrice(cartItem.getTotalPrice()));

        holder.tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartDAO.addToCart(cartItem);
                holder.tvQuantity.setText(String.valueOf(cartItem.getQuantity()));
                holder.tvTotalPrice.setText(String.valueOf(cartItem.getTotalPrice()));
                ((CartActivity)mContext).updateCartTotalPrice();
            }
        });

        holder.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartDAO.removeFromCart(cartItem);
                holder.tvQuantity.setText(String.valueOf(cartItem.getQuantity()));
                holder.tvTotalPrice.setText(String.valueOf(cartItem.getTotalPrice()));
                /*
                 * khi thêm thì các food trong giỏ hàng không thay đổi
                 * nhưng khi xoá nếu qantity giảm về 0 sẽ bị xoá ra khỏi giỏ hàng
                 * (mCartItems thay đổi)--> phải gọi notify*/
                notifyDataSetChanged();
                ((CartActivity)mContext).updateCartTotalPrice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFood;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvQuantity;
        private TextView tvTotalPrice;
        private TextView tvPlus;
        private TextView tvMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvPlus = itemView.findViewById(R.id.tvPlus);
            tvMinus = itemView.findViewById(R.id.tvMinus);
        }
    }
}
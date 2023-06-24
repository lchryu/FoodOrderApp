package com.example.foodorderapp.history_purchasing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.cart.model.CartDAO;
import com.example.foodorderapp.cart.model.CartItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryPurchasingDetailAdapter extends RecyclerView.Adapter<HistoryPurchasingDetailAdapter.HistoryPurchasingDetailViewHolder> {
    private Context mContext;
    private List<CartItem> mCartItems;

    public HistoryPurchasingDetailAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<CartItem>lst) {
        this.mCartItems = lst;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryPurchasingDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_purchasing_detail, parent, false);
        return new HistoryPurchasingDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryPurchasingDetailViewHolder holder, int position) {
        CartItem cartItem = mCartItems.get(position);
//        if (cartItem != null) {
//            return;
//        }
        holder.imgFood.setImageResource(cartItem.getFood().getImgResource());
        holder.tvName.setText(cartItem.getFood().getName());
        holder.tvPrice.setText(CartDAO.formatPrice(cartItem.getFood().getPrice()));
        holder.tvQuantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.tvTotalPrice.setText(CartDAO.formatPrice(cartItem.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        if (mCartItems != null) {
            return mCartItems.size();
        }
        return 0;
    }

    public class HistoryPurchasingDetailViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgFood;
        TextView tvName, tvPrice, tvQuantity, tvTotalPrice;

        public HistoryPurchasingDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}

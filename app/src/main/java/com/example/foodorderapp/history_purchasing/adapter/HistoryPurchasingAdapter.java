package com.example.foodorderapp.history_purchasing.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.cart.model.CartDAO;
import com.example.foodorderapp.history_purchasing.activity.HistoryPurchasingDetailActivity;
import com.example.foodorderapp.utility.Utility;
import com.example.foodorderapp.cart.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class HistoryPurchasingAdapter extends RecyclerView.Adapter<HistoryPurchasingAdapter.HistoryPurchasingViewHolder>{
    private Context mContext;
    private List<Cart>listCart;

    public HistoryPurchasingAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<Cart>lst) {
        this.listCart = lst;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryPurchasingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_purchasing, parent, false);
        return new HistoryPurchasingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryPurchasingViewHolder holder, int position) {
        Cart cart = listCart.get(position);
        if (cart == null) {
            return;
        }
//        holder.tvTotalPrice.setText(String.valueOf(getTotalCart(cart)));
        holder.tvTotalPrice.setText(CartDAO.formatPrice(getTotalCart(cart)));
        holder.tvItemCount.setText(String.valueOf(cart.listCartItem.size()));
        holder.tvTimestamp.setText(Utility.timestampToString(cart.timestamp));
        holder.cvHistoryPurchasing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HistoryPurchasingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cartItems", new ArrayList<>(cart.getListCartItem()));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
    public int getTotalCart(Cart cart) {
        int sum = 0;
        for (int i = 0; i < cart.getListCartItem().size(); i++) {
            sum += cart.getListCartItem().get(i).getTotalPrice();
        }
        return sum + (int)(sum * 0.1); // tính cả thuế
    }
    @Override
    public int getItemCount() {
        if (listCart != null) return listCart.size();
        return 0;
    }

    public class HistoryPurchasingViewHolder extends RecyclerView.ViewHolder{
        private TextView tvItemCount, tvTimestamp, tvTotalPrice;
        private CardView cvHistoryPurchasing;
        public HistoryPurchasingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemCount = itemView.findViewById(R.id.tvItemCount);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            cvHistoryPurchasing = itemView.findViewById(R.id.cvHistoryPurchasing);
        }
    }
}

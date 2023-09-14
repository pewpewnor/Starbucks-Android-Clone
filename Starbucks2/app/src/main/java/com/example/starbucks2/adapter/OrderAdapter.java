package com.example.starbucks2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starbucks2.R;
import com.example.starbucks2.models.Order;
import com.example.starbucks2.models.Product;

import java.util.ArrayList;
import java.util.Collections;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private ArrayList<Order> orders;

    public OrderAdapter(ArrayList<Order> orders) {
        Collections.reverse(orders);
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String products = this.orders.get(position).getProducts();
        holder.tvName.setText(products);
        String price = "Total: $" + this.orders.get(position).getTotalPrice();
        holder.tvPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        CardView cvProduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            cvProduct = itemView.findViewById(R.id.cvProduct);
        }
    }
}

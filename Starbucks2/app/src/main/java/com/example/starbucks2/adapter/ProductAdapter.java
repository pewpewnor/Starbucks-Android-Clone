package com.example.starbucks2.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starbucks2.R;
import com.example.starbucks2.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private ArrayList<Product> products;
    private ProductClickListener listener;
    private boolean second = false;

    public ProductAdapter(ArrayList<Product> products, ProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    public ProductAdapter(ArrayList<Product> products) {
        this.products = products;
        this.listener = new ProductClickListener() {
            @Override
            public void onProductClicked(int position) {
                return;
            }
        };
        this.second = true;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);

        if (this.second == true) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        }

        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(this.products.get(position).getName());
        String price = "$" + this.products.get(position).getPrice();
        holder.tvPrice.setText(price);
        if (second == true) return;
        holder.picCoffee.setImageDrawable(this.products.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView picCoffee;
        CardView cvProduct;

        public MyViewHolder(@NonNull View itemView, ProductClickListener listener) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            picCoffee = (ImageView) itemView.findViewById(R.id.picCoffee);
            cvProduct = itemView.findViewById(R.id.cvProduct);

            cvProduct.setOnClickListener(x -> {
                listener.onProductClicked(getAdapterPosition());
            });
        }
    }

    public interface ProductClickListener {
        void onProductClicked(int position);
    }
}

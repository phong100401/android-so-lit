package com.example.practice_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder>{
    Context currentContext;
    List<Product> products;

    public ListProductAdapter(Context currentContext, List<Product> products) {
        this.currentContext = currentContext;
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(currentContext).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.idView.setText(String.valueOf(currentProduct.getId()));
        holder.productNameView.setText(currentProduct.getName());
        holder.productQuantityView.setText(String.valueOf(currentProduct.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView idView;
        TextView productNameView;
        TextView productQuantityView;

        public ViewHolder(View itemView) {
            super(itemView);
            idView = itemView.findViewById(R.id.product_id);
            productNameView = itemView.findViewById(R.id.product_name);
            productQuantityView = itemView.findViewById(R.id.product_quantity);
        }
    }
}

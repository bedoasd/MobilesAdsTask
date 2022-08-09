package com.example.mobilesads.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobilesads.R;
import com.example.mobilesads.model.Products;
import com.example.mobilesads.viewmodel.ProductsViewModel;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Products> productsList = new ArrayList<>();
    private Context mcontext;
    private OnProductClickListener onProductClickListener;
    private ProductsViewModel productsViewModel;
    int global_position;

    public Adapter( Context mcontext, OnProductClickListener onProductClickListener) {
        this.mcontext = mcontext;
        this.onProductClickListener = onProductClickListener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int stock=productsList.get(global_position).getStock();
        Log.v("stock",String.valueOf(stock));
        if(stock>50)
        {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item2, parent, false), onProductClickListener);

        }
        else
        {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false), onProductClickListener);

        }


    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        global_position=position;
        holder.producttitle.setText(productsList.get(position).getTitle());
        holder.product_description.setText(productsList.get(position).getDescription());
        holder.product_price.setText(""+String.valueOf(productsList.get(position).getPrice())+"LE");


    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(productsViewModel==null){
            productsViewModel=new ViewModelProvider((ViewModelStoreOwner) recyclerView.getContext()).get(ProductsViewModel.class);
        }
    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void setList(List<Products>modelList){
        this.productsList =modelList;
        notifyDataSetChanged();
    }

    public Products getSelectedProduct(int position){

        if(productsList!=null){
            if( productsList.size()>0){
                return productsList.get(position);
            }
        }
        return null;

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView producttitle,product_description,product_price;
        OnProductClickListener onProductClickListener;

        public MyViewHolder(@NonNull View itemView, OnProductClickListener onProductClickListener) {
            super(itemView);
            this.onProductClickListener=onProductClickListener;
            producttitle = itemView.findViewById(R.id.product_title);
            product_description = itemView.findViewById(R.id.product_description);
            product_price = itemView.findViewById(R.id.product_price);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            onProductClickListener.onProductClick(getAdapterPosition());
        }
    }
}
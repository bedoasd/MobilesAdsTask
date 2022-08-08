package com.example.mobilesads;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobilesads.adapters.Adapter;
import com.example.mobilesads.adapters.OnProductClickListener;
import com.example.mobilesads.model.Products;
import com.example.mobilesads.viewmodel.ProductsViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements OnProductClickListener {


    private ProductsViewModel productsViewModel;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=findViewById(R.id.usernamee);

        if(getIntent().hasExtra("user")){
        String user=getIntent().getStringExtra("user");
        username.setText( user);
        }

        recyclerView=findViewById(R.id.recycler);
        adapter=new Adapter(getApplicationContext(),MainActivity.this);
        recyclerView.setAdapter(adapter);

        productsViewModel=new ViewModelProvider(this).get(ProductsViewModel.class);

        productsViewModel.getProducts();

        productsViewModel.productsList.observe(this, new Observer<ArrayList<Products>>() {
            @Override
            public void onChanged(ArrayList<Products> products) {
                adapter.setList(products);
            }
        });

    }

    @Override
    public void onProductClick(int position) {
       String title= adapter.getSelectedProduct(position).getTitle();
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.mydialog);

        TextView dialog_title = (TextView) dialog.findViewById(R.id.dialog_title);
        TextView diaog_description = (TextView) dialog.findViewById(R.id.dialog_description);
        TextView dialog_price = (TextView) dialog.findViewById(R.id.dialog_price);
        ImageView productImage = dialog.findViewById(R.id.dialog_image);

        dialog_title.setText(adapter.getSelectedProduct(position).getTitle());
        diaog_description.setText(adapter.getSelectedProduct(position).getDescription());
        dialog_price.setText(String.valueOf(adapter.getSelectedProduct(position).getPrice())+"LE");

        Glide.with(MainActivity.this)
                .load(adapter.getSelectedProduct(position).getThumbnail())
                .into(productImage);
        dialog.show();





    }
}
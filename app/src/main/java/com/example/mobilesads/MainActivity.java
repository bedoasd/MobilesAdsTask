package com.example.mobilesads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.mobilesads.adapters.Adapter;
import com.example.mobilesads.adapters.OnProductClickListener;
import com.example.mobilesads.model.Products;
import com.example.mobilesads.viewmodel.ProductsViewModel;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements OnProductClickListener {


    private ProductsViewModel productsViewModel;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private TextView username;
    private ImageView logot;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logot=findViewById(R.id.logout);
        mAuth=FirebaseAuth.getInstance();
        username=findViewById(R.id.usernamee);
        String name=mAuth.getCurrentUser().getEmail();

   /*     YoYo.with(Techniques.BounceInRight)
                .duration(700)
                .repeat(3)
                .playOn(findViewById(R.id.usernamee));*/
        username.setText(name);
        username.setSelected(true);

        recyclerView = findViewById(R.id.recycler);
        adapter = new Adapter(getApplicationContext(), MainActivity.this);
        recyclerView.setAdapter(adapter);

        productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);

        productsViewModel.getProducts();

        productsViewModel.productsList.observe(this, new Observer<ArrayList<Products>>() {
            @Override
            public void onChanged(ArrayList<Products> products) {
                adapter.setList(products);
            }
        });

        logot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));

                    }
                });
            }
        });

    }

    @Override
    public void onProductClick(int position) {

        shoeItem(position);
    }


    private void shoeItem(int position){
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
        dialog_price.setText(adapter.getSelectedProduct(position).getPrice()+"LE");

        Glide.with(MainActivity.this)
                .load(adapter.getSelectedProduct(position).getThumbnail())
                .into(productImage);
        dialog.show();
    }


}
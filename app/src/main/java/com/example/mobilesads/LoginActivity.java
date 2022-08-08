package com.example.mobilesads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username =findViewById(R.id.et_email);
        password=findViewById(R.id.et_pass);
        loginbtn=findViewById(R.id.btn_login);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=username.getText().toString().trim();
                String pass=password.getText().toString().trim();
                if (TextUtils.isEmpty(user)){
                    username.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    password.setError("Required Field..");
                    return;
                }

                if (password.length()<8){
                    password.setError("Must be greater than 8 elements..");
                }
                else{
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }

            }
        });



    }
}
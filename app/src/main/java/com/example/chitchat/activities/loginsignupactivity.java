package com.example.chitchat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chitchat.R;

public class loginsignupactivity extends AppCompatActivity {


    Button login, signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsignupactivity_activity);


        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent loginintent = new Intent(loginsignupactivity.this, loginpage.class);
                startActivity(loginintent);
                finish();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signupintent = new Intent(loginsignupactivity.this, signuppage.class);
                startActivity(signupintent);
                finish();

            }
        });

    }

}
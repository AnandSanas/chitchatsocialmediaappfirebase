package com.example.chitchat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chitchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class loginpage extends AppCompatActivity {

    Button login_lp;
    ImageView signupdp_lp;
    EditText email_lp,pwd_lp;
    TextView fgtpwd,createnewac_lp;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage_activity);

        login_lp = findViewById(R.id.login_lp);
        signupdp_lp = findViewById(R.id.signupdp_lp);
        email_lp = findViewById(R.id.email_lp);
        pwd_lp = findViewById(R.id.pwd_lp);
        fgtpwd = findViewById(R.id.fgtpwd);
        createnewac_lp = findViewById(R.id.createnewac_lp);
        mAuth = FirebaseAuth.getInstance();
        databaseReference =  database.getReference("chitchat");


        createnewac_lp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginpageintent = new Intent(loginpage.this, signuppage.class);
                startActivity(loginpageintent);
                finish();
            }
        });



        login_lp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginuser();
            }


        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentuser = mAuth.getCurrentUser();

    }

    private void loginuser() {

        String email = email_lp.getText().toString().trim();
        String password = pwd_lp.getText().toString().trim();



        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser user = mAuth.getCurrentUser();

                    Toast.makeText(loginpage.this, user+"is loged in", Toast.LENGTH_LONG).show();

                    Intent homeintent = new Intent(loginpage.this, MainActivity.class);
                    startActivity(homeintent);
                    finish();


                } else {

                    Log.i("TAG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(loginpage.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}
package com.example.chitchat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chitchat.R;
import com.example.chitchat.datamodels.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signuppage extends AppCompatActivity {

    EditText signupid,signuppwd,signupemail;
    Button signup_sp;
    TextView alreadyhaveaccount;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    String  stringname, stringemail, stringpwd;
    String currentuser,localid  ;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("chitchat"); // userref is invisible on cloud



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage_activity);

        signupid = findViewById(R.id.signupid);
        signuppwd = findViewById(R.id.signuppwd);
        signupemail = findViewById(R.id.signupemail);
        signup_sp = findViewById(R.id.signup_sp);
        alreadyhaveaccount = findViewById(R.id.alreadyhaveaccount);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        localid = databaseReference.push().getKey();

        final Intent signupintent = new Intent(signuppage.this, loginpage.class);

        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(signupintent);
                finish();
            }
        });



        signup_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //always remeber to check rules if text dont upload

                registeruser();// string converion and validation for email id and pwd

                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(stringemail, stringpwd) // stringemail stringpwd pass from reisteruser
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful())
                                {
                                    currentuser = mAuth.getCurrentUser().getUid();
//                                    Toast.makeText(signuppage.this,"User successfully reister",Toast.LENGTH_LONG).show();
                                    User user = new User(stringname, stringemail, localid);// values pass  from registerser to user object
                                    databaseReference.child(localid).setValue(user)

                                    //         Chitchat.child(currentuser).setValue(user)                             or

                                    // FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()){

                                                        Toast.makeText(signuppage.this,"User has been successfull signup",Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        startActivity(signupintent);
                                                        finish();

                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(signuppage.this,"Failed to register Try again",Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                    }
                                                }
                                            });

                                }
                                else
                                {
                                    Toast.makeText(signuppage.this,"Failed to register Try again",Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.INVISIBLE);

                                }
                            }
                        });

            }
        });


    }



    private void registeruser() {

         stringname = signupid.getText().toString().trim();
         stringemail = signupemail.getText().toString().trim();
         stringpwd =  signuppwd.getText().toString().trim();


        if (stringname.isEmpty())
        {
            signupid.setError("Name is required");
            signupid.requestFocus();

        }

            if (stringemail.isEmpty())
        {
            signupemail.setError("Email is required");
            signupemail.requestFocus();

        }
            if (stringpwd.isEmpty())
        {
            signuppwd.setError("Password is required");
            signuppwd.requestFocus();

        }

            if (stringpwd.length()<6)
            {
                signuppwd.setError("Min paswword lentgh is 6");
                signuppwd.requestFocus();
            }


    }

}
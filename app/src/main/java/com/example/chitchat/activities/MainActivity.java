package com.example.chitchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.chitchat.R;
import com.example.chitchat.homefragrent;
import com.example.chitchat.newpost;
import com.example.chitchat.profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar1 = findViewById(R.id.optionmenu);

        setSupportActionBar(toolbar1);

        firebaseAuth = FirebaseAuth.getInstance();
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new homefragrent()).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedfragment=null;

                switch (item.getItemId())
                        {
                        case R.id.homefragrent:
                        selectedfragment= new homefragrent();
                        break;

                        case R.id.newpost:
                        selectedfragment= new newpost();
                        break;

                        case R.id.userprofilepic:
                        selectedfragment= new profile();
                        break;
                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedfragment).commit();
                        return true; // if sets false it will not highlight the icons from botom navigation


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.screentopmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logoutbtn:
                firebaseAuth.signOut();
                Intent signoutInten = new Intent(MainActivity.this, loginpage.class);
                startActivity(signoutInten);
                return true;

            case R.id.editprofile:
                Intent edit = new Intent(getApplicationContext(),updateprofile.class);
                startActivity(edit);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}



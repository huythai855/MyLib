package com.project.mylib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Act_Menu extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_menu);

        bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        System.out.println("status " + status);

        switch (status){
            case "1":
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Frag_Borrow()).commit();
                return;
            case "2":
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Frag_Bookshielf()).commit();
                return;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Frag_Security()).commit();
                return;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment = null;

                    switch (item.getItemId()){
                        case R.id.tranfer:
                            fragment = new Frag_Borrow();
                            break;

                        case R.id.bookshielf:
                            fragment = new Frag_Bookshielf();
                            break;

                        case R.id.security:
                            fragment = new Frag_Security();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

                    return true;
                }
            };
}
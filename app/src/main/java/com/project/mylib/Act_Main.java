package com.project.mylib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Act_Main extends AppCompatActivity {

    static SharedPreferences login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        login = Act_Main.this.getSharedPreferences("login", Act_Main.this.MODE_PRIVATE);
        String all = login.getString("username", "null");

        System.out.println(all);
        System.out.println(12345);

        if(all.equals("null")){
            Intent intent = new Intent(Act_Main.this, Act_Register.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(Act_Main.this, Act_Login.class);
            startActivity(intent);
        }

    }
}
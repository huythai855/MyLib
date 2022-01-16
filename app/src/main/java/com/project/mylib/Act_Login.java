package com.project.mylib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Act_Login extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btnLogin;
    TextView warning;
    static SharedPreferences login;

    @Override
    public void onBackPressed() {
    }

    void init(){
        username = findViewById(R.id.edtUsernameLogin);
        password = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        warning = findViewById(R.id.txtWarningLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        init();

        login = Act_Login.this.getSharedPreferences("login", Act_Login.this.MODE_PRIVATE);

        String uname = login.getString("username", "null");
        String pass = login.getString("password", "null");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname_check = username.getText().toString();
                String pass_check = password.getText().toString();

                if(uname_check.equals(uname) && pass_check.equals(pass)){
                    // TODO create class Tranfer
                    Intent intent = new Intent(Act_Login.this, Act_Menu.class);
                    intent.putExtra("status","1");
                    startActivity(intent);
                }
                else{
                    warning.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
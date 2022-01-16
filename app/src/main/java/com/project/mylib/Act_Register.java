package com.project.mylib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Act_Register extends AppCompatActivity {

    EditText edtNameRegis, edtUsernameRegis, edtPasswordRegis;
    Button btnRegis;
    TextView txtWarningRegis;
    static SharedPreferences login;

    void init(){
        edtNameRegis = findViewById(R.id.edtNameChange);
        edtUsernameRegis = findViewById(R.id.edtOldpassChange);
        edtPasswordRegis = findViewById(R.id.edtNewpassChange);
        txtWarningRegis = findViewById(R.id.txtWarningChange);
        btnRegis = findViewById(R.id.btnChange);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);

        login = Act_Register.this.getSharedPreferences("login", Act_Register.this.MODE_PRIVATE);

        init();

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtNameRegis.getText().toString();
                String uname = edtUsernameRegis.getText().toString();
                String pass = edtPasswordRegis.getText().toString();

                boolean ok1 = false, ok2 = false;
                int len;
                String s;

                len = uname.length();
                if(len == 0) ok1 = true;
                s = uname;
                for(int i=0; i< len; i++)
                    if(s.charAt(i) == ' ')
                        ok1 = true;

                len = pass.length();
                if(len == 0) ok2 = true;
                s = pass;
                for(int i=0; i< len; i++)
                    if(s.charAt(i) == ' ')
                        ok2 = true;


                if(ok1 || ok2){
                    txtWarningRegis.setText("Tên đăng nhập hoặc mật khẩu không hợp lệ!");
                    txtWarningRegis.setVisibility(View.VISIBLE);
                    return;
                }

                if(name.equals("")){
                    txtWarningRegis.setText("Tên người dùng không hợp lệ");
                    txtWarningRegis.setVisibility(View.VISIBLE);
                    return;
                }


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                SharedPreferences.Editor e_login = login.edit();
                e_login.putString("username", uname);
                e_login.putString("password", pass);
                e_login.putString("uname", name);
                e_login.commit();

                String all = login.getString("username", "null");

                Intent intent = new Intent(Act_Register.this, Act_Login.class);
                startActivity(intent);

            }
        });









    }


}
package com.project.mylib;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link #newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_Security extends Fragment {

    static SharedPreferences login;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;




    public Frag_Security() {
        // Required empty public constructor
    }

    public static Frag_Security newInstance(String param1, String param2) {
        Frag_Security fragment = new Frag_Security();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_security, container, false);
        Context context = view.getContext();

        EditText name = view.findViewById(R.id.edtNameChange);
        EditText oldpass = view.findViewById(R.id.edtOldpassChange);
        EditText newpass = view.findViewById(R.id.edtNewpassChange);

        TextView warning = view.findViewById(R.id.txtWarningChange);
        Button change = view.findViewById(R.id.btnChange);
        TextView logout = view.findViewById(R.id.txtLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Act_Login.class);
                startActivity(intent);
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String Oldpass = oldpass.getText().toString();
                String Newpass = newpass.getText().toString();

                int len;
                boolean ok = true;

                len = Name.length();
                if(len == 0){
                    warning.setText("Tên người dùng không hợp lệ");
                    warning.setVisibility(View.VISIBLE);
                    return;
                }

                login = context.getSharedPreferences("login", context.MODE_PRIVATE);
                String Old = login.getString("password","null");

                if(!Oldpass.equals(Old)){
                    warning.setText("Mật khẩu cũ không đúng");
                    warning.setVisibility(View.VISIBLE);
                    return;
                }

                len = Newpass.length();
                if(len == 0)
                    ok = false;
                for(int i=0; i<len; i++)
                    if(Newpass.charAt(i) == ' ')
                        ok = false;

                if(ok == false){
                    warning.setText("Mật khẩu mới không hợp lệ");
                    warning.setVisibility(View.VISIBLE);
                    return;
                }

                warning.setText("Đổi mật khẩu thành công!");
                warning.setVisibility(View.VISIBLE);
                warning.setTextColor(getResources().getColor(R.color.xanh_luc_dam));

                SharedPreferences.Editor e_login = login.edit();
                e_login.putString("password", Newpass);
                e_login.putString("uname", Name);
                e_login.commit();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(context, Act_Login.class);
                startActivity(intent);
            }
        });





        return view;
    }
}
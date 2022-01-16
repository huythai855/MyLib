package com.project.mylib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Sub_ViewBorrow extends AppCompatActivity {

    ImageView imgBook, btnReturn;
    TextView txtTitle, txtAuthor, ansBorrowCode, txtNumber, txtCode;
    TextView ansBorrowName, ansBorrowAddress, ansBorrowTel, ansBorrowDate, ansBorrowDate2, ansLibrarian;
    String bookKey, number;

    void init(){
        imgBook = findViewById(R.id.imgBook);
        btnReturn = findViewById(R.id.btnReturn);

        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        ansBorrowCode = findViewById(R.id.ansBorrowCode);
        txtNumber = findViewById(R.id.txtNumber);
        txtCode = findViewById(R.id.txtCode);

        ansBorrowName = findViewById(R.id.ansBorrowName);
        ansBorrowAddress = findViewById(R.id.ansBorrowAddress);
        ansBorrowTel = findViewById(R.id.ansBorrowTel);
        ansBorrowDate = findViewById(R.id.ansBorrowDate);
        ansBorrowDate2 = findViewById(R.id.ansBorrowDate2);
        ansLibrarian = findViewById(R.id.ansLibrarian);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_viewborrow);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Xem lượt mượn");

        init();

        Intent intent = getIntent();
        int key = intent.getIntExtra("key",0);
        Context context = Sub_ViewBorrow.this;

        SharedPreferences BorrowBook, BorrowTitle, BorrowPerson, BorrowCode, BorrowDate, BorrowDate2, BorrowNumber, BorrowAddress, BorrowTel, BorrowLibrarian;
        SharedPreferences  BookAuthor, booklist, BookCode;
        SharedPreferences login;

        BorrowBook = context.getSharedPreferences("BorrowBook", context.MODE_PRIVATE);
        BorrowTitle = context.getSharedPreferences("BorrowTitle", context.MODE_PRIVATE);
        BorrowPerson = context.getSharedPreferences("BorrowPerson", context.MODE_PRIVATE);
        BorrowCode = context.getSharedPreferences("BorrowCode", context.MODE_PRIVATE);
        BorrowDate = context.getSharedPreferences("BorrowDate", context.MODE_PRIVATE);
        BorrowDate2 = context.getSharedPreferences("BorrowDate2", context.MODE_PRIVATE);
        BorrowAddress = context.getSharedPreferences("BorrowAddress", context.MODE_PRIVATE);
        BorrowNumber = context.getSharedPreferences("BorrowNumber", context.MODE_PRIVATE);
        BorrowTel = context.getSharedPreferences("BorrowTel", context.MODE_PRIVATE);
        BorrowLibrarian = context.getSharedPreferences("BorrowLibrarian", context.MODE_PRIVATE);
        booklist = context.getSharedPreferences("booklist", context.MODE_PRIVATE);


        BookAuthor = context.getSharedPreferences("BookAuthor", context.MODE_PRIVATE);
        BookCode = context.getSharedPreferences("BookCode", context.MODE_PRIVATE);

        String Key = Integer.toString(key); /// số thứ tự mượn trong Tranfer list
        String Null = "null";

        String bookCode = BorrowBook.getString(Key,"null");

        System.out.println(bookCode);

        int BookNum = booklist.getInt("NumberOfBook",0);

        System.out.println(BookNum);


        for(int i=1; i<=BookNum; i++){
            String temp = Integer.toString(i);
            String cmp = BookCode.getString(temp,"null");
            System.out.println(cmp);
            if(cmp.equals(bookCode)){
                bookKey = Integer.toString(i);
                System.out.println(bookKey);
            }
        }




        String bookTitle = BorrowTitle.getString(Key, "null");
        String bookAuthor = BookAuthor.getString(bookKey,Null);

        String code = BorrowCode.getString(Key, Null);
        number = BorrowNumber.getString(Key, Null);

        String name = BorrowPerson.getString(Key, Null);
        String address = BorrowAddress.getString(Key,Null);
        String tel = BorrowTel.getString(Key, Null);
        String date = BorrowDate.getString(Key, Null);
        String date2 = BorrowDate2.getString(Key, Null);
        String librarian = BorrowLibrarian.getString(Key, Null);

        txtCode.setText("Mã sách: " + bookCode);
        txtTitle.setText(bookTitle);
        txtNumber.setText("Số lượng: " + number + " cuốn");
        ansBorrowCode.setText(code);
        ansBorrowCode.setVisibility(View.VISIBLE);
        txtAuthor.setText("Tác giả: " + bookAuthor);


        ansBorrowName.setText(name);
        ansBorrowAddress.setText(address);
        ansBorrowTel.setText(tel);
        ansBorrowDate.setText(date);
        ansBorrowDate2.setText(date2);
        ansLibrarian.setText(librarian);

        String IP = "http://"+ getString(R.string.ip) + "/upload2/"+ bookCode +".jpg";
        String ImgLink = IP;

        Glide.with(context)
                .asBitmap()
                .load(ImgLink)
                .into(imgBook);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = BorrowCode.edit();
                editor.putString(Key, "null");
                editor.commit();

                SharedPreferences BookBorrowing = getSharedPreferences("BookBorrowing", MODE_PRIVATE);


                String Now = BookBorrowing.getString(bookKey, "0");
                int next = Integer.valueOf(Now) - Integer.valueOf(number);

                System.out.println("this \n");
                System.out.println(bookKey + " ");

                System.out.println(Now);
                System.out.println(number);
                System.out.println(next);

                SharedPreferences.Editor editor1;
                editor = BookBorrowing.edit();
                editor.putString(bookKey, Integer.toString(next));
                editor.commit();



                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Sub_ViewBorrow.this, Act_Menu.class);
                intent.putExtra("status","1");
                startActivity(intent);
            }
        });


    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(Sub_ViewBorrow.this, Act_Menu.class);
        intent.putExtra("status","1");
        startActivity(intent);

        return true;
    }
}
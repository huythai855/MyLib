package com.project.mylib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Sub_ViewBook extends AppCompatActivity {

    TextView ansTitle, ansAuthor, ansCode, ansDescription, ansGenre, ansLanguage, ansLocation, ansBorrowing, ansAvailable;
    String Title, Author, Code, Description, Genre, Language, Location, Borrowing, Available, Number;
    ImageView btnEdit, imgBook;

    void init(){
        ansTitle = findViewById(R.id.ansTitle);
        ansAuthor = findViewById(R.id.ansAuthor);
        ansCode = findViewById(R.id.ansCode);
        ansDescription = findViewById(R.id.ansDescription);
        ansGenre = findViewById(R.id.ansGenre);
        ansLanguage = findViewById(R.id.ansLanguage);
        ansLocation = findViewById(R.id.ansLocation);
        ansBorrowing = findViewById(R.id.ansBorrowing);
        ansAvailable = findViewById(R.id.ansAvaiable);
        btnEdit = findViewById(R.id.btnEdit);
        imgBook = findViewById(R.id.imgBook);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_viewbook);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        int key = intent.getIntExtra("key",0);
        Context context = Sub_ViewBook.this;

        String Key = Integer.toString(key);

        init();

        SharedPreferences abc;
        abc = context.getSharedPreferences("BookTitle", context.MODE_PRIVATE);
        Title = abc.getString(Key, "null");

        getSupportActionBar().setTitle(Title);

        abc = context.getSharedPreferences("BookAuthor", context.MODE_PRIVATE);
        Author = abc.getString(Key, "null");

        abc = context.getSharedPreferences("BookCode", context.MODE_PRIVATE);
        Code = abc.getString(Key, "null");

        abc = context.getSharedPreferences("BookDescription", context.MODE_PRIVATE);
        Description = abc.getString(Key, "null");

        abc = context.getSharedPreferences("BookGenre", context.MODE_PRIVATE);
        Genre = abc.getString(Key, "null");

        abc = context.getSharedPreferences("BookLanguage", context.MODE_PRIVATE);
        Language = abc.getString(Key, "null");

        abc = context.getSharedPreferences("BookLocation", context.MODE_PRIVATE);
        Location = abc.getString(Key, "null");

        abc = context.getSharedPreferences("BookBorrowing", context.MODE_PRIVATE);
        Borrowing = abc.getString(Key, "null");



        abc = context.getSharedPreferences("BookNumber", context.MODE_PRIVATE);
        Number = abc.getString(Key, "null");
        int all = Integer.valueOf(Number);
        int borrow = Integer.valueOf(Borrowing);
        int available = all - borrow;
        Available = Integer.toString(available);

        String IP = "http://"+ getString(R.string.ip) + "/upload2/" + Code +".jpg";
        String imglink = IP;

        Glide.with(context)
                .asBitmap()
                .load(imglink)
                .into(imgBook);

        ansTitle.setText(Title);
        ansAuthor.setText("Tác giả: " + Author);
        ansCode.setText("Mã sách: " + Code);
        ansDescription.setText(Description);
        ansGenre.setText(Genre);
        ansLanguage.setText(Language);
        ansLocation.setText(Location);
        ansBorrowing.setText(Borrowing);
        ansAvailable.setText(Available);

        // todo: Edit Button
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sub_ViewBook.this, Opt_EditBook.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(Sub_ViewBook.this, Act_Menu.class);
        intent.putExtra("status","2");
        startActivity(intent);

        return true;
        //return super.onOptionsItemSelected(item);
    }
}
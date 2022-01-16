package com.project.mylib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Opt_EditBook extends AppCompatActivity {

    EditText edtTitle, edtAuthor, edtCode, edtDescription, edtNumber, edtLocation;
    Spinner spGenre, spLanguage;
    TextView txtWarningAddBook;
    ImageView btnUpload, btnReload, btnSave, imgBook;
    String Genre1, Language1;
    String Key;
    int key;
    static SharedPreferences booklist;
    static SharedPreferences BookTitle, BookAuthor, BookCode, BookDescription, BookGenre, BookNumber, BookLanguage, BookLocation, BookBorrowing;

    void init(){
        edtTitle = findViewById(R.id.edtTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtCode = findViewById(R.id.edtCode);
        edtDescription = findViewById(R.id.edtDescription);
        edtNumber = findViewById(R.id.edtNumber);
        edtLocation = findViewById(R.id.edtLocation);
        spGenre = findViewById(R.id.spGenre);
        spLanguage = findViewById(R.id.spLanguage);
        txtWarningAddBook = findViewById(R.id.txtWarningAddBook);
        btnUpload = findViewById(R.id.btnUpload);
        btnReload = findViewById(R.id.btnReload);
        btnSave = findViewById(R.id.btnSave);
        imgBook = findViewById(R.id.imgBook);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opt_editbook);

        Context context = this;
        BookTitle = context.getSharedPreferences("BookTitle", context.MODE_PRIVATE);
        BookAuthor = context.getSharedPreferences("BookAuthor", context.MODE_PRIVATE);
        BookCode = context.getSharedPreferences("BookCode", context.MODE_PRIVATE);
        BookDescription = context.getSharedPreferences("BookDescription", context.MODE_PRIVATE);
        BookGenre = context.getSharedPreferences("BookGenre", context.MODE_PRIVATE);
        BookNumber = context.getSharedPreferences("BookNumber", context.MODE_PRIVATE);
        BookLanguage = context.getSharedPreferences("BookLanguage", context.MODE_PRIVATE);
        BookLocation = context.getSharedPreferences("BookLocation", context.MODE_PRIVATE);
        BookBorrowing = context.getSharedPreferences("BookBorrowing", context.MODE_PRIVATE);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa thông tin sách");

        init();

        Intent intent = getIntent();
        key = intent.getIntExtra("key", 0);
        Key = Integer.toString(key);

        edtTitle.setHint(BookTitle.getString(Key, "null"));
        edtAuthor.setHint(BookAuthor.getString(Key, "null"));
        edtCode.setHint(BookCode.getString(Key, "null"));
        edtDescription.setHint(BookDescription.getString(Key, "null"));
        edtNumber.setHint(BookNumber.getString(Key, "null"));
        edtLocation.setHint(BookLocation.getString(Key, "null"));
        edtTitle.setHint(BookTitle.getString(Key, "null"));

        String imglink = "http://192.168.1.4:8080/upload2/"+BookCode.getString(Key, "null")+".jpg";

        Glide.with(context)
                .asBitmap()
                .load(imglink)
                .into(imgBook);



        // todo: spGenre
        {
            ArrayList<String> Genres = new ArrayList<>();
            Genres.add("Tiểu thuyết");
            Genres.add("Khoa học");
            Genres.add("Pháp luật");
            Genres.add("Tâm lý học");
            Genres.add("Giáo trình");
            Genres.add("Văn hóa xã hội");
            Genres.add("Kinh tế");
            Genres.add("Thiếu nhi");

            ArrayAdapter<String> SearchlistAdapter = new ArrayAdapter<>(
                    Opt_EditBook.this, android.R.layout.simple_spinner_dropdown_item, Genres
            );

            spGenre.setAdapter(SearchlistAdapter);

            spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Genre1 = Genres.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }


        // todo: spLanguage
        {
            ArrayList<String> Languages = new ArrayList<>();
            Languages.add("Tiếng Việt");
            Languages.add("Tiếng Anh");
            Languages.add("Tiếng Trung");
            Languages.add("Khác");

            ArrayAdapter<String> SearchlistAdapter = new ArrayAdapter<>(
                    Opt_EditBook.this, android.R.layout.simple_spinner_dropdown_item, Languages
            );

            spLanguage.setAdapter(SearchlistAdapter);

            spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Language1 = Languages.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }


        // todo: btnSave
        {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Title, Author, Code, Description, Number, Location;
                    String Genre, Language;

                    Title = edtTitle.getText().toString();
                    Author = edtAuthor.getText().toString();
                    Code = edtCode.getText().toString();
                    Description = edtDescription.getText().toString();
                    Number = edtNumber.getText().toString();
                    Location = edtLocation.getText().toString();
                    Genre = Genre1;
                    Language = Language1;

                    boolean check = true;
                    if(Title.equals("")) check = false;
                    if(Author.equals("")) check = false;
                    if(Code.equals("")) check = false;
                    if(Description.equals("")) check = false;
                    if(Number.equals("")) check = false;
                    if(Location.equals("")) check = false;

                    if(check == false){
                        txtWarningAddBook.setVisibility(View.VISIBLE);
                    }
                    else {
                        Context context = Opt_EditBook.this;
                        int AllNumber = key-1;

                        SharedPreferences.Editor editor;

                        String key = Integer.toString(AllNumber + 1);
                        System.out.println(key);



                        editor = BookTitle.edit();
                        editor.putString(key, Title);
                        editor.commit();

                        editor = BookAuthor.edit();
                        editor.putString(key, Author);
                        editor.commit();

                        editor = BookCode.edit();
                        editor.putString(key, Code);
                        editor.commit();

                        editor = BookDescription.edit();
                        editor.putString(key, Description);
                        editor.commit();

                        editor = BookGenre.edit();
                        editor.putString(key, Genre);
                        editor.commit();

                        editor = BookNumber.edit();
                        editor.putString(key, Number);
                        editor.commit();

                        editor = BookLanguage.edit();
                        editor.putString(key, Language);
                        editor.commit();

                        editor = BookLocation.edit();
                        editor.putString(key, Location);
                        editor.commit();

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(Opt_EditBook.this, Sub_ViewBook.class);
                        intent.putExtra("key",key);
                        startActivity(intent);
                    }
                }
            });
        }


        // todo: btnUpload
        {
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Opt_EditBook.this, Opt_Upload.class);
                    intent.putExtra("url", "http://192.168.1.4:8080/upload2.php");
                    intent.putExtra("key", key);
                    startActivity(intent);
                }
            });
        }


        // todo: btnReload
        {
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String code = edtCode.getText().toString();
                    String imglink = "http://192.168.1.4:8080/upload2/"+code+".jpg";

                    Glide.with(Opt_EditBook.this)
                            .asBitmap()
                            .load(imglink)
                            .into(imgBook);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(Opt_EditBook.this, Sub_ViewBook.class);
        intent.putExtra("key", key);
        startActivity(intent);

        return true;
        //return super.onOptionsItemSelected(item);
    }

}
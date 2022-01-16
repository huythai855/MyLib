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

public class Sub_NewBook extends AppCompatActivity {

    EditText edtTitle, edtAuthor, edtCode, edtDescription, edtNumber, edtLocation;
    Spinner spGenre, spLanguage;
    TextView txtWarningAddBook;
    ImageView btnUpload, btnReload, btnSave, imgBook;
    String Genre1, Language1;
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
        setContentView(R.layout.sub_newbook);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm sách");

        init();

        // todo: spGenre
        {
            ArrayList<String> Genres = new ArrayList<>();
            Genres.add("Tiểu thuyết");
            Genres.add("Khoa học");
            Genres.add("Pháp luật");
            Genres.add("Tâm lý học");
            Genres.add("Sách giáo khoa");
            Genres.add("Văn hóa xã hội");
            Genres.add("Kinh tế");
            Genres.add("Thiếu nhi");

            ArrayAdapter<String> SearchlistAdapter = new ArrayAdapter<>(
                    Sub_NewBook.this, android.R.layout.simple_spinner_dropdown_item, Genres
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
                    Sub_NewBook.this, android.R.layout.simple_spinner_dropdown_item, Languages
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
                        System.out.println("Check = true");


                        Context context = Sub_NewBook.this;
                        booklist = context.getSharedPreferences("booklist", context.MODE_PRIVATE);

                        BookTitle = context.getSharedPreferences("BookTitle", context.MODE_PRIVATE);
                        BookAuthor = context.getSharedPreferences("BookAuthor", context.MODE_PRIVATE);
                        BookCode = context.getSharedPreferences("BookCode", context.MODE_PRIVATE);
                        BookDescription = context.getSharedPreferences("BookDescription", context.MODE_PRIVATE);
                        BookGenre = context.getSharedPreferences("BookGenre", context.MODE_PRIVATE);
                        BookNumber = context.getSharedPreferences("BookNumber", context.MODE_PRIVATE);
                        BookLanguage = context.getSharedPreferences("BookLanguage", context.MODE_PRIVATE);
                        BookLocation = context.getSharedPreferences("BookLocation", context.MODE_PRIVATE);
                        BookBorrowing = context.getSharedPreferences("BookBorrowing", context.MODE_PRIVATE);

                        int AllNumber = booklist.getInt("NumberOfBook", 0);

                        SharedPreferences.Editor editor;

                        String key = Integer.toString(AllNumber + 1);
                        System.out.println(key);

                        editor = booklist.edit();
                        editor.putInt("NumberOfBook", AllNumber + 1);
                        editor.commit();

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

                        editor = BookBorrowing.edit();
                        editor.putString(key, "0");
                        editor.commit();


                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(Sub_NewBook.this, Act_Menu.class);
                        intent.putExtra("status","2");
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
                    Intent intent = new Intent(Sub_NewBook.this, Opt_Upload.class);
                    String IP = "http://"+ getString(R.string.ip) + "/upload2.php";
                    intent.putExtra("url", IP);
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

                    String imglink = "http://"+ getString(R.string.ip) + "/upload2/"+code+".jpg";
                    Glide.with(Sub_NewBook.this)
                            .asBitmap()
                            .load(imglink)
                            .into(imgBook);
                }
            });
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(Sub_NewBook.this, Act_Menu.class);
        intent.putExtra("status","2");
        startActivity(intent);

        return true;
    }
}
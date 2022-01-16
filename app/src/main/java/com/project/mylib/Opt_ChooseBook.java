package com.project.mylib;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Opt_ChooseBook extends AppCompatActivity {
    static SharedPreferences booklist, BookTitle, BookAuthor, BookDescription, BookCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opt_choosebook);
        Context context = Opt_ChooseBook.this;

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chọn sách");

        booklist = context.getSharedPreferences("booklist", context.MODE_PRIVATE);
        BookTitle = context.getSharedPreferences("BookTitle", context.MODE_PRIVATE);
        BookAuthor = context.getSharedPreferences("BookAuthor", context.MODE_PRIVATE);
        BookDescription = context.getSharedPreferences("BookDescription", context.MODE_PRIVATE);
        BookCode = context.getSharedPreferences("BookCode", context.MODE_PRIVATE);

        int NumberOfBook = booklist.getInt("NumberOfBook", 0);

        String mTitle[] = new String[NumberOfBook+1];
        String mDescription[] = new String[NumberOfBook+1];
        String mImg[] = new String[NumberOfBook+1];
        String mAuthor[] = new String[NumberOfBook+1];


        for(int i=1; i<=NumberOfBook; i++){
            String key = Integer.toString(i);

            mTitle[i-1] = BookTitle.getString(key,"null");
            mAuthor[i-1] = BookAuthor.getString(key, "null");
            mDescription[i-1] = BookDescription.getString(key,"null");
            String code = BookCode.getString(key,"null");

            String IP = "http://"+ getString(R.string.ip) + "/upload2/"+code+".jpg";
            mImg[i-1] = IP;
        }


        ListView listView = findViewById(R.id.lvChooseBook);
        Adapter adapter = new Adapter(this, mTitle, mAuthor, mDescription, mImg);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==NumberOfBook)
                    return;

                Intent intent = new Intent(view.getContext(), Sub_NewBorrow.class);
                intent.putExtra("key", i+1);
                startActivity(intent);
            }
        });


    }

    class Adapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rAuthor[];
        String rDescription[];
        String rImg[];


        Adapter (Context c, String title[],String author[], String description[], String Img[]){
            super(c, R.layout.item_book, R.id.txtTitleRow, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rAuthor = author;
            this.rImg = Img;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.item_book, parent, false);

            ImageView image = row.findViewById(R.id.imgRow);
            TextView title = row.findViewById(R.id.txtTitleRow);
            TextView description = row.findViewById(R.id.txtDescriptionRow);
            TextView author = row.findViewById(R.id.txtAuthorRow);


            System.out.println(rImg[position]);

            Glide.with(row)
                    .asBitmap()
                    .load(rImg[position])
                    .into(image);
            title.setText(rTitle[position]);
            description.setText(rDescription[position]);
            author.setText(rAuthor[position]);

            return row;
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(Opt_ChooseBook.this, Sub_NewBorrow.class);
        intent.putExtra("key",0);
        startActivity(intent);

        return true;
    }
}
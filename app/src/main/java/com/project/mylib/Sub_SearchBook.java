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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Sub_SearchBook extends AppCompatActivity {

    EditText edtInfo;
    Spinner spSearch;
    Button btnSearch;
    ListView lvAnswer;
    String ChoosenSearch;
    static  SharedPreferences BookTitle, BookAuthor, BookGenre, BookCode, BookImg, BookDescription;
    int dem;

    void init(){
        edtInfo = findViewById(R.id.edtInfo);
        spSearch = findViewById(R.id.spSearch);
        btnSearch = findViewById(R.id.btnSearch);
        lvAnswer = findViewById(R.id.lvAnswer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_searchbook);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tìm sách");

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        String key = intent.getStringExtra("key");

        init();

        edtInfo.setHint(info);

        // TODO: Set searchlist
        {
            ArrayList<String> SearchList = new ArrayList<>();
            SearchList.add("Tên sách");
            SearchList.add("Tác giả");
            SearchList.add("Thể loại");
            SearchList.add("Mã sách");

            ArrayAdapter<String> SearchlistAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_dropdown_item, SearchList
            );

            spSearch.setAdapter(SearchlistAdapter);

            spSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ChoosenSearch = SearchList.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }


        // TODO: Set Search button
        {
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String info = edtInfo.getText().toString();
                    Intent intent = new Intent(Sub_SearchBook.this, Sub_SearchBook.class);
                    intent.putExtra("info", info);
                    intent.putExtra("key", ChoosenSearch);
                    startActivity(intent);
                }
            });

        }


        // TODO: set initial answer
        {
            SharedPreferences booklist;
            booklist = getSharedPreferences("booklist",this.MODE_PRIVATE);

            int num = booklist.getInt("NumberOfBook",0);
            System.out.println("num:"+num);



            BookTitle = getSharedPreferences("BookTitle", this.MODE_PRIVATE);
            BookAuthor = getSharedPreferences("BookAuthor", this.MODE_PRIVATE);
            BookGenre = getSharedPreferences("BookGenre", this.MODE_PRIVATE);
            BookCode = getSharedPreferences("BookCode", this.MODE_PRIVATE);
            BookImg = getSharedPreferences("BookImg", this.MODE_PRIVATE);
            BookDescription = getSharedPreferences("BookDescription", this.MODE_PRIVATE);

            int NumberOfBook = 0;
            dem = 0;

            boolean ok[] = new boolean[num+2];
            for(int i=1; i<=num; i++)
                ok[i] = false;

            System.out.println("Info:"+info);
            System.out.println("\n");
            System.out.println("Key:"+ key+"\n");

            for(int i=1; i<=num; i++){
                String number = Integer.toString(i);

                String Title = BookTitle.getString(number, "null");
                String Author = BookAuthor.getString(number, "null");
                String Genre = BookGenre.getString(number, "null");
                String Code = BookCode.getString(number, "null");

                if(key.equals("Tên sách"))
                    if(Title.equals(info)){
                        ++NumberOfBook;
                        ok[i] = true;
                    }

                if(key.equals("Tác giả"))
                    if(Author.equals(info)){
                        ++NumberOfBook;
                        ok[i] = true;
                    }

                if(key.equals("Thể loại"))
                    if(Genre.equals(info)){
                        ++NumberOfBook;
                        ok[i] = true;
                    }

                if(key.equals("Mã sách"))
                    if(Code.equals(info)){
                        ++NumberOfBook;
                        ok[i] = true;
                    }
            }

            System.out.println(NumberOfBook);

            String mTitle[] = new String[NumberOfBook+1];
            String mDescription[] = new String[NumberOfBook+1];
            String mImg[] = new String[NumberOfBook+1];
            String mAuthor[] = new String[NumberOfBook+1];
            int mId[] = new int[NumberOfBook+1];


            for(int i=1; i<=num; i++){
                String number = Integer.toString(i);
                String Title = BookTitle.getString(number, "null");
                String Author = BookAuthor.getString(number, "null");
                String Description = BookDescription.getString(number, "null");
                String Image = BookCode.getString(number, "null");

                if(ok[i] == true){
                    mTitle[dem] = Title;
                    mAuthor[dem] = Author;
                    mDescription[dem] = Description;
                    mImg[dem] = Image;
                    mId[dem] = i;
                    ++dem;
                }
            }

            Context context = getApplicationContext();
            Sub_SearchBook.Adapter adapter = new Adapter(context, mTitle, mAuthor, mDescription, mImg);
            lvAnswer.setAdapter(adapter);

            lvAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i==dem) return;
                    Intent intent = new Intent(view.getContext(), Sub_ViewBook.class);
                    intent.putExtra("key", mId[i]);
                    startActivity(intent);
                }
            });

        }
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

            String IP = "http://"+ getString(R.string.ip) + "/upload2/"+rImg[position]+".jpg";

            Glide.with(row)
                    .asBitmap()
                    //.load(rImg[position])
                    .load(IP)
                    .into(image);
            title.setText(rTitle[position]);
            description.setText(rDescription[position]);
            author.setText(rAuthor[position]);

            return row;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(Sub_SearchBook.this, Act_Menu.class);
        intent.putExtra("status","2");
        startActivity(intent);

        return true;
    }
}
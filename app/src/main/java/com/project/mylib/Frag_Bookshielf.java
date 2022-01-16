package com.project.mylib;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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

public class Frag_Bookshielf extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static SharedPreferences booklist, BookTitle, BookAuthor, BookDescription, BookCode;
    String ChoosenSearch;

    private String mParam1;
    private String mParam2;

    ListView listView;


    public Frag_Bookshielf() {
        // Required empty public constructor
    }

    public static Frag_Bookshielf newInstance(String param1, String param2) {
        Frag_Bookshielf fragment = new Frag_Bookshielf();
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
        View view = inflater.inflate(R.layout.frag_bookshielf, container, false);
        Context context = view.getContext();

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
            mAuthor[i-1] = "Tác giả: " +  BookAuthor.getString(key, "null");
            mDescription[i-1] = BookDescription.getString(key,"null");
            String code = BookCode.getString(key,"null");

            String IP = "http://"+ getString(R.string.ip) + "/upload2/"+code+".jpg";
            mImg[i-1] = IP;
        }

        // TODO: Set searchlist
        {
            Spinner searchlist = view.findViewById(R.id.searchlist);
            ArrayList<String> SearchList = new ArrayList<>();
            SearchList.add("Tên sách");
            SearchList.add("Tác giả");
            SearchList.add("Thể loại");
            SearchList.add("Mã sách");

            ArrayAdapter<String> SearchlistAdapter = new ArrayAdapter<>(
                    context, android.R.layout.simple_spinner_dropdown_item, SearchList
            );

            searchlist.setAdapter(SearchlistAdapter);

            searchlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ChoosenSearch = SearchList.get(i);

                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        EditText edtBookFind = view.findViewById(R.id.edtBookFind);

        // TODO: set Search button
        {
            Button btnSearch = view.findViewById(R.id.btnSearch);
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String info = edtBookFind.getText().toString();

                    Intent intent = new Intent(context, Sub_SearchBook.class);
                    intent.putExtra("key",ChoosenSearch);
                    intent.putExtra("info",info);
                    startActivity(intent);

                }
            });
        }


        // TODO: Set Add button
        {
            Button btnAddBook = view.findViewById(R.id.btnAddBook);
            btnAddBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Sub_NewBook.class);
                    startActivity(intent);
                }
            });
        }

        listView = view.findViewById(R.id.listview);
        /// TODO: Set Book Click
        {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if(i==NumberOfBook)
                        return;

                    Intent intent = new Intent(view.getContext(), Sub_ViewBook.class);
                    intent.putExtra("key", i+1);
                    startActivity(intent);
                }
            });
        }



        Adapter adapter = new Adapter(view.getContext(), mTitle, mAuthor, mDescription, mImg);
        listView.setAdapter(adapter);





        return view;
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


}
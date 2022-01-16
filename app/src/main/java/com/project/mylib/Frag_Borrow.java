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

public class Frag_Borrow extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String ChoosenSearch;

    private String mParam1;
    private String mParam2;
    int dem;

    ListView listView;


    public Frag_Borrow() {
        // Required empty public constructor
    }

    public static Frag_Borrow newInstance(String param1, String param2) {
        Frag_Borrow fragment = new Frag_Borrow();
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
        View view = inflater.inflate(R.layout.frag_borrow, container, false);
        Context context = view.getContext();
        SharedPreferences borrowlist, BorrowCode, BorrowBook;

        borrowlist = context.getSharedPreferences("borrowlist", context.MODE_PRIVATE);
        BorrowBook = context.getSharedPreferences("BorrowBook", context.MODE_PRIVATE); /// book's code
        BorrowCode = context.getSharedPreferences("BorrowCode", context.MODE_PRIVATE);

        int NumberOfBorrow = borrowlist.getInt("NumberOfBorrow", 0);

        dem = 0;
        for(int i=1; i<=NumberOfBorrow; i++){
            String key = Integer.toString(i);
            String temp = BorrowCode.getString(key,"null");
            System.out.println(temp + i);
            if(!temp.equals("null")) ++dem;
        }
        System.out.println(dem);

        String mBook[] = new String[dem+1];
        String mBorrow[] = new String[dem+1];
        int mBorrowReal[] = new int[dem+1];

        int j = 0;

        for(int i=1; i<=NumberOfBorrow; i++){
            String key = Integer.toString(i);
            String temp = BorrowCode.getString(key,"null");
            if(temp.equals("null")) continue;

            mBook[j] = BorrowBook.getString(key,"null");
            mBorrow[j] = BorrowCode.getString(key, "null");
            mBorrowReal[j] = i;
            j++;
        }

        // TODO: Set searchlist
        {
            Spinner searchlist = view.findViewById(R.id.searchlist1);
            ArrayList<String> SearchList = new ArrayList<>();
            SearchList.add("Mã mượn");
            SearchList.add("Tên sách");
            SearchList.add("Tên người mượn");
            SearchList.add("Số điện thoại");

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




        // TODO: set Search button
        {
            EditText edtBorrowFind = view.findViewById(R.id.edtBorrowFind);
            Button btnSearch = view.findViewById(R.id.btnSearchBorrow);
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String info = edtBorrowFind.getText().toString();
                    Intent intent = new Intent(context, Sub_SearchBorrow.class);
                    intent.putExtra("key",ChoosenSearch);
                    intent.putExtra("info",info);
                    startActivity(intent);

                }
            });
        }



        // TODO: Set Add button
        {
            Button btnAddBorrow = view.findViewById(R.id.btnAddBorrow);
            btnAddBorrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Sub_NewBorrow.class);
                    intent.putExtra("key",0);
                    startActivity(intent);
                }
            });
        }



        listView = view.findViewById(R.id.listview);
        Adapter adapter = new Adapter(view.getContext(), mBook, mBorrow);
        listView.setAdapter(adapter);

        /// TODO: Set borrow Click
        {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if(i==dem)
                        return;
                    Intent intent = new Intent(view.getContext(), Sub_ViewBorrow.class);
                    intent.putExtra("key", mBorrowReal[i]);
                    startActivity(intent);
                }
            });
        }



        return view;
    }

    class Adapter extends ArrayAdapter<String> {
        Context context;
        String rBook[];
        String rBorrow[];


        Adapter (Context c, String book[],String borrow[]){
            super(c, R.layout.item_borrow, R.id.txtBorrowCode, borrow);
            this.context = c;
            this.rBook = book;
            this.rBorrow = borrow;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View bi = layoutInflater.inflate(R.layout.item_borrow, parent, false);

            ImageView image = bi.findViewById(R.id.imgBorrowBook);
            TextView title = bi.findViewById(R.id.txtBorrowTitle);
            TextView borrower = bi.findViewById(R.id.txtBorrowPerson);
            TextView date = bi.findViewById(R.id.txtBorrowDate);
            TextView code = bi.findViewById(R.id.txtBorrowCode);


            SharedPreferences abc;
            abc = getContext().getSharedPreferences("booklist",getContext().MODE_PRIVATE);
            int Num1 = abc.getInt("NumberOfBook",0);

            for(int i=1; i<=Num1; i++){
                SharedPreferences BookTitle = getContext().getSharedPreferences("BookTitle",getContext().MODE_PRIVATE);
                SharedPreferences BookCode = getContext().getSharedPreferences("BookCode",getContext().MODE_PRIVATE);
                String TitleTemp = BookTitle.getString(Integer.toString(i), "null");
                String CodeTemp = BookCode.getString(Integer.toString(i), "null");
                if(CodeTemp.equals(rBook[position])){
                    title.setText(TitleTemp);
                }
            }

            abc = getContext().getSharedPreferences("borrowlist",getContext().MODE_PRIVATE);
            int Num2 = abc.getInt("NumberOfBorrow",0);

            System.out.println(Num2) ;
            if(position == dem){
                borrower.setText("");
                date.setText("");
                code.setText("");
                title.setText("");
                return bi;
            }



            for(int i=1; i<=Num2; i++){
                SharedPreferences BorrowPerson = getContext().getSharedPreferences("BorrowPerson",getContext().MODE_PRIVATE);
                SharedPreferences BorrowDate = getContext().getSharedPreferences("BorrowDate",getContext().MODE_PRIVATE);
                SharedPreferences BorrowCode = getContext().getSharedPreferences("BorrowCode",getContext().MODE_PRIVATE);
                String PersonTemp = BorrowPerson.getString(Integer.toString(i), "null");
                String DateTemp = BorrowDate.getString(Integer.toString(i), "null");
                String CodeTemp = BorrowCode.getString(Integer.toString(i), "null");

                if(CodeTemp.equals(rBorrow[position])){
                    borrower.setText("Người mượn: " + PersonTemp);
                    date.setText("Ngày mượn: " + DateTemp);
                    code.setText(CodeTemp);

                }
            }
            String IP = "http://"+ getString(R.string.ip) + "/upload2/"+rBook[position]+".jpg";
            Glide.with(bi)
                    .asBitmap()
                    .load(IP)
                    .into(image);

            return bi;
        }
    }


}
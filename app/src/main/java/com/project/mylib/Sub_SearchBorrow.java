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

public class Sub_SearchBorrow extends AppCompatActivity {

    EditText edtInfo;
    Spinner spSearch;
    Button btnSearch;
    ListView lvAnswer;
    String ChoosenSearch;
    int dem;

    SharedPreferences borrowlist, BorrowCode, BorrowBook;

    void init(){
        edtInfo = findViewById(R.id.edtInfo);
        spSearch = findViewById(R.id.spSearch);
        btnSearch = findViewById(R.id.btnSearch);
        lvAnswer = findViewById(R.id.lvAnswer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_searchborrow);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tìm lượt mượn");

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        String key = intent.getStringExtra("key");

        init();

        borrowlist = getSharedPreferences("borrowlist", MODE_PRIVATE);
        BorrowBook = getSharedPreferences("BorrowBook", MODE_PRIVATE); /// book's code - not book order
        BorrowCode = getSharedPreferences("BorrowCode", MODE_PRIVATE);

        int NumberOfBorrow = borrowlist.getInt("NumberOfBorrow", 0);

        edtInfo.setHint(info);

        // TODO: Set searchlist
        {
            ArrayList<String> SearchList = new ArrayList<>();
            SearchList.add("Mã mượn");
            SearchList.add("Tên sách");
            SearchList.add("Tên người mượn");
            SearchList.add("Số điện thoại");

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
                    Intent intent = new Intent(Sub_SearchBorrow.this, Sub_SearchBorrow.class);
                    intent.putExtra("info", info);
                    intent.putExtra("key", ChoosenSearch);
                    startActivity(intent);
                }
            });

        }


        // TODO: set initial answer
        {
            SharedPreferences borrowlist = getSharedPreferences("borrowlist", this.MODE_PRIVATE);
            int num = borrowlist.getInt("NumberOfBorrow", 0);

            boolean ok[] = new boolean[num + 2];
            for (int i = 1; i <= num; i++)
                ok[i] = false;

            System.out.println("Info:" + info);
            System.out.println("\n");
            System.out.println("Key:" + key + "\n");

            SharedPreferences BorrowCode = getSharedPreferences("BorrowCode", MODE_PRIVATE);
            SharedPreferences BorrowBook = getSharedPreferences("BorrowBook", MODE_PRIVATE);

            SharedPreferences BorrowTitle = getSharedPreferences("BorrowTitle", MODE_PRIVATE);
            SharedPreferences BorrowPerson = getSharedPreferences("BorrowPerson", MODE_PRIVATE);
            SharedPreferences BorrowTel = getSharedPreferences("BorrowTel", MODE_PRIVATE);

            dem = 0;
            for (int i = 1; i <= num; i++) {
                String number = Integer.toString(i);

                String code = BorrowCode.getString(number, "null");
                String title = BorrowTitle.getString(number, "null");
                String name = BorrowPerson.getString(number, "null");
                String tel = BorrowTel.getString(number, "null");

                if (code.equals("null"))
                    continue;

                if (key.equals("Mã mượn"))
                    if (code.equals(info)) {
                        ++dem;
                        ok[i] = true;
                    }

                if (key.equals("Tên sách"))
                    if (title.equals(info)) {
                        ++dem;
                        ok[i] = true;
                    }

                if (key.equals("Tên người mượn"))
                    if (name.equals(info)) {
                        ++dem;
                        ok[i] = true;
                    }

                if (key.equals("Số điện thoại"))
                    if (tel.equals(info)) {
                        ++dem;
                        ok[i] = true;
                    }
            }

            String mBook[] = new String[dem + 1];
            String mBorrow[] = new String[dem + 1];
            int mBorrowReal[] = new int[dem + 1];


            int j = 0;
            for (int i = 1; i <= NumberOfBorrow; i++) {
                String number = Integer.toString(i);
                if (ok[i] == false) continue;
                mBook[j] = BorrowBook.getString(number, "null");
                mBorrow[j] = BorrowCode.getString(number, "null");
                mBorrowReal[j] = i;
                j++;
            }


            Sub_SearchBorrow.Adapter adapter = new Adapter(this, mBook, mBorrow);
            lvAnswer.setAdapter(adapter);

            lvAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i==dem) return;
                    Intent intent = new Intent(view.getContext(), Sub_ViewBorrow.class);
                    intent.putExtra("key", mBorrowReal[i]);
                    startActivity(intent);
                }
            });
        }
    }


    // todo: set adapter
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
                    borrower.setText("Người mượn: " +  PersonTemp);
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(Sub_SearchBorrow.this, Act_Menu.class);
        intent.putExtra("status","1");
        startActivity(intent);

        return true;
    }
}
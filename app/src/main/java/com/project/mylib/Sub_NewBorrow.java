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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Sub_NewBorrow extends AppCompatActivity {

    /// todo: thong tin sach
    TextView txtTitle, txtAuthor, txtCode, txtStatus;
    EditText edtNumber;

    /// todo: thong tin nguoi muon
    EditText edtBorrowPerson, edtBorrowAddress, edtBorrowTel, edtBorrowDate, edtBorrowDate2;
    TextView txtLibrarian2, txtWarningAddBorrow;

    ImageView btnSave, imgBook;
    Button btnChooseBook;

    String mTitle, mAuthor, mBorrowing, mCode;
    String Librarian;
    int key;
    String Available;


    void init(){
        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtCode = findViewById(R.id.txtCode);
        txtStatus = findViewById(R.id.txtStatus);
        edtNumber = findViewById(R.id.edtNumber);

        edtBorrowPerson = findViewById(R.id.edtBorrowPerson);
        edtBorrowAddress = findViewById(R.id.edtBorrowAddress);
        edtBorrowTel = findViewById(R.id.edtBorrowTel);
        edtBorrowDate = findViewById(R.id.edtBorrowDate);
        edtBorrowDate2 = findViewById(R.id.edtBorrowDate2);
        txtLibrarian2 = findViewById(R.id.txtLibrarian2);
        txtWarningAddBorrow = findViewById(R.id.txtWarningAddBorrow);

        btnSave = findViewById(R.id.btnSave1);
        imgBook = findViewById(R.id.imgBook);
        btnChooseBook = findViewById(R.id.btnChooseBook);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_newborrow);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm lượt mượn");
        Context context = this;

        init();

        // todo: set default value
        {
            Intent intent = getIntent();
            int key2;
            key2 = intent.getIntExtra("key",0);
            key = key2;
            if(key2 != 0){
                SharedPreferences abc;
                String Key = Integer.toString(key2);
                abc = getSharedPreferences("BookTitle", this.MODE_PRIVATE);
                mTitle = abc.getString(Key,"null");

                abc = getSharedPreferences("BookAuthor", this.MODE_PRIVATE);
                mAuthor = abc.getString(Key, "null");

                abc = getSharedPreferences("BookBorrowing", this.MODE_PRIVATE);
                mBorrowing = abc.getString(Key, "null");

                abc = getSharedPreferences("BookNumber", this.MODE_PRIVATE);
                String Number = abc.getString(Key, "null");

                int all = Integer.valueOf(Number);
                int borrow = Integer.valueOf(mBorrowing);
                int available = all - borrow;
                Available = Integer.toString(available);

                abc = getSharedPreferences("BookCode", this.MODE_PRIVATE);
                mCode = abc.getString(Key,"null");

                String IP = "http://"+ getString(R.string.ip) + "/upload2/"+mCode+".jpg";
                String mImgLink = IP;


                abc = getSharedPreferences("BookLocation", this.MODE_PRIVATE);
                String Location = abc.getString(Key,null);


                txtTitle.setText(mTitle);
                txtAuthor.setText("Tác giả: " + mAuthor);
                txtCode.setText("Mã sách:" + mCode);
                txtStatus.setVisibility(View.VISIBLE);
                txtStatus.setText("Còn lại " + Available + " cuốn ở " + Location);

                Glide.with(context)
                        .asBitmap()
                        .load(mImgLink)
                        .into(imgBook);
            }

            SharedPreferences abc = getSharedPreferences("login", this.MODE_PRIVATE);
            Librarian = abc.getString("uname","null");
            txtLibrarian2.setText(Librarian);
        }


        // todo: Set Choose button
        btnChooseBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sub_NewBorrow.this, Opt_ChooseBook.class);
                startActivity(intent);
            }
        });


        // todo: Set Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Number = edtNumber.getText().toString();
                String Person = edtBorrowPerson.getText().toString();
                String Address = edtBorrowAddress.getText().toString();
                String Tel = edtBorrowTel.getText().toString();
                String Date = edtBorrowDate.getText().toString();
                String Date2 = edtBorrowDate2.getText().toString();

                boolean checkBook = true, checkInfo = true, checkTel = true, checkDate = true, checkNum = true;

                if(key == 0) checkBook = false;

                if(checkBook == false){
                    txtWarningAddBorrow.setVisibility(View.VISIBLE);
                    txtWarningAddBorrow.setText("Bạn chưa chọn sách");
                    return;
                }

                if(Number.equals("")) checkInfo = false;
                if(Person.equals("")) checkInfo = false;
                if(Address.equals("")) checkInfo = false;
                //if(Tel.equals("")) checkInfo = false;
                if(Date.equals("")) checkInfo = false;
                if(Date2.equals("")) checkInfo = false;
                if(checkInfo == false){
                    txtWarningAddBorrow.setVisibility(View.VISIBLE);
                    txtWarningAddBorrow.setText("Vui lòng điền đầy đủ các thông tin");
                    return;
                }


                if(Tel.length() != 10)
                    checkTel = false;

                if(!Tel.equals("") && Tel.charAt(0) != '0')
                    checkTel = false;

                char c = (char)47;
                System.out.println(c);

                if(Date.length() != 10 || Date2.length()!=10){
                    txtWarningAddBorrow.setVisibility(View.VISIBLE);
                    System.out.println(Date.charAt(2) + " " + Date2.charAt(5));
                    txtWarningAddBorrow.setText("Ngày phải ở định dạng dd/mm/yyyy");
                    return;
                }

                if(Date.charAt(2) != c)
                    checkDate = false;
                if(Date2.charAt(2) != c)
                    checkDate = false;
                if(Date.charAt(5) != c)
                    checkDate = false;
                if(Date2.charAt(5) != c)
                    checkDate = false;

                /*
                if(checkTel == false){
                    txtWarningAddBorrow.setVisibility(View.VISIBLE);
                    txtWarningAddBorrow.setText("Số điện thoại không hợp lệ");
                    return;
                }
                 */

                if(checkDate == false){
                    txtWarningAddBorrow.setVisibility(View.VISIBLE);
                    txtWarningAddBorrow.setText("Ngày phải ở định dạng dd/mm/yyyy");
                    return;
                }

                int bNum = Integer.valueOf(Number);
                int bAvail = Integer.valueOf(Available);
                System.out.println(bNum + " " + bAvail);
                if(bNum > bAvail){
                    txtWarningAddBorrow.setVisibility(View.VISIBLE);
                    txtWarningAddBorrow.setText("Không đủ số sách theo yêu cầu");
                    return;
                }

                SharedPreferences borrowlist, BorrowBook, BorrowTitle, BorrowPerson, BorrowCode, BorrowDate, BorrowDate2, BorrowNumber, BorrowAddress, BorrowTel, BorrowLibrarian;
                SharedPreferences BookBorrowing;

                Context context = Sub_NewBorrow.this;
                borrowlist = context.getSharedPreferences("borrowlist", context.MODE_PRIVATE);

                BorrowTitle = context.getSharedPreferences("BorrowTitle", context.MODE_PRIVATE);
                BorrowBook = context.getSharedPreferences("BorrowBook", context.MODE_PRIVATE);
                BorrowPerson = context.getSharedPreferences("BorrowPerson", context.MODE_PRIVATE);
                BorrowCode = context.getSharedPreferences("BorrowCode", context.MODE_PRIVATE);
                BorrowDate = context.getSharedPreferences("BorrowDate", context.MODE_PRIVATE);
                BorrowDate2 = context.getSharedPreferences("BorrowDate2", context.MODE_PRIVATE);
                BorrowNumber = context.getSharedPreferences("BorrowNumber", context.MODE_PRIVATE);
                BorrowAddress = context.getSharedPreferences("BorrowAddress", context.MODE_PRIVATE);
                BorrowTel = context.getSharedPreferences("BorrowTel", context.MODE_PRIVATE);
                BorrowLibrarian = context.getSharedPreferences("BorrowLibrarian", context.MODE_PRIVATE);

                BookBorrowing = context.getSharedPreferences("BookBorrowing", context.MODE_PRIVATE);

                int AllNumber = borrowlist.getInt("NumberOfBorrow", 0);
                SharedPreferences.Editor editor;

                String key = Integer.toString(AllNumber + 1);
                System.out.println(key);

                editor = borrowlist.edit();
                editor.putInt("NumberOfBorrow", AllNumber + 1);
                editor.commit();


                editor = BorrowTitle.edit();
                editor.putString(key, mTitle);
                editor.commit();

                editor = BorrowBook.edit();
                editor.putString(key, mCode);
                editor.commit();

                editor = BorrowPerson.edit();
                editor.putString(key, Person);
                editor.commit();

                editor = BorrowDate.edit();
                editor.putString(key, Date);
                editor.commit();

                editor = BorrowDate2.edit();
                editor.putString(key, Date2);
                editor.commit();

                editor = BorrowCode.edit();
                editor.putString(key, "BC" + "_" +  mCode + "_" + Tel + "_" +Date);
                editor.commit();

                editor = BorrowNumber.edit();
                editor.putString(key, Number);
                editor.commit();

                editor = BorrowAddress.edit();
                editor.putString(key, Address);
                editor.commit();

                editor = BorrowTel.edit();
                if(Tel.equals(""))
                    Tel = "null";
                editor.putString(key, Tel);
                editor.commit();

                editor = BorrowLibrarian.edit();
                editor.putString(key, Librarian);
                editor.commit();

                String realcode = "null";

                SharedPreferences booklist = getSharedPreferences("booklist", context.MODE_PRIVATE);
                int Num = booklist.getInt("NumberOfBook", 0);

                for(int i=1; i<=Num; i++){
                    SharedPreferences BookCode = getSharedPreferences("BookCode", context.MODE_PRIVATE);
                    String Code = BookCode.getString(Integer.toString(i), "null");
                    System.out.println(i + " abc " + Code);
                    if(Code.equals(mCode))
                        realcode = Integer.toString(i);
                }

                System.out.println("this\n");
                System.out.println(mCode + "\n");
                System.out.println(realcode);



                String NumberBorrowing = BookBorrowing.getString(realcode, "0");
                //todo look at here
                int nBorrowing = Integer.valueOf(NumberBorrowing) + Integer.valueOf(Number);

                editor = BookBorrowing.edit();
                editor.putString(realcode, Integer.toString(nBorrowing));
                editor.commit();


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Sub_NewBorrow.this, Act_Menu.class);
                intent.putExtra("status","1");
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(Sub_NewBorrow.this, Act_Menu.class);
        intent.putExtra("status","1");
        startActivity(intent);

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Sub_NewBorrow.this, Act_Menu.class);
        intent.putExtra("status","1");
        startActivity(intent);
    }
}
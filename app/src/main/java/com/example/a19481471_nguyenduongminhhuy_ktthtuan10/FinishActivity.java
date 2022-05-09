package com.example.a19481471_nguyenduongminhhuy_ktthtuan10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        EditText etID = findViewById(R.id.edtid);
        EditText etname = findViewById(R.id.edtname);
        EditText edtadd = findViewById(R.id.edtadd);
        EditText edtemail = findViewById(R.id.edtemail);
        GridView gridView = findViewById(R.id.gr);
        DBHelper dbHelper = new DBHelper(this);

        Button btnSave = findViewById(R.id.btnsave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Author author = new Author(Integer.parseInt(etID.getText().toString()),
                        etname.getText().toString(),edtadd.getText().toString(),edtemail.getText().toString());
                if(dbHelper.insertAuthor(author)>0)
                    Toast.makeText(getApplicationContext(),"Ban da luu thanh cong",Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(),"Ban da luu khong thanh cong",Toast.LENGTH_SHORT).show();
            }
        });

        Button btnselect = findViewById(R.id.btnselect);
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Author> list = new ArrayList<>();
                ArrayList<String> list_String = new ArrayList<>();
                if (etID.getText().toString().equals("")){
                    list = dbHelper.getALL();
                } else {
                    try {
                        Author at = dbHelper.getIdAuthor(Integer.parseInt(etID.getText().toString().trim()));
                        list.add(at);
                    } catch (Exception e){
                        list.clear();
                        Toast.makeText(FinishActivity.this,"Khong tim thay",Toast.LENGTH_SHORT).show();
                    }

                }
                for (Author a: list) {
                    list_String.add(a.getId()+"");
                    list_String.add(a.getName());
                    list_String.add(a.getAddress());
                    list_String.add(a.getEmail());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(FinishActivity.this,
                        android.R.layout.simple_list_item_1,list_String);
                gridView.setAdapter(adapter);
            }
        });
    }
}
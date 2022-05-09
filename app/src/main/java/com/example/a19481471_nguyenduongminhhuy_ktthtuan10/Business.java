package com.example.a19481471_nguyenduongminhhuy_ktthtuan10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Business extends AppCompatActivity {
    EditText edit_id,edit_name,edit_trangThai;
    Button btn_submit,btn_update,btn_all,btn_delete,btn_search;
    BussinesDAO bussinesDAO;
    DatabaseReference databaseReference;
    ListView listView;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        edit_id = findViewById(R.id.edit_id);
        edit_name = findViewById(R.id.edit_name);
        edit_trangThai = findViewById(R.id.edit_trangThai);
        btn_submit = findViewById(R.id.btn_submit);
        btn_update = findViewById(R.id.btn_update);
        btn_all = findViewById(R.id.btn_all);
        btn_delete = findViewById(R.id.btn_delete);
        btn_search = findViewById(R.id.btn_search);
        bussinesDAO = new BussinesDAO();
        list = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        btn_submit.setOnClickListener(view -> {
            bussinesDAO.add(new BussinesEntity(Integer.parseInt(edit_id.getText().toString()),
                    edit_name.getText().toString(),
                    edit_trangThai.getText().toString()))
                    .addOnSuccessListener(suc -> {
                        Toast.makeText(this,"complete add record",Toast.LENGTH_LONG).show();
                    })
                    .addOnFailureListener(fai -> {
                        Toast.makeText(this,""+fai.getMessage(),Toast.LENGTH_LONG).show();
                    });
            btn_all.callOnClick();
        });

        btn_all.setOnClickListener(view -> {
            databaseReference = (DatabaseReference) bussinesDAO.get();
            databaseReference.child(currentUser.getUid()).addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for(DataSnapshot dt : snapshot.getChildren()){
                        BussinesEntity e = dt.getValue(BussinesEntity.class);
                        list.add(e.getId() + " - ten: " +e.getCongViec() + " - trang thai: " +e.getTrangThai());
                    }
                    change(list);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        btn_delete.setOnClickListener(view -> {
            bussinesDAO.remove(edit_id.getText().toString());
            btn_all.callOnClick();
        });

        btn_search.setOnClickListener(view -> {
            String key = edit_id.getText().toString();
            databaseReference = (DatabaseReference) bussinesDAO.get();
            databaseReference.child(currentUser.getUid()).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    BussinesEntity e = snapshot.getValue(BussinesEntity.class);
                    edit_id.setText(String.valueOf(e.getId()));
                    edit_name.setText(e.getCongViec());
                    edit_trangThai.setText(e.getTrangThai());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
//
            btn_all.callOnClick();
        });

        btn_update.setOnClickListener(view ->{
            String congViec = edit_name.getText().toString();
            String trangThai = edit_trangThai.getText().toString();
            String key = edit_id.getText().toString();
            if(TextUtils.isEmpty(congViec)){
                edit_name.setError("empty");
                edit_name.requestFocus();
            }else if(TextUtils.isEmpty(trangThai)) {
                edit_trangThai.setError("empty");
                edit_trangThai.requestFocus();
            }else if(TextUtils.isEmpty(key)) {
                edit_trangThai.setError("empty");
                edit_trangThai.requestFocus();
            }else {
                HashMap<String,Object> map = new HashMap<>();
                map.put("id",Integer.parseInt(key));
                map.put("congViec",congViec);
                map.put("trangThai",trangThai);
                bussinesDAO.update(key,map);

                btn_all.callOnClick();
            }
        });
    }

    public void change(List list){
        listView = findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<String>(Business.this,
                android.R.layout.simple_list_item_1,list );
        for (int i = 0;i < list.size();i++){
            System.out.print(list.get(i));
        }
        System.out.print(1);
        listView.setAdapter(adapter);
    }
}
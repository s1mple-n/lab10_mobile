package com.example.a19481471_nguyenduongminhhuy_ktthtuan10;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class BussinesDAO {
    private DatabaseReference databaseReference;
    private String user;
    public BussinesDAO()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        user = currentUser.getUid();
        databaseReference = db.getReference(BussinesEntity.class.getSimpleName());
    }

    public Task<Void> add(BussinesEntity emp)
    {
        return databaseReference.child(user).child(String.valueOf(emp.getId())).setValue(emp);
    }

    public Task<Void> update(String key, HashMap<String ,Object> hashMap)
    {
        return databaseReference.child(user).child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key)
    {
        return databaseReference.child(user).child(key).removeValue();
    }


    public Query get(String key)
    {
        if(key == null)
        {
            return databaseReference.orderByKey().limitToFirst(8);
        }
        return databaseReference.orderByKey().startAfter(key).limitToFirst(8);
    }

    public Query get()
    {
        return databaseReference;
    }

}

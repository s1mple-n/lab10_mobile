package com.example.a19481471_nguyenduongminhhuy_ktthtuan10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "MyDB2", null, 1);
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table Authors(id integer primary key, name text, address text, email text);");
        sqLiteDatabase.execSQL("Create table Books(id integer primary key, title text, id_author integer not null constraint id_author references Authors(id) on delete cascade on update cascade );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists Authors");
        sqLiteDatabase.execSQL("Drop table if exists Books");
        onCreate(sqLiteDatabase);
    }
    //them
    public int insertAuthor(Author author){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("id",author.getId());
        content.put("name",author.getName());
        content.put("address",author.getAddress());
        content.put("email",author.getEmail());
        int res = (int) db.insert("Authors", null,content);
        db.close();
        return res;
    }
    //lay toan bo list author
    public ArrayList<Author> getALL(){
        ArrayList<Author> list = new ArrayList<>();
        String strSQL = "Select * from Authors";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(strSQL,null);
        if(cursor!=null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                list.add(new Author(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3)));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    public Author getIdAuthor(int id){
        Author author = new Author();
        String strSQL = "Select * from Authors where id ="+id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(strSQL,null);
        if(cursor!=null){

            cursor.moveToFirst();

            author.setId(cursor.getInt(0));
            author.setName(cursor.getString(1));
            author.setAddress(cursor.getString(2));
            author.setEmail(cursor.getString(3));

            cursor.close();
            db.close();
        }

        return author;
    }


}

package com.example.a11_18_2app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues mValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        Button createDB_btn = findViewById(R.id.creatDB_btn);
        createDB_btn.setOnClickListener(this);
        Button addData_btn = findViewById(R.id.addData_btn);
        addData_btn.setOnClickListener(this);
        Button updateData_btn = findViewById(R.id.updateData_btn);
        updateData_btn.setOnClickListener(this);
        Button deleteData_btn = findViewById(R.id.deleteData_btn);
        deleteData_btn.setOnClickListener(this);
        Button queryData_btn = findViewById(R.id.queryData_btn);
        queryData_btn.setOnClickListener(this);

        dbHelper=new MyDatabaseHelper(this,"sqltest.db",null,1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.creatDB_btn:
                dbHelper.getWritableDatabase();
                break;
            case R.id.addData_btn:
                db=dbHelper.getWritableDatabase();
                mValues=new ContentValues();
                mValues.put("name","The Da 3502");
                mValues.put("author","wl");
                mValues.put("pages","550");
                mValues.put("price","15.6");
                db.insert("Book","null",mValues);
                Log.d("addData_btn","addData_btn");
                mValues.clear();
                break;
            case R.id.updateData_btn:
                db=dbHelper.getWritableDatabase();
                mValues=new ContentValues();
                mValues.put("price",9.99);
                db.update("Book",mValues,"name = ?",new  String[]{"The Da 3502"});
                Log.d("updateData_btn","updateData_btn");
                mValues.clear();
                break;
            case R.id.deleteData_btn:
                db=dbHelper.getWritableDatabase();
                db.delete("Book","pages > ?",new String[]{"500"});
                Log.d("deleteData_btn","deleteData_btn");
                break;
            case  R.id.queryData_btn:
                db=dbHelper.getWritableDatabase();
                Cursor cursor=db.query("Book",null,null,null,null
                ,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String name =cursor.getString(cursor.getColumnIndex("name"));
                        String author =cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price =cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("SQLiteActivity","《"+name+"》,the author is "+author+"all："+pages+"页,售价："+price);
                    }while (cursor.moveToNext());
                }
                cursor.close();
        }

    }
}

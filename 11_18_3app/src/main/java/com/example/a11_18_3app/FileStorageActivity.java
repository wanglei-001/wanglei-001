package com.example.a11_18_3app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileStorageActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText myEdit1;
    private EditText myEdit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filestorage);
        myEdit1 = findViewById(R.id.my_edit1);
        myEdit2 = findViewById(R.id.my_edit2);
        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);
        Button loadBtn = findViewById(R.id.load_btn);
        loadBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_btn:
                String inputData = myEdit1.getText().toString();
                saveDataToFile(inputData);
                break;
            case R.id.load_btn:
                String getData = getDataFromFile();
                if (!TextUtils.isEmpty(getData)) {
                    myEdit2.setText(getData);
                    myEdit2.setSelection(getData.length());
                    Log.d("FileStorageActivity", "Restoring successed!");
                }
                break;
        }
    }
    /* 将数据存储至文件*/
    public void saveDataToFile(String inputText){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("MyDataFile.txt", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
            Log.d("FileStorageActivity", "Test write OK.......");
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* 从文件中读取数据*/
    public String getDataFromFile() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("MyDataFile.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}

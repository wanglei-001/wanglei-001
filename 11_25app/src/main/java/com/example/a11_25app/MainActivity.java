package com.example.a11_25app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView myText;
    private final int UPDATE_TEXT=1;
    private final int CHANG_BG=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        myText = findViewById(R.id.mytext);
        Button change_text = findViewById(R.id.change_text);
        change_text.setOnClickListener(this);
        Button change_bd = findViewById(R.id.change_bg);
        change_bd.setOnClickListener(this);
    }

   @SuppressLint("HandlerLeak")
   private Handler handler=new Handler() {
       @Override
       public void handleMessage(@NonNull Message msg) {
           switch (msg.what) {
               case UPDATE_TEXT:
                   myText.setText("Nice to meet you");
                   break;
               case CHANG_BG:
                   myText.setTextColor(Color.parseColor("#0000FF"));
           }
       }
   };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        message.what=UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.change_bg:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message =new Message();
                        message.what=CHANG_BG;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
        }
    }


}

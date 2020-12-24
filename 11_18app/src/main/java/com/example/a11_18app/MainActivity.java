package com.example.a11_18app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pres);

        Button save_data=findViewById(R.id.save_data);
        save_data.setOnClickListener(this);
        Button restore_data=findViewById(R.id.restore_data);
        restore_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_data:
                SharedPreferences sharedPreferences=getSharedPreferences("MySharePres",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("Name","WL");
                editor.putInt("Number",20201118);

                editor.apply();
                Log.d("SharedPresActivity","save data……");
                break;
            case  R.id.restore_data:
                SharedPreferences pref=getSharedPreferences("MySharePres",MODE_PRIVATE);
                String name =pref.getString("Name","");
                int number=pref.getInt("Number",0);
                Log.d("SharedPresActivity","This name is" + name + "and the number is" + number);
                break;

        }
    }
}

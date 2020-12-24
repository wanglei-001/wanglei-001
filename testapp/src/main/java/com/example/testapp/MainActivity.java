package com.example.testapp;


import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MySurfaceView mySurfaceView;
    private TextView teNowTiem;
    private SeekBar sb;
    private TextView teAllTiem;
    private Button butPlay;
    private Timer timer;
    private Handler handler = new Handler();
    private static String path = "/storage/emulated/0/Music/temp.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },100);
        }
        initView();
        initTimer();

    }

    private void initTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                final String currentTime = mySurfaceView.getCurrentTime();
                final String duration = mySurfaceView.getDuration();
                final int progress = mySurfaceView.getProgress();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        sb.setProgress(progress);//设置进度条
                        teAllTiem.setText(duration);//设置总时长
                        teNowTiem.setText(currentTime);//设置当前时长
                    }
                });
            }
        },0,100);
    }

    private void initView() {
        mySurfaceView = (MySurfaceView) findViewById(R.id.sv);
        mySurfaceView.setDataPath(path);
        teNowTiem = (TextView) findViewById(R.id.te_nowTiem);
        sb = (SeekBar) findViewById(R.id.sb);
        teAllTiem = (TextView) findViewById(R.id.te_allTiem);
        butPlay = (Button) findViewById(R.id.but_play);

        //设置拖动
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mySurfaceView.seekTo(progress);//视频播放拖动
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //暂停播放
        butPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySurfaceView.playOrNo();
            }
        });

    }
}
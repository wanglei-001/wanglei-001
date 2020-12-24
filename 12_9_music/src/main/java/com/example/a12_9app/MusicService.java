package com.example.a12_9app;

import android.app.Service;
import android.content.Intent;

import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {
    MediaPlayer player;
    Timer timer;
    public MusicService() {
    }



    public IBinder onBind(Intent intent) {
        return new  MusicControl();
    }

    class MusicControl extends Binder {
        MusicService getService(){
            return  MusicService.this;
        }
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player == null) return;
        if(player.isPlaying()) player.stop();
        player.release();
        player = null;
    }
    public void play(){
        try{
            player.reset();
            player = MediaPlayer.create(getApplicationContext(),R.raw.music);
            player.start();
            addTimer();  //添加计时器
        }catch(Exception e) {
            e.printStackTrace();
        }


    }

    private void addTimer() {
        if(timer == null){
            timer=new Timer();
            TimerTask task =new TimerTask() {
                @Override
                public void run() {
                    if(player == null) return;
                    int duration = player.getDuration();
                    int currentPosition = player.getCurrentPosition();
                    Message msg = MainActivity.handler.obtainMessage();
                    Bundle bundle=new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentPosition",currentPosition);
                    msg.setData(bundle);
                    MainActivity.handler.sendMessage(msg);
                }
            };
            timer.schedule(task,5,500);
        }


    }
    public void pausePlay(){
        player.pause();   //暂停音乐
    }
    public void continuePlay(){
        player.start();   //继续播放
    }
    public  void  seekTo(int progress){
        player.seekTo(progress);  //设置音乐播放位置
    }
}

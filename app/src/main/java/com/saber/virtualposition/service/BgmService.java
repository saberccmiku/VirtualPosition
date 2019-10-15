package com.saber.virtualposition.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.saber.virtualposition.R;
import com.saber.virtualposition.SplashActivity;

public class BgmService extends Service {

    private static Thread uploadGpsThread;
    private boolean isRun = true;
    private MediaPlayer bgMediaPlayer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification notification = new Notification.Builder(this)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setWhen(System.currentTimeMillis())
                    .setTicker("后台测试")
                    .setContentTitle("后台测试标题")
                    .setContentText("后台测试内容")
                    .setOngoing(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(false)
                    .build();
            /*使用startForeground,如果id为0，那么notification将不会显示*/
            startForeground(100, notification);



            //2.开启线程（或者需要定时操作的事情）
            if (uploadGpsThread == null) {
                uploadGpsThread = new Thread(new Runnable() {
                    int count = 0;
                    @Override
                    public void run() {
                        //这里用死循环就是模拟一直执行的操作
                        while (isRun) {

                            //你需要执行的任务
                            System.out.println(count++);

                            try {
                                Thread.sleep(10000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }


        //3.最关键的神来之笔，也是最投机的动作，没办法要骗过CPU
        //这就是播放音乐类APP不被杀的做法，自己找个无声MP3放进来循环播放
        if(bgMediaPlayer == null){
            bgMediaPlayer = MediaPlayer.create(this, R.raw.silence);
            bgMediaPlayer.setLooping(true);
            bgMediaPlayer.start();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        isRun = false;
        stopForeground(true);
        bgMediaPlayer.release();
        stopSelf();
        super.onDestroy();
    }
}

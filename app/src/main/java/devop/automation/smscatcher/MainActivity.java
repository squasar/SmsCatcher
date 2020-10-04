package devop.automation.smscatcher;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class MainActivity extends Activity {
    Intent mServiceIntent;
    private SendingService myService;
    Context ctx;
    private boolean isServiceBlocked;
    private boolean mIsBound;
    public Context getCtx() {
        return ctx;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        isServiceBlocked = false;
        mIsBound = false;
        setContentView(R.layout.activity_main);
        myService = new SendingService(getCtx());
        mServiceIntent = new Intent(getCtx(), myService.getClass());
        startService(mServiceIntent);
        stopService(mServiceIntent);
    }
    @Override
    protected void onDestroy() {
        if(isServiceBlocked){
            if(mIsBound){
                unbindService(myConnection);
                mIsBound = false;
            }
            stopService(mServiceIntent);
        }
        super.onDestroy();
    }
    public void startService(View view) {
        if (!isMyServiceRunning(myService.getClass())) {
            startService(mServiceIntent);
            if(!mIsBound){
                doBindService();
            }
            isServiceBlocked = false;
        }
    }
    public void stopService(View view) {
        if (isMyServiceRunning(myService.getClass())) {
            if(mIsBound){
                mIsBound = false;
            }
            stopService(mServiceIntent);
            isServiceBlocked = true;
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    public ServiceConnection myConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            myService = ((SendingService.SendingBinder) binder).getService();
        }
        public void onServiceDisconnected(ComponentName className) {
            myService = null;
        }
    };
    private void doBindService() {
        mIsBound = true;
    }
}

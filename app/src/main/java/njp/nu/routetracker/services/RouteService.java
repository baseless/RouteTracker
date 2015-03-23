package njp.nu.routetracker.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class RouteService extends Service {

    private final IBinder binder = new RouteServiceBinder();
    String test = "";

    public String test() {
        test += "N";
        return test;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("RouteService", "Service bound");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onRebind(Intent intent) {

    }

    public class RouteServiceBinder extends Binder { //enclosed binder class

        RouteService getService() {
            Log.i("RouteService", "Service constructor ran");
            return RouteService.this;
        }

    }
}

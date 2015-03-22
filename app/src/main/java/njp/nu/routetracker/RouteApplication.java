package njp.nu.routetracker;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RouteApplication extends Application {

    private RouteService routeService;
    private boolean serviceBound = false;
    Timer routeTimer = new Timer();
    private List<LatLng> routeCoordinates; //Coordinate list, used primarily by route fragment - NO add methods here, use mapfragment to add

    public void clearRouteCoordinates() {
        if(routeCoordinates != null)
            routeCoordinates.clear();
    }

    public boolean ServiceIsBound() {
        return serviceBound;
    }

    public void startRoute() {
        clearRouteCoordinates();
        routeTimer.scheduleAtFixedRate(routePulse, 0, 2000);
    }

    public void stopRoute() {
        routeTimer.cancel();
    }

    public List<LatLng> getRouteCoordinates() {
        if(routeCoordinates == null)
            routeCoordinates = new ArrayList<>();
        return routeCoordinates;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent serviceIntent = new Intent(this, RouteService.class); //service
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE); //service
        startRoute();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (serviceBound) { //service
            unbindService(serviceConnection); //service
            serviceBound = false; //service
        } //service
    }

    TimerTask routePulse = new TimerTask() {
        @Override
        public void run() {
            broadcastRoutePulse();
        }
    };

    private void broadcastRoutePulse() {
        //Log.i("", "TIMER RUNNING");
        Intent i = new Intent("com.hmkcode.android.USER_ACTION");
        sendBroadcast(i);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() { //service

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i("serviceConnection", "onServiceConnected");
            RouteService.RouteServiceBinder binder = (RouteService.RouteServiceBinder) service;
            routeService = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            serviceBound = false;
        }
    };
}

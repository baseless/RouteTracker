package njp.nu.routetracker;

import android.app.Application;
import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RouteApplication extends Application {

    Timer routeTimer = new Timer();
    private List<LatLng> routeCoordinates; //Coordinate list, used primarily by route fragment - NO add methods here, use mapfragment to add

    public void clearRouteCoordinates() {
        if(routeCoordinates != null)
            routeCoordinates.clear();
    }

    public void startRoute() {
        clearRouteCoordinates();
        routeTimer.scheduleAtFixedRate(routePulse, 0, 500);
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
        startRoute();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
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
}

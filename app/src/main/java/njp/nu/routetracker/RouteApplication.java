package njp.nu.routetracker;

/**
 * Created by Daniel Ryhle on 2015-03-23.
 */

import android.app.Application;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import njp.nu.routetracker.services.RouteService;

public class RouteApplication extends Application {

    private Timer routeTimer;
    private List<Location> routeLocations;
    private List<LatLng> routeLatLng;
    private RouteService routeService;


    public void clearRouteCoordinates() {
        if(routeLatLng != null)
            routeLatLng.clear();
        if(routeLocations != null)
            routeLocations.clear();
    }

    public void startRoute() {
        clearRouteCoordinates();
        routeTimer = new Timer();
        routeTimer.scheduleAtFixedRate(routePulse, 0, 500);
    }

    public void stopRoute() {
        routeTimer.cancel();
        routeTimer.purge();
    }

    public List<LatLng> getRouteCoordinates() {
        if(routeLatLng == null)
            routeLatLng = new ArrayList<>();
        return routeLatLng;
    }

    public List<Location> getRouteLocations() {
        if(routeLocations == null)
            routeLocations = new ArrayList<>();
        return routeLocations;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        routeService = new RouteService(this, getRouteLocations(), getRouteCoordinates());
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
        Log.i("Broadcast", "Pulse sent");
        routeService.parseCurrentPosition();
        Intent i = new Intent("njp.nu.android.ROUTE_UPDATE");
        sendBroadcast(i);
    }
}

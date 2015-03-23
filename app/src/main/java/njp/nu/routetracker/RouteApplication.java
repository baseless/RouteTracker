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

import njp.nu.routetracker.services.DatabaseService;
import njp.nu.routetracker.services.ScheduledLocationService;

public class RouteApplication extends Application {

    private Timer routeTimer;
    private List<Location> routeLocations;
    private List<LatLng> routeLatLng;
    //private RouteService routeService;
    private DatabaseService dbService;
    private ScheduledLocationService locationService;

    @Override
    public void onCreate() {
        super.onCreate();
        dbService = new DatabaseService(this);
        locationService = new ScheduledLocationService(this, getRouteLocations(), getRouteCoordinates(), dbService);
    }

    public boolean isGpsEnabled() {
        return locationService.isGpsEnabled();
    }

    public boolean isWifiEnabled() {
        return locationService.isWifiEnabled();
    }

    public void clearRouteCoordinates() {
        if(routeLatLng != null)
            routeLatLng.clear();
        if(routeLocations != null)
            routeLocations.clear();
    }

    public void startRoute() {
        Log.i("", "startRoute");
        clearRouteCoordinates();
        locationService.startLocationUpdates();
        routeTimer = new Timer();
        routeTimer.scheduleAtFixedRate(routePulse, 100, 500);
    }

    public void stopRoute() {
        locationService.stopLocationUpdates();
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
        Intent i = new Intent("njp.nu.android.ROUTE_UPDATE");
        sendBroadcast(i);
    }
}

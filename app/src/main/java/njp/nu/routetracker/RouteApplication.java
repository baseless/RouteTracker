package njp.nu.routetracker;

/**
 * Created by Daniel Ryhle on 2015-03-23.
 */

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
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
    private float routeDistance;
    private DatabaseService dbService;
    private ScheduledLocationService locationService;
    private boolean isStarted = false;

    public void updateRouteDistance(float routeDistance) {
        this.routeDistance += routeDistance;
    }

    public float getRouteDistance() {
        return routeDistance;
    }

    public boolean isStarted() {
        return isStarted;
    }

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
        if(!isStarted) {
            Log.i("", "startRoute");
            clearRouteCoordinates();
            routeDistance = 0;
            locationService.startLocationUpdates();
            routeTimer = new Timer();
            routeTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    broadcastRoutePulse();
                }
            }, 100, 1000);
            isStarted = true;
            broadcastWidgetUpdate();
        }
    }

    public void stopRoute() {
        if(isStarted) {
            locationService.stopLocationUpdates();
            routeTimer.cancel();
            routeTimer.purge();
            isStarted = false;
            broadcastWidgetUpdate();
        }
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

    private void broadcastRoutePulse() {
        Log.i("Broadcast", "Pulse sent");
        Intent i = new Intent("njp.nu.android.ROUTE_UPDATE");
        sendBroadcast(i);
    }

    private void broadcastWidgetUpdate() {
        int[] widgetIds =AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this, RouteWidget.class));
        Intent intent = new Intent(this,RouteWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,widgetIds);
        sendBroadcast(intent);
    }
}

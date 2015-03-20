package njp.nu.routetracker;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class RouteApplication extends Application {
    private List<LatLng> routeCoordinates; //Coordinate list, used primarily by route fragment - NO add methods here, use mapfragment to add

    public void clearRouteCoordinates() {
        routeCoordinates.clear();
    }

    public List<LatLng> getRouteCoordinates() {
        if(routeCoordinates == null)
            routeCoordinates = new ArrayList<>();
        return routeCoordinates;
    }
}

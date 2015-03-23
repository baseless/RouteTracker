package njp.nu.routetracker._old;

import android.content.Context;
import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

import njp.nu.routetracker._old.LocationService;
import njp.nu.routetracker.services.DatabaseService;

public class RouteService {

    private List<Location> routeLocations;
    private List<LatLng> routeLatLng;
    private Context context;
    private LocationService locationService;
    private DatabaseService databaseService;

    public RouteService (Context context, List<Location> routeLocations, List<LatLng> routeLatLng) {
        this.routeLocations = routeLocations;
        this.routeLatLng = routeLatLng;
        this.locationService = new LocationService(context);
        this.databaseService = new DatabaseService(context);
        this.context = context;
    }

    public void parseCurrentPosition() {
        Location location;
        if(locationService.isGpsEnabled()) {
            location = locationService.getCurrentLocation();
            routeLocations.add(location);
            routeLatLng.add(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }
}

package njp.nu.routetracker;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class RouteService extends Service {

    private final IBinder binder = new RouteServiceBinder();
    private List<LatLng> routeCoordinates;

    public List<LatLng> getRouteCoordinates() {
        if(routeCoordinates == null)
            routeCoordinates = new ArrayList<>();
        return routeCoordinates;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class RouteServiceBinder extends Binder { //enclosed binder class

        RouteService getService() {
            return RouteService.this;
        }

    }
}

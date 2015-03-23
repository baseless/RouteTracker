package njp.nu.routetracker;

/**
 * Created by Daniel Ryhle on 2015-03-23.
 */

import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.List;

public class RouteFragment extends SupportMapFragment implements OnMapReadyCallback {

    private MarkerOptions marker;
    private List<LatLng> coords;
    private GoogleMap map;
    private float zoom;
    private PolylineOptions polyOpts;
    private Polyline poly;

    public void initializeRoute(List<LatLng> coords, float zoom) {
        this.coords = coords;
        this.zoom = zoom;
        initializeMap();
    }

    public void updateMap() {
        if(!initializeMap()) {
            drawPolyline();
            moveMap();
        }
    }

    private boolean initializeMap() { //returns false if no need to initialize
        if(map == null) {
            this.getMapAsync(this);
            return true;
        }
        return false;
    }

    private void drawAllMapPositions() {
        if(coords.size() > 0) {
            marker = new MarkerOptions(); //start marker
            marker.position(coords.get(0));

            map.addMarker(marker);
            moveMap();
            initializePolyline(); //draw lines
        }
    }

    private void moveMap() {
        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(coords.get(coords.size()-1), zoom); // center and zoom camera
        map.moveCamera(camera);
    }

    private void drawPolyline() {
        poly.setPoints(coords);
    }

    private void initializePolyline() {
        polyOpts = new PolylineOptions();
        polyOpts.width(2);
        polyOpts.color(Color.BLUE);
        poly =  map.addPolyline(polyOpts);
        drawPolyline();
    }

    public void onMapReady(GoogleMap map) {
        this.map = map;
        drawAllMapPositions();
    }
}

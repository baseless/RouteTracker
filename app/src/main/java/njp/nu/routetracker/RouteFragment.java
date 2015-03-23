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
            if(marker == null && coords.size() > 0)
                setStartMarker();
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
            if(marker == null)
                setStartMarker();
            moveMap();
        }
        initializePolyline(); //draw lines
    }

    private void setStartMarker() {
        marker = new MarkerOptions(); //start marker
        marker.position(coords.get(0));
        map.addMarker(marker);
    }

    private void moveMap() {
        if(coords != null && coords.size() > 0) {
            CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(coords.get(coords.size() - 1), zoom); // center and zoom camera
            map.moveCamera(camera);
        }
    }

    private void drawPolyline() {
        if(coords != null && coords.size() > 1)
            poly.setPoints(coords);
    }

    private void initializePolyline() {
        polyOpts = new PolylineOptions();
        polyOpts.width(12);
        polyOpts.color(Color.BLUE);
        poly =  map.addPolyline(polyOpts);
        drawPolyline();
    }

    public void onMapReady(GoogleMap map) {
        this.map = map;
        drawAllMapPositions();
    }
}

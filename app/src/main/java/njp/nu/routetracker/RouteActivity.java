package njp.nu.routetracker;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;
import java.util.Random;

public class RouteActivity extends FragmentActivity {

    private RouteFragment routeMap;
    private RouteApplication routeApp;
    private double demo_lat = 0;
    private double demo_long = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        routeApp = ((RouteApplication)getApplicationContext());
        routeMap = (RouteFragment) getSupportFragmentManager().findFragmentById(R.id.routeMapFragment);

        //----DEMO----
        List<LatLng> coords = routeApp.getRouteCoordinates();
        demo_lat = 56.046887;
        demo_long = 14.146163;
        if(coords.size() == 0)
            coords.add(new LatLng(demo_lat, demo_long)); //hÃ¤r kommer gps koordinat addas istÃ¤llet
        //-----------

        routeMap.initializeRoute(coords, 13.9f);
    }

    public void onRouteAddClick(View v) { //Demo metod fÃ¶r att simulera en timead service
        Random r = new Random();
        List<LatLng> coords = routeApp.getRouteCoordinates();
        demo_lat = coords.get(coords.size()-1).latitude + r.nextInt(10) * 0.0001;
        demo_long = coords.get(coords.size()-1).longitude + r.nextInt(10) * 0.0001;
        routeMap.addPosition(new LatLng(demo_lat, demo_long));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

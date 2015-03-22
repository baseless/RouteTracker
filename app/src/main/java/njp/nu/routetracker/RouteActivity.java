package njp.nu.routetracker;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;
import java.util.Random;

public class RouteActivity extends FragmentActivity {

    private RouteService routeService;
    private boolean serviceBound = false;
    private RouteFragment routeMap;
    private double demo_lat = 0;
    private double demo_long = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent serviceIntent = new Intent(this, RouteService.class);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        initializeMapFragment();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serviceBound) {
            unbindService(serviceConnection);
            serviceBound = false;
        }
    }

    private void initializeMapFragment() {
        routeMap = (RouteFragment) getSupportFragmentManager().findFragmentById(R.id.routeMapFragment);
        List<LatLng> coords = ((RouteApplication)getApplicationContext()).getRouteCoordinates();
        demo_lat = 56.046887;
        demo_long = 14.146163;
        if(coords.size() == 0)
            coords.add(new LatLng(demo_lat, demo_long));                                          //här kommer gps koordinat addas istället
        routeMap.initializeRoute(coords, 13.9f);
    }

    public void onRouteAddClick(View v) {                                                         //Demo metod för att simulera en timead service
        Random r = new Random();
        List<LatLng> coords = ((RouteApplication)getApplicationContext()).getRouteCoordinates(); //DEMO
        demo_lat = coords.get(coords.size()-1).latitude + r.nextInt(10) * 0.0001;                //DEMO
        demo_long = coords.get(coords.size()-1).longitude + r.nextInt(10) * 0.0001;              //DEMO
        routeMap.addPosition(new LatLng(demo_lat, demo_long));                                   //här kommer gps koordinat addas istället
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            RouteService.RouteServiceBinder binder = (RouteService.RouteServiceBinder) service;
            routeService = binder.getService();
            initializeMapFragment();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            serviceBound = false;
        }
    };
}

package njp.nu.routetracker;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import java.util.List;
import java.util.Random;

public class RouteActivity extends FragmentActivity {

    private RouteApplication app;
    private RouteFragment routeMap;
    private double demo_lat = 0;
    private double demo_long = 0;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updatePanel();
            updatePosition();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        app = (RouteApplication)getApplicationContext();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeMapFragment();
        registerReceiver(receiver, new IntentFilter("com.hmkcode.android.USER_ACTION"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
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

    private void updatePanel() {
        Random r = new Random();
        double speed = ((double)r.nextInt(10) * 4.0 + 2.0) / 10;
        double speed2 = ((double)r.nextInt(4) * 4.0 + 10.0) / 10;
        TextView curSpeed = (TextView)findViewById(R.id.viewCurrentSpeed);
        TextView avgSpeed = (TextView)findViewById(R.id.viewAvgSpeed);
        TextView time = (TextView)findViewById(R.id.viewSpentTime);
        curSpeed.setText(Double.toString(speed) + " Km/h");
        avgSpeed.setText(Double.toString(speed2) + " Km/h");
    }

    private void updatePosition() {
        Random r = new Random();
        List<LatLng> coords = ((RouteApplication)getApplicationContext()).getRouteCoordinates(); //DEMO
        demo_lat = coords.get(coords.size()-1).latitude + r.nextInt(10) * 0.00001;                //DEMO
        demo_long = coords.get(coords.size()-1).longitude + r.nextInt(10) * 0.00001;              //DEMO
        routeMap.addPosition(new LatLng(demo_lat, demo_long));
    }
}

package njp.nu.routetracker;

/**
 * Created by Daniel Ryhle on 2015-03-23.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;
import java.util.Random;

import njp.nu.routetracker.services.StatisticsService;

public class RouteActivity extends FragmentActivity {

    private RouteApplication app;
    private RouteFragment routeMap;
    private StatisticsService statisticsService;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updatePanel();
            routeMap.updateMap();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        app = (RouteApplication)getApplicationContext();
        statisticsService = new StatisticsService(app.getRouteLocations());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeMapFragment();
        registerReceiver(receiver, new IntentFilter("njp.nu.android.ROUTE_UPDATE"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    private void initializeMapFragment() {
        routeMap = (RouteFragment) getSupportFragmentManager().findFragmentById(R.id.routeMapFragment);
        routeMap.initializeRoute(app.getRouteCoordinates(), 18.0f);
    }

    private void updatePanel() {
        TextView curSpeed = (TextView)findViewById(R.id.viewCurrentSpeed);
        TextView dist = (TextView)findViewById(R.id.viewDistance);
        TextView time = (TextView)findViewById(R.id.viewSpentTime);
        curSpeed.setText(statisticsService.getCurrentSpeed() + " m/s");
        dist.setText(String.format("%.2f",app.getRouteDistance()) + " m");
        time.setText(statisticsService.getElapsedTime());
    }

    public void onStopClick(View v) {
        app.stopRoute();
        Intent switchToRoute = new Intent(RouteActivity.this, ResultActivity.class);
        startActivity(switchToRoute);
    }
}

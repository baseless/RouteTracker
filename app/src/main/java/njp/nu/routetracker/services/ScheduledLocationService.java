package njp.nu.routetracker.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;
import njp.nu.routetracker.RouteApplication;

public class ScheduledLocationService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private DatabaseService dbService;
    private static final String TAG = ScheduledLocationService.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private RouteApplication app;
    private Context appContext;
    private List<Location> routeLocations;
    private List<LatLng> routeLatLng;
    private long routeId;

    /* Constructor */
    public ScheduledLocationService(RouteApplication app, List<Location> routeLocations, List<LatLng> routeLatLng, DatabaseService dbService) {
        this.appContext = app.getApplicationContext();
        this.app = app;
        this.dbService = dbService;
        this.routeLatLng = routeLatLng;
        this.routeLocations = routeLocations;
        buildGoogleApiClient();
    }

    /* Build the Google API client object needed to use the services API */
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(appContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
        Log.d(TAG, "GoogleAPIClient successfully built.");
    }

    /* This runs ones when the Google API Client is built and connected */
    @Override
    public void onConnected(Bundle connectionHint) {
        createLocationRequest();
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) { }

    /* Setup a request with GPS-settings for timed queries*/
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(500);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /* Start updating locations */
    public void startLocationUpdates() {
        Log.i("", "START LOCATION UPDATES");
        routeId = dbService.insertRoute();
        createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Starting location updates..");
    }

    /* Stopping location updates */
    public void stopLocationUpdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Log.d(TAG, "Shutting down location updates..");
        dbService.updateRouteStopTime(routeId);
        //for(Route r : dbService.getRoutes())
        //    Log.i("", r.getRouteID() + "--" + r.getStartTime() + "--" + r.getStopTime());
    }

    /* Check whether GPS is enabled */
    public boolean isGpsEnabled() {
        LocationManager service = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
        return service.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean isWifiEnabled() {
        LocationManager service = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
        return service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /* On location change */
    @Override
    public void onLocationChanged(Location location) {
        if(location != null && location.getLatitude() > 0) {
            routeLocations.add(location);
            routeLatLng.add(new LatLng(location.getLatitude(), location.getLongitude()));
            dbService.insertPosition(location, routeId);
            if(routeLocations.size() > 1)
                addDistance(location);
        }
    }

    private void addDistance(Location current) {
        Location previous = routeLocations.get(routeLocations.size()-2);
        app.updateRouteDistance(current.distanceTo(previous));
    }
}

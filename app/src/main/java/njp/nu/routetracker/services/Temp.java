package njp.nu.routetracker.services;

import android.content.Context;
import android.location.Location;

import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import njp.nu.routetracker.RouteActivity;


public class Temp implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = LocationService.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;

    private Location mCurrentLocation;
    private Location mPreviousLocation;

    private LocationRequest mLocationRequest;

    private RouteActivity main;

    /* Constructor */
    public Temp(RouteActivity main) {
        this.main = main;
        if( isGpsEnabled() ){
            buildGoogleApiClient();
        } else {
            Log.e(TAG, "GPS IS NOT ENABLED!");
        }
    }

    /* Build the Google API client object needed to use the services API */
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(main.getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
        Log.d(TAG, "GoogleAPIClient successfully built.");
    }

    /* Query the GPS to retrieve a new Location. Used when only one request is needed. Else use startLocationUpdates. */
    public void getCurrentLocation() {
        mPreviousLocation = mCurrentLocation;
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mCurrentLocation != null)
            Log.d(TAG, "Lat: " + String.valueOf(mCurrentLocation.getLatitude()) + ", Long: " + String.valueOf(mCurrentLocation.getLongitude()));
    }

    /* This runs ones when the Google API Client is built and connected */
    @Override
    public void onConnected(Bundle connectionHint) {
        createLocationRequest();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /* Return the current location */
    public Location getLocation() {
        return mCurrentLocation;
    }

    /* Calculate distance between current and previous known locations */
    public double getDistanceFromPreviousLocation() {
        float[] results = new float[3];
        if (mPreviousLocation != null) {
            Location.distanceBetween(mPreviousLocation.getLatitude(), mPreviousLocation.getLongitude(), mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), results);
            Log.d(TAG, "Distance from previous location: " + results[0] + "meters");
        }
        return results[0];
    }



    /* Setup a request with GPS-settings for timed queries*/
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /* Start updating locations */
    public void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Starting location updates..");
    }

    /* Stopping location updates */
    public void stopLocationUpdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Log.d(TAG, "Shutting down location updates..");
    }

    /* Check whether GPS is enabled */
    public boolean isGpsEnabled() {
        LocationManager service = (LocationManager) main.getSystemService(Context.LOCATION_SERVICE);
        return service.isProviderEnabled(LocationManager.GPS_PROVIDER) && service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /* On location change */
    @Override
    public void onLocationChanged(Location location) {
        mPreviousLocation = mCurrentLocation;
        mCurrentLocation = location;
    }


    /* Save current positions */
    public Location[] saveCurrentState(){
        Location[] locations = new Location[2];
        locations[0] = mCurrentLocation;
        locations[1] = mPreviousLocation;
        return locations;
    }

    /* Load current positions */
    public void loadCurrentState(Location[] locations){
        mCurrentLocation = locations[0];
        mPreviousLocation = locations[1];
    }
}

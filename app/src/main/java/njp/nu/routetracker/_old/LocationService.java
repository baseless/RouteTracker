package njp.nu.routetracker._old;

/**
 * Created by Mattias Hjalmarsson on 2015-03-23.
 */

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class LocationService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LocationService.class.getSimpleName();
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private Context appContext;

    /* Constructor */
    public LocationService(Context appContext) {
        this.appContext = appContext;
        if( isGpsEnabled() ){
            buildGoogleApiClient();
        } else {
            Log.e(TAG, "GPS IS NOT ENABLED!");
        }
    }


    /* Build the Google API client object needed to use the services API */
    private synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(appContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
        Log.d(TAG, "GoogleAPIClient successfully built.");
    }

    /* Get the current location from the GPS*/
    public Location getCurrentLocation() {
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (currentLocation != null) {
            Log.d(TAG, "Lat: " + String.valueOf(currentLocation.getLatitude()) + ", Long: " + String.valueOf(currentLocation.getLongitude()));
        }
        return currentLocation;
    }

    /* This runs ones when the Google API Client is built and connected */
    @Override
    public void onConnected(Bundle connectionHint) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    /* Check whether GPS is enabled */
    public boolean isGpsEnabled() {
        LocationManager service = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
        return service.isProviderEnabled(LocationManager.GPS_PROVIDER) && service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}

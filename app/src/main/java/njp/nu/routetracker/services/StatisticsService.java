package njp.nu.routetracker.services;

import android.location.Location;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StatisticsService {

    private List<Location> routeLocations;

    public StatisticsService(List<Location> routeLocations) {
        this.routeLocations = routeLocations;
    }

    public float getAverageSpeed(float distMeters) {
        if(routeLocations.size() > 1) {
            long first = routeLocations.get(0).getTime();
            long last = routeLocations.get(routeLocations.size() - 1).getTime();
            return distMeters / TimeUnit.MILLISECONDS.toSeconds(last - first);
        }
        else
            return 0;
    }

    public String getCurrentSpeed() {
        if(routeLocations != null && routeLocations.size() > 1)
            return Double.toString(routeLocations.get(routeLocations.size()-1).getSpeed());
        else
            return "0.0";
    }

    public String getElapsedTime () {
        if(routeLocations.size() > 1) {
            long first = routeLocations.get(0).getTime();
            long last = routeLocations.get(routeLocations.size() - 1).getTime();
            return millisecondsToTime(last - first);
        }
        else
            return "00:00:00";
    }

    private String millisecondsToTime(long ms) {
        long hours = TimeUnit.MILLISECONDS.toHours(ms);
        if(hours > 0)
            ms -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        if(minutes > 0)
            ms -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(ms);

        String result = "";

        if(hours < 10)
            result += "0";
        result += hours + ":";

        if(minutes < 10)
            result += "0";
        result += minutes + ":";

        if(seconds < 10)
            result += "0";
        result += seconds;

        return result;
    }
}

package njp.nu.routetracker.services;

import android.location.Location;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StatisticsService {

    private List<Location> routeLocations;

    public StatisticsService(List<Location> routeLocations) {
        this.routeLocations = routeLocations;
    }

    public String getAvgSpeed() {
        return null;
    }

    public String getCurrentSpeed() {
        return null;
    }

    public String getElapsedTime () {
        if(routeLocations.size() > 1) {
            long first = routeLocations.get(0).getTime();
            long last = routeLocations.get(routeLocations.size() - 1).getTime();
            Log.d("TIME FIRST", String.valueOf(first));
            Log.d("TIME LAST", String.valueOf(last));
            return millisecondsToTime(last - first);
        }
        else
            return "00:00:00";
    }

    private String millisecondsToTime(long ms) {
        Date date = new Date(ms);
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        return formatter.format(date);
    }
}

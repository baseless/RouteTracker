package njp.nu.routetracker.services;

/**
 * Created by Andreas Svensson on 2015-03-23.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import njp.nu.routetracker.domain.Route;

public class DatabaseService extends SQLiteOpenHelper{

    private static final int database_version = 1;
    private static final String nameOFDataBase = "Database name";

    //Tables names
    private static final String routeName = "Routes";
    private static final String positionName = "Locations";

    //Tables id;
    private static final String routeId = "routeID";
    private static final String positionId = "PositionID";

    //Timer, distance and coordinates
    private static final String startTimer = "startTime";
    private static final String stopTimer = "stopTime";
    private static final String latitude = "Latitude";
    private static final String longitude = "Longitude";
    private static final String timeStamp = "timeStamp";

    //table create statements
    //table 1
    private static final String createTableRouteString = "Create table "
            + routeName + " ( " + routeId + " Integer primary key, "
            + startTimer + " text, " + stopTimer + " text )";

    //table 2
    private static final String createTablePositionString = "Create table "
            + positionName + " (" + positionId + " Integer primary key, "
            + latitude + " text, " + longitude + " text, "
            + timeStamp + " text, "
            + routeId + " int, " +  "Foreign key ( " + routeId + " ) references "
            + routeName + " ( " + routeId + " ))";


    public DatabaseService(Context context) {
        super(context, nameOFDataBase, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableRouteString);
        db.execSQL(createTablePositionString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + routeName);
        db.execSQL("Drop table if exists " + positionName);
        onCreate(db);
    }

    public long insertRoute() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(startTimer, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return db.insert(routeName, null, v);
    }

    public long updateRouteStopTime(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(stopTimer, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String selection = routeId + "=" + rowId;
        return db.update(routeName, v, selection, null);
    }

    public long insertPosition(Location l, long parentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(routeId, parentId);
        v.put(latitude, l.getLatitude());
        v.put(longitude, l.getLongitude());
        v.put(timeStamp, l.getTime());
        return db.insert(positionName, null, v);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public List<Route> getRoutes() {
        List<Route> routes = new ArrayList<Route>();
        String query = "SELECT * FROM " + routeName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.getCount()!=0){
            if(c.moveToFirst()){
                do{
                    routes.add(new Route(
                            c.getString(c.getColumnIndex(startTimer)),
                            c.getString(c.getColumnIndex(stopTimer)),
                            c.getInt(c.getColumnIndex(routeId))
                    ));
                }while(c.moveToNext());
            }
        }

        return routes;
    }

/*

    public Route getRoute(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + routeName + " WHERE "
                + routeId + " = " + id;
        Log.e(logger, query);
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        Route r = new Route(c.getInt(c.getColumnIndex(routeId)));
        return r;
    }


    public List<Route> getLatestRoutes(int rid) {
        List<Route> routes = new ArrayList<Route>();
        String query = "SELECT * FROM " + routeName + " WHERE "
                + positionId + " = " + rid;
        Log.e(logger, query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        int fixedSize = c.getCount(); //return number of rows
        if (fixedSize > 10) {
            int lastTenValues = fixedSize - 10;
            for (int i = 0; i < lastTenValues; i++) {
                c.moveToNext();
            }
        } else if (c != null) {
            c.moveToFirst();
        }
        Route r = new Route(c.getInt(c.getColumnIndex(routeId)));
        r.setStartTime(c.getInt(c.getColumnIndex(startTimer)));
        r.setStopTime(c.getInt(c.getColumnIndex(stopTimer)));
        routes.add(r);
        return routes;
    }

    public List<Position> getPositions(int id) {
        List<Position> positions = new ArrayList<Position>();
        String query = "SELECT * FROM " + positionName + " WHERE "
                + positionId + " = " + id;
        Log.e(logger, query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        int fixedSize = c.getCount(); //return number of rows
        if (fixedSize > 10) {
            int lastTenValues = fixedSize - 10;
            for (int i = 0; i < lastTenValues; i++) {
                c.moveToNext();
            }
        } else if (c != null) {
            c.moveToFirst();
        }
        Position p = new Position();
        p.setPositionID(c.getInt(c.getColumnIndex(positionId)));
        p.setCoordinatesLatitude(c.getLong(c.getColumnIndex(latitude)));
        p.setGetCoordinatesLongitude(c.getLong(c.getColumnIndex(longitude)));
        return positions;
    }

*/
}

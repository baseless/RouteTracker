package njp.nu.routetracker.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import njp.nu.routetracker.domain.Position;
import njp.nu.routetracker.domain.Route;

public class Database extends SQLiteOpenHelper{

    private static final String logger = "Database";
    private static final int database_version = 1;
    private static final String nameOFDataBase = "Database name";


    //Tables names
    private static final String routeName = "Route table 1";
    private static final String positionName = "Posistion table 2";

    //Tables id;
    private static final String routeId = "routeID";
    private static final String positionId = "PosisitionID";
    private static final String routerForeignKey = "routerForeigen key"; //Help

    //Timer, distance and coordinates
    private static final String startTimer = "start";
    private static final String stopTimer = "stop";
    private static final String latitude = "Latitude";
    private static final String longitude = "Longitude";
    private static final String timeFromLastPosition = "time";
    private static final String distanceFromLastPosition = "distance";

    //table create statements
    //table 1
    private static final String createTableRouteString = "Create table"
            + routeName + "(" + routeId + "Integer primary key,"
            + startTimer + "long Timer," + stopTimer + " long StopTime" + ")";

    //table 2
    private static final String createTablePositionString = "Create table"
            + positionName + "(" + positionId + "Integer primary key,"
            + latitude + "long Latitude," + longitude + " long Longitude, "
            + timeFromLastPosition + "long time, " + distanceFromLastPosition + " long distance, " + ")"
            + routerForeignKey + "integer, " + "Foreign key(" + routerForeignKey + ") references"
            + routeName + "(" + routeId + "));";


    public Database(Context context) {
        super(context, nameOFDataBase, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableRouteString);
        db.execSQL(createTablePositionString);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists" + routeName);
        db.execSQL("Drop table if exists" + positionName);

        onCreate(db);
    }

    public Route getRoute(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + routeName + " WHERE "
                + routeId + " = " + id;
        Log.e(logger, query);
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        Route r = new Route();
        r.setRouteID(c.getInt(c.getColumnIndex(routeId)));
        return r;
    }


    public List<Route> getLatestRoutes(int rid) {
        List<Route> routes = new ArrayList<Route>();
        String query = "SELECT * FROM " + routeName + " ORDER BY "
                + routeId + " = " + rid + "DESC limit " + 10;
        Log.e(logger, query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
         if (c != null) {
            c.moveToFirst();
        }
        Route r = new Route();
        r.setRouteID(c.getInt(c.getColumnIndex(routeId)));
        r.setStartTime(c.getInt(c.getColumnIndex(startTimer)));
        r.setStopTime(c.getInt(c.getColumnIndex(stopTimer)));
        routes.add(r);
        return routes;
    }

    public List<Position> getPositions(int id) {
        List<Position> positions = new ArrayList<Position>();
        String query = "SELECT * FROM " + positionName + " ORDER BY "
                + positionId + " = " + id + "DESC limit " + 10;
        Log.e(logger, query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
         if (c != null) {
            c.moveToFirst();
        }
        Position p = new Position();
        p.setPositionID(c.getInt(c.getColumnIndex(positionId)));
        p.setCoordinatesLatitude(c.getLong(c.getColumnIndex(latitude)));
        p.setGetCoordinatesLongitude(c.getLong(c.getColumnIndex(longitude)));
        p.setTimeFromLastPosition(c.getLong(c.getColumnIndex(timeFromLastPosition)));
        p.setDistanceFromLastPosition(c.getLong(c.getColumnIndex(distanceFromLastPosition)));
        return positions;
    }

    public long insertRoute(Route route) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(routeId, route.getRouteID());
        v.put(startTimer, route.getStartTime());
        v.put(stopTimer, route.getStopTime());

        long id = db.insert(createTableRouteString, null, v);
        return id;
    }

    public long insertPosition(Position p, Route routeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(positionId, p.getPositionID());
        v.put(latitude, p.getCoordinatesLatitude());
        v.put(longitude, p.getCoordinatesLongitude());
        v.put(timeFromLastPosition, p.getTimeFromLastPosition());
        v.put(distanceFromLastPosition, p.getDistanceFromLastPosition());

        long id = db.insert(createTablePositionString, null, v);
        return id;
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public void deleteRoutingTable(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(routeName, routeId + "=?",
                new String[]{
                        String.valueOf(id)
                });
    }

    public void deletePositionTable(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(positionName, positionId + "=?",
                new String[]{
                        String.valueOf(id)
                });
    }
}

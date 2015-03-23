package njp.nu.routetracker.domain;

public class Position {
    private int positionID;
    private Route routeForeignKeyID;
    private double coordinatesLatitude;
    private double coordinatesLongitude;
    private double timeFromLastPosition;
    private double distanceFromLastPosition;

    public Position() {
    }

    public Position(int positionID) {
        this.positionID = positionID;
    }


    public Position(int positionID, Route routeForeignKeyID) {
        this.positionID = positionID;
        this.routeForeignKeyID = routeForeignKeyID;
    }


    public Position(int positionID, Route routeForeignKeyID, double coordinatesLatitude) {
        this.positionID = positionID;
        this.routeForeignKeyID = routeForeignKeyID;
        this.coordinatesLatitude = coordinatesLatitude;
    }
    public Position(int positionID, Route routeForeignKeyID, double coordinatesLatitude, double coordinatesLongitude) {
        this.positionID = positionID;
        this.routeForeignKeyID = routeForeignKeyID;
        this.coordinatesLatitude = coordinatesLatitude;
        this.coordinatesLongitude = coordinatesLongitude;
    }

    public Position(int positionID, Route routeForeignKeyID, double coordinatesLatitude, double coordinatesLongitude, double timeFromLastPosition) {
        this.positionID = positionID;
        this.routeForeignKeyID = routeForeignKeyID;
        this.coordinatesLatitude = coordinatesLatitude;
        this.coordinatesLongitude = coordinatesLongitude;
        this.timeFromLastPosition = timeFromLastPosition;
    }

    public Position(int positionID, Route routeForeignKeyID, double coordinatesLatitude, double coordinatesLongitude, double timeFromLastPosition, double distanceFromLastPosition) {
        this.positionID = positionID;
        this.routeForeignKeyID = routeForeignKeyID;
        this.coordinatesLatitude = coordinatesLatitude;
        this.coordinatesLongitude = coordinatesLongitude;
        this.timeFromLastPosition = timeFromLastPosition;
        this.distanceFromLastPosition = distanceFromLastPosition;
    }
    public int getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public double getDistanceFromLastPosition() {
        return distanceFromLastPosition;
    }

    public void setDistanceFromLastPosition(double distanceFromLastPosition) {
        this.distanceFromLastPosition = distanceFromLastPosition;
    }

    public double getTimeFromLastPosition() {
        return timeFromLastPosition;
    }

    public void setTimeFromLastPosition(double timeFromLastPosition) {
        this.timeFromLastPosition = timeFromLastPosition;
    }

    public double getCoordinatesLongitude() {
        return coordinatesLongitude;
    }

    public void setGetCoordinatesLongitude(long coordinatesLongitude) {
        this.coordinatesLongitude = coordinatesLongitude;
    }

    public double getCoordinatesLatitude() {
        return coordinatesLatitude;
    }

    public void setCoordinatesLatitude(long coordinatesLatitude) {
        this.coordinatesLatitude = coordinatesLatitude;
    }

    public Route getRouteForeignKeyID() {
        return routeForeignKeyID;
    }

    public void setRouteForeignKeyID(Route routeForeignKeyID) {
        this.routeForeignKeyID = routeForeignKeyID;
    }
}


public class DistancePerTileCalculator {

    public static double inLatitudelAxis(int depth){
        return MapServer.MAP_LATITUDE_AXIS_LENGTH/calculateTheTotalNumberOfTilesInAxis(depth);
    }

    public static double inLongitudinalAxis(int depth){
        return MapServer.MAP_LONGITUDE_AXIS_LENGTH/calculateTheTotalNumberOfTilesInAxis(depth);
    }

    public static int calculateTheTotalNumberOfTilesInAxis(int depth){
        return (int)(Math.pow(2.0,depth));
    }
}

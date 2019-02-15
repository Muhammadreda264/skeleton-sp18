public class QuerySuccess {


    public static boolean checker (Point upperLeft,Point lowerRight){
        return  latitudeChecker(upperLeft.getLatitude())||latitudeChecker(lowerRight.getLatitude())||
                longitudinalChecker(upperLeft.getLongitudinal())||longitudinalChecker(lowerRight.getLongitudinal())
                ;
    }

    private static boolean longitudinalChecker(double longitudinal){
        boolean t = longitudinal>=MapServer.ROOT_ULLON &&longitudinal<=MapServer.ROOT_LRLON;
        return t;
    }

    private static boolean latitudeChecker(double latitude){
        boolean t =  latitude<=MapServer.ROOT_ULLAT &&latitude>=MapServer.ROOT_LRLAT;
        return t;
    }
}

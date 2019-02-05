public class QuerySuccess {
    private static Point upperLeftPoint;
    private static Point lowerRightPoint;

    public static boolean checker (Point upperLeft,Point lowerRight){
        upperLeftPoint=upperLeft;
        lowerRightPoint=lowerRight;
        return checkupperLeftPoint()&&checklowerLeftPoint();
    }

    private static boolean checkupperLeftPoint(){
        return checkupperLeftLongitudinal()&& checkupperLeftLatitude() ? true:false;
    }

    private static boolean checkupperLeftLongitudinal(){
        double i =upperLeftPoint.getLongitudinal()-MapServer.ROOT_ULLON;
        boolean t =upperLeftPoint.getLongitudinal()>=MapServer.ROOT_ULLON;
        return upperLeftPoint.getLongitudinal()>=MapServer.ROOT_ULLON ? true:false;
    }

    private static boolean checkupperLeftLatitude(){
        return upperLeftPoint.getLatitude()<=MapServer.ROOT_ULLAT ? true:false ;
    }

    private static boolean checklowerLeftPoint(){
        return checklowerLeftLongitudinal()&&checklowerLeftLatitude()? true:false;
    }

    private static boolean checklowerLeftLongitudinal(){
        return lowerRightPoint.getLongitudinal()<=MapServer.ROOT_LRLON ? true:false;
    }

    private static boolean checklowerLeftLatitude(){
        return lowerRightPoint.getLatitude()>=MapServer.ROOT_LRLAT ? true:false;
    }
}

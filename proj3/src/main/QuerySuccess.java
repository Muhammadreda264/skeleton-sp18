public class QuerySuccess {
    Point upperLeft;
    Point lowerRight;

    public QuerySuccess(Point upperLeft,Point lowerRight){
        this.upperLeft=upperLeft;
        this.lowerRight=lowerRight;

    }


    public boolean checke(){
       return checkInRange()&&checkOrder();
    }

    private boolean checkInRange(){
        return  latitudeInRange(upperLeft.getLatitude())||latitudeInRange(lowerRight.getLatitude())||
                longitudinalInRange(upperLeft.getLongitudinal())||longitudinalInRange(lowerRight.getLongitudinal());

    }

    private boolean longitudinalInRange(double longitudinal){
        return longitudinal>=MapServer.ROOT_ULLON &&longitudinal<=MapServer.ROOT_LRLON;
    }

    private static boolean latitudeInRange(double latitude){
        return latitude<=MapServer.ROOT_ULLAT &&latitude>=MapServer.ROOT_LRLAT;
    }

    private boolean checkOrder(){
        return longitudinalOrder()&&latitudeOrder();
    }

    private boolean longitudinalOrder(){
        return this.upperLeft.getLongitudinal()<this.lowerRight.getLongitudinal();

    }

    private boolean latitudeOrder(){
        return this.upperLeft.getLatitude()>this.lowerRight.getLatitude();
    }

}

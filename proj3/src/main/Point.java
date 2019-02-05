public class Point {
    private double xCoordinate;
    private double yCoordinate;
     Point (double xCoordinate,double yCoordinate ){
        this.xCoordinate=xCoordinate;
        this.yCoordinate=yCoordinate;
    }
    public double getLongitudinal(){

         return xCoordinate;
    }

    public double getLatitude(){
         return yCoordinate;
    }
}

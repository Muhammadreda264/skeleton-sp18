public class SuitableDepth {
    private static final double DISTANCE_PER_PIXEL_IN_ZERO_DEBTH =0.00034332275390625;
    private  double longitudinalDistance;
    private  double screenWidth;
    private  double lowerRightlongitudinal;
    private  double upperLeftLongitude;
    public SuitableDepth(double upperLeftLongitude,double lowerRightlongitudinal,double screenWidth){
        this.upperLeftLongitude=upperLeftLongitude;
        this.lowerRightlongitudinal=lowerRightlongitudinal;
        this.screenWidth=screenWidth;

    }

    public int find(){
        longitudinalDistance=calculateLongitudinalDistance();
        double requestedLongitudinalDistancePerPixel=calculateRequestedLongitudinalDistancePerPixel();
        int suitableDepth=0;
        double distancePerPixelInthisDebth= DISTANCE_PER_PIXEL_IN_ZERO_DEBTH;
        while (requestedLongitudinalDistancePerPixel<=distancePerPixelInthisDebth
                &&suitableDepth<7){
            suitableDepth++;
            distancePerPixelInthisDebth=distancePerPixelInthisDebth/2;
        }
        return suitableDepth;
    }

    private double calculateLongitudinalDistance(){

        return Math.abs(this.lowerRightlongitudinal-this.upperLeftLongitude);
    }

    private double calculateRequestedLongitudinalDistancePerPixel(){

        return longitudinalDistance/screenWidth;
    }

}

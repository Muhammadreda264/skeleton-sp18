public class SuitableDepth {
    private static final double DISTANCE_PER_PIXEL_IN_ZERO_DEBTH =0.00034332275390625;
    private static double longitudinalDistance;
    private static double screenWidth;
    private static double lowerRightlongitudinal;
    private static double upperLeftLongitude;

    public static int find(double upperLeftLongitudeVaule,double lowerRightlongitudinalVaule,double screenWidthVaule){
        upperLeftLongitude=upperLeftLongitudeVaule;
        lowerRightlongitudinal=lowerRightlongitudinalVaule;
        screenWidth=screenWidthVaule;
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

    private static double calculateLongitudinalDistance(){

        return Math.abs(lowerRightlongitudinal-upperLeftLongitude);
    }

    private static double calculateRequestedLongitudinalDistancePerPixel(){

        return longitudinalDistance/screenWidth;
    }

}

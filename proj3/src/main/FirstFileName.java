public class FirstFileName {


    public static int getXValue (double resquestedLrlon,int depth){
        double disanceBetweenRootUllonAndResquestedLrlon=Math.abs(resquestedLrlon-MapServer.ROOT_ULLON);
        double xValue=disanceBetweenRootUllonAndResquestedLrlon/
                DistancePerTileCalculator.inLongitudinalAxis(depth);
        return (int)Math.floor(xValue);
    }

    public static int getYValue (double resquestedLrlat,int depth){
        double disanceBetweenRootUllatAndResquestedLrlat=Math.abs(resquestedLrlat-MapServer.ROOT_ULLAT);
        double yValue=(disanceBetweenRootUllatAndResquestedLrlat/
                DistancePerTileCalculator.inLatitudelAxis(depth));
        return (int) Math.floor(yValue);
    }

}
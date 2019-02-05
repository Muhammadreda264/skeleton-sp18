public class LastFileName {

    public static int getXValue (double resquestedLrlon,int depth){
        double disanceBetweenRootUllonAndResquestedresquestedLRlon= Math.abs(resquestedLrlon-MapServer.ROOT_ULLON);
        double lastLongitudinalValue=(disanceBetweenRootUllonAndResquestedresquestedLRlon/
                DistancePerTileCalculator.inLongitudinalAxis(depth));
        return (int) Math.ceil(lastLongitudinalValue);
    }

    public static int getYValue (double resquestedLllat,int depth){

        double disanceBetweenRootULLATAndResquestedresquestedLRlat=Math.abs(resquestedLllat-MapServer.ROOT_ULLAT);
        double yValue=(disanceBetweenRootULLATAndResquestedresquestedLRlat/
                DistancePerTileCalculator.inLatitudelAxis(depth));
        return (int) Math.ceil(yValue);
    }

}

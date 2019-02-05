public class LowerRightdLongitudeRasterer {
    private static int depth;
    private static double resquestedLrlon;

    public static int find (double resquestedLRlonValue,int depthValue){
        resquestedLrlon=resquestedLrlon;
        depth=depthValue;
        double lastLongitudinalValue=(calculateTheDisanceBetweenRootUllonAndResquestedresquestedLRlon()/
                DistancePerTileCalculator.inLongitudinalAxis(depth));
        return (int) Math.ceil(lastLongitudinalValue);
    }

    private static double calculateTheDisanceBetweenRootUllonAndResquestedresquestedLRlon(){
        return resquestedLrlon-MapServer.ROOT_LRLON;
    }




}

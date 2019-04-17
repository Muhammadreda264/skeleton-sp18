public class Boundaries {
    private Point upperLeft;
    private Point lowerRight;
    private int depth;

    public Boundaries(int depth,Point upperLeft,Point lowerRight){
        this.upperLeft=upperLeft;
        this.lowerRight=lowerRight;
        this.depth=depth;

    }
    private int getFirstXValue (){
        double disanceBetweenRootUllonAndResquestedLrlon=Math.abs(this.upperLeft.getLongitudinal()-MapServer.ROOT_ULLON);
        double xValue=disanceBetweenRootUllonAndResquestedLrlon/ Rasterer.DistancePerTileInLongitudinalAxis(depth);
        return (int)Math.floor(xValue);
    }

    private int getFirstYValue (){
        double disanceBetweenRootUllatAndResquestedLrlat=Math.abs(this.upperLeft.getLatitude()-MapServer.ROOT_ULLAT);
        double yValue=(disanceBetweenRootUllatAndResquestedLrlat/ Rasterer.DistancePerTileInLatitudelAxis(depth));
        return (int) Math.floor(yValue);
    }
    private int getLastXValue (){
        double disanceBetweenRootUllonAndResquestedresquestedLRlon= Math.abs(this.lowerRight.getLongitudinal()-MapServer.ROOT_ULLON);
        double lastLongitudinalValue=(disanceBetweenRootUllonAndResquestedresquestedLRlon/
                Rasterer.DistancePerTileInLongitudinalAxis(depth));
        return (int) Math.ceil(lastLongitudinalValue);
    }

    private int getLastYValue (){

        double disanceBetweenRootULLATAndResquestedresquestedLRlat=Math.abs(this.lowerRight.getLatitude()-MapServer.ROOT_ULLAT);
        double yValue=(disanceBetweenRootULLATAndResquestedresquestedLRlat/Rasterer.DistancePerTileInLatitudelAxis(depth));
        return (int) Math.ceil(yValue);
    }

    public FileName firstfile(){
        return new FileName(depth,getFirstXValue(),getFirstYValue());
    }
    public FileName lastfile(){
        return new FileName(depth,getLastXValue(),getLastYValue());
    }


}

public class FileName {
    private static final int minDepth=0;
    private static final int maxDepth=7;
    private static final int minValue=0;
    private final int maxValue ;
    private final int xValue;
    private final int yValue;
    private final int depth;

    public FileName(int depth,int xValue,int yValue){
        if(isValidDepth())
        this.depth = depth;
        else
            this.depth=fixDepth(depth);
        this.maxValue=Rasterer.calculateTheTotalNumberOfTilesInAxis(this.depth)-1;
        if(isValidValue(xValue))
        this.xValue=xValue;
        else
            this.xValue=fixValue(xValue);
        if(isValidValue(yValue))
        this.yValue=yValue;
        else
            this.yValue=fixValue(yValue);

    }
    public String toString(){
        return String.format("d%d_x%d_y%d.png",depth,xValue,yValue);
    }


    public int getXValue() {
        return xValue;
    }

    public int getYValue() {

        return yValue;
    }

    public int getDepth() {

        return depth;
    }

    public  boolean isValid(){
        return isValidDepth() && isValidValue(xValue) && isValidValue(yValue);
    }

    private boolean isValidDepth(){
        return (depth>=minDepth) && (depth<=maxDepth);
    }

    private boolean isValidValue(int Value){
        return (Value>=minValue)&&
                (Value<=maxValue);
    }

    private int fixDepth(int depth){
        return depth < 0 ? minDepth : maxDepth;
    }

    private int fixValue(int value){
        return value < 0 ? maxValue :maxValue;
    }

    public double getUpperLeftLongitudal(){
        return getLon(this.depth,this.xValue);
    }

    public double getLowerRightLongitudal(){
        return getLon(this.depth,this.xValue);
    }

    private double getLon(int depth,int xValue){
        return MapServer.ROOT_ULLON+(xValue*Rasterer.DistancePerTileInLongitudinalAxis(depth));
    }


    public double getUpperLeftLatitude(){
        return getLat(this.depth,this.yValue);
    }

    public double getLowerRightLatitude(){
        return getLat(this.depth,this.yValue);
    }
    private double getLat(int depth, int yValue){
        return MapServer.ROOT_ULLAT-(yValue* Rasterer.DistancePerTileInLatitudelAxis(depth));

    }

}
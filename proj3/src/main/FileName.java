public class FileName {
    private int xValue;
    private int yValue;
    private int depth;

    public FileName(){

    }
    public FileName(int depth,int xValue,int yValue){
        this.depth = depth;
        this.xValue=xValue;
        this.yValue=yValue;

    }
    public String toString(){
        return String.format("d%d_x%d_y%d.png",depth,xValue,yValue);
    }

    public void setValues(int depth,int xValue,int yValue){
        setDepth(depth);
        setxValue(xValue);
        setYValue(yValue);
    }
    public void setxValue(int xValue) {
         this.xValue=xValue;
    }

    public void setYValue(int yValue) {
        this.yValue=yValue;
    }

    public void setDepth(int depth){
        this.depth=depth;
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
        return (depth>=0) && (depth<=7);
    }

    private boolean isValidValue(int Value){
        return (Value>=0)&&
                (Value<DistancePerTileCalculator.calculateTheTotalNumberOfTilesInAxis(depth));
    }

}

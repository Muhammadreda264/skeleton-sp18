public class FileName {
    private int xValue;
    private int yValue;
    private int depth;
    public FileName(int xValue,int yValue,int depth){
        this.xValue=xValue;
        this.yValue=yValue;
        this.depth = depth;

    }
    public String toString(){
        String fileName =String.format("d%d_x%d_y%d.png",depth,xValue,yValue);
        return fileName;
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

    public static String format (int xValue,int yValue,int depth){
        return String.format("d%d_x%d_y%d.png",depth,xValue,yValue);
    }
}

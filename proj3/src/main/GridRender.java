import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GridRender {
    private FileName upperLeft;
    private FileName lowerRight;

    public GridRender(FileName upperLeft,FileName lowerRight){
        this.upperLeft=upperLeft;
        this.lowerRight=lowerRight;

    }

    public String[][] Format(){
        int cols = Math.abs(lowerRight.getXValue()-upperLeft.getXValue());
        int rows = Math.abs(lowerRight.getYValue()-upperLeft.getYValue());
        String [][] grid=new String[rows][cols];
        ArrayList<String> tiles=getTiles();
        int count =0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if(count<tiles.size()){
                grid[r][c] = tiles.get(count);
                count++;}
                else
                break;

            }
        }
        return grid;
    }

    private ArrayList<String> getTiles (){
        ArrayList<String> Tiles = new ArrayList<>();
        FileName fileName=new FileName();
        int depth=lowerRight.getDepth();
        for (int j=upperLeft.getYValue();j<lowerRight.getYValue();j++){
            for (int i =upperLeft.getXValue();i<lowerRight.getXValue();i++){
                fileName.setValues(depth,i,j);

                 Tiles.add(fileName.toString());
                 if(!fileName.isValid())
                     System.out.println(fileName.toString());
            }

        }
             return Tiles;
    }

}

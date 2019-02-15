import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.*;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public static final double DISTANCE_PER_PIXEL_IN_ZERO_DEBTH =0.00034332275390625;
    private static int depth;
    private double longitudinalDistance;
    private static boolean querySuccess;
    private static double rasterUllon;

    public Rasterer() {

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles co llected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {

        Map<String, Object> results = new HashMap<>();
        Point upperLeft = new Point(params.get("ullon"),params.get("ullat"));
        Point lowerRight = new Point(params.get("lrlon"),params.get("lrlat"));
        double width =params.get("w");
        System.out.println(params);
        querySuccess =QuerySuccess.checker(upperLeft,lowerRight);
        results.put("query_success",querySuccess);

        depth=SuitableDepth.find(upperLeft.getLongitudinal(),lowerRight.getLongitudinal(),width);
        results.put("depth",depth);

        int firstxvalue = FirstFileName.getXValue(upperLeft.getLongitudinal(),depth);
        double rasterULLon=calculateTheLongitudal(firstxvalue);
        results.put("raster_ul_lon",rasterULLon);

        int firstyvalue = FirstFileName.getYValue(upperLeft.getLatitude(),depth);
        double rasterULLat=calculateTheLatitude(firstyvalue);
        results.put("raster_ul_lat",rasterULLat);

        int lastxvalue = LastFileName.getXValue(lowerRight.getLongitudinal(),depth);
        double rasterLRLon=calculateTheLongitudal(lastxvalue);
        results.put("raster_lr_lon",rasterLRLon);

        int lastyvalue = LastFileName.getYValue(lowerRight.getLatitude(),depth);
        double rasterLRLat=calculateTheLatitude(lastyvalue);

        results.put("raster_lr_lat",rasterLRLat);
        FileName f=new FileName(depth,firstxvalue,firstyvalue);
        FileName L =new FileName(depth,lastxvalue,lastyvalue);
        System.out.println(f);
        System.out.println(L);
        String[][] grid =new GridRender(f,L).Format();
        System.out.println(Arrays.deepToString(grid));
        results.put("render_grid",grid);
        System.out.println(results);
        return results;
    }

    private static double calculateTheLongitudal(double xValue){
        return MapServer.ROOT_ULLON+(xValue*DistancePerTileCalculator.inLongitudinalAxis(depth));
    }

    private static double calculateTheLatitude(double yValue){
        return MapServer.ROOT_ULLAT-(yValue* DistancePerTileCalculator.inLatitudelAxis(depth));
    }






}
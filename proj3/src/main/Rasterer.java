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
    public static int depth;



    public Rasterer() {

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
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

        boolean querySuccess =new QuerySuccess(upperLeft,lowerRight).checke();
        results.put("query_success",querySuccess);

         depth=new SuitableDepth(upperLeft.getLongitudinal(),lowerRight.getLongitudinal(),width).find();
        results.put("depth",depth);

        return results;
    }


    public static double DistancePerTileInLatitudelAxis(int depth){
        return MapServer.MAP_LATITUDE_AXIS_LENGTH/calculateTheTotalNumberOfTilesInAxis(depth);
    }

    public static double DistancePerTileInLongitudinalAxis(int depth){
        return MapServer.MAP_LONGITUDE_AXIS_LENGTH/calculateTheTotalNumberOfTilesInAxis(depth);
    }

    public static int calculateTheTotalNumberOfTilesInAxis(int depth){
        return (int)(Math.pow(2.0,depth));
    }
    public double calculateTheLongitudal(double xValue,int depth){
        return MapServer.ROOT_ULLON+(xValue*Rasterer.DistancePerTileInLongitudinalAxis(depth));
    }

    public double calculateTheLatitude(double yValue,int depth){
        return MapServer.ROOT_ULLAT-(yValue* Rasterer.DistancePerTileInLatitudelAxis(depth));
    }





}
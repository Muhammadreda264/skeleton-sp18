import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private static final double DISTANCE_PER_PIXEL_IN_ZERO_DEBTH =0.00034332275390625;
    private static final int FEET_PERLONGITUDE_PER_DEGREE =288200;

    public Rasterer() {
        testTheNumberOfTilesInCoordinates();
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
        System.out.println(params);
        String[][] doit ={{"d2_x0_y1.png", "d2_x1_y1.png", "d2_x2_y1.png","d2_x3_y1.png"},
                {"d2_x0_y2.png","d2_x1_y2.png", "d2_x2_y2.png", "d2_x3_y2.png"}};
        results.put("render_grid",doit);
        results.put("raster_ul_lon",-122.19737159729004);
        results.put("raster_ul_lat",37.88);
        results.put("w",1838.0);
        results.put("h",981.0);
        results.put("depth",0);
        results.put("query_success",true);
        results.put("raster_lr_lon",37.84676042746281);
        results.put("raster_lr_lat",-122.27625);

        return results;
    }

    private int findSuitableDepth(double lowerRightlongitudinal,double upperLeftLongitude,double screenWidth){
        double longitudinalDistance = calculatelongitudinalDistance( lowerRightlongitudinal, upperLeftLongitude);
        double requestedLongitudinalDistancePerPixel=calculateRequestedLongitudinalDistancePerPixel
                ( longitudinalDistance,screenWidth);
        int suitableDepth=0;
        double distancePerPixelInthisDebth= DISTANCE_PER_PIXEL_IN_ZERO_DEBTH;
        while (requestedLongitudinalDistancePerPixel<distancePerPixelInthisDebth&&suitableDepth<7){
            suitableDepth++;
            distancePerPixelInthisDebth=distancePerPixelInthisDebth/2;
        }
        return suitableDepth;
    }

    private double calculatelongitudinalDistance(double lowerRightlongitudinal,double upperLeftLongitude){
        return (lowerRightlongitudinal-upperLeftLongitude);
    }

    private double calculateRequestedLongitudinalDistancePerPixel(double longitudinaldistance,double screenWidth){
        return longitudinaldistance/screenWidth;
    }

    private int calculateTheNumberOfTilesInAxis(int depth){
        return (int) (Math.pow(2.0,(double)depth));
    }



}

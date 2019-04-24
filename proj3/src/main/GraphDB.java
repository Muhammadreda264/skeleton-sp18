import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;
import static java.util.stream.Collectors.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {

    private HashMap<Long ,Vertex> graphDB=new HashMap<Long ,Vertex>();


    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {

        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */

    public  void clean() {

        HashMap<Long,Vertex> cleanedGraph = new HashMap<>();
        graphDB.values().stream()
                .filter(Vertex::hasAdjacent)
                .forEach(Vertex ->cleanedGraph.put(Vertex.getId(),Vertex));
        graphDB=cleanedGraph;
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    public Iterable<Long> verticesIDs() {

        return graphDB.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    public Iterable<Long> adjacent(long v) {

        return graphDB.get(v).getAdjacent();
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex id closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closestVertexId(double lon, double lat) {
        Iterator<Long> verticesIterator=verticesIDs().iterator();
        double closestDistance=Double.MAX_VALUE;
        Long closest=0L;
        Long current=0L;
        double currentDistance;
        while(verticesIterator.hasNext()){
            current=verticesIterator.next();
            currentDistance=distance(lon,lat,lon(current),lat(current));
            if(currentDistance<closestDistance){
                closestDistance=currentDistance;
                closest=current;
            }
        }
        return closest;
    }

    /**
     * Returns the vertex object closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public Vertex closestVertex(double lon, double lat) {
        Iterator<Long> verticesIterator=verticesIDs().iterator();
        double closestDistance=Double.MAX_VALUE;
        Long closest=0L;
        Long current=0L;
        double currentDistance;
        while(verticesIterator.hasNext()){
            current=verticesIterator.next();
            currentDistance=distance(lon,lat,lon(current),lat(current));
            if(currentDistance<closestDistance){
                closestDistance=currentDistance;
                closest=current;
            }
        }
        return getVertex(closest);
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return (graphDB.get(v).getLon());
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {

        return (graphDB.get(v).getLat());
    }
    /**
     * add Vertex to the graphDB .
     * @param v the Vertex to add to graphDB.
     */
    void addVertex(Vertex v) {
        graphDB.put(v.getId(),v);

    }
    /**
     * add edge between two edges.
     * @param firstId the id of first Vertex.
     * @param secondId the id of second Vertex.
     */

    void addEdge(Long firstId,Long secondId){
        graphDB.get(firstId).addEdge(secondId);
        graphDB.get(secondId).addEdge(firstId);
    }

    /**
     * Gets the Vertex from id.
     * @param id The id of the vertex.
     * @return The Vertex mapped to id.
     */
    Vertex getVertex(Long id){
        return graphDB.get(id);
    }

    /**
     * Validates that graphDB contains id.
     * @param  id The id of the vertex.
     * @return true if vertices contains id otherwise return false.
     */

    boolean contains(Long id){
        return graphDB.containsKey(id);
    }

}

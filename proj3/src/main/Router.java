import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;


/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map by using A* .
 */
public class Router {

    private Router(){
        // Private constructor to avoid instantiation
    }



    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, final double stlon, final double stlat,
                                          final double destlon, final double destlat) {
        final Long sourceId=g.closestVertexId(stlon,stlat);
        final Long targetId=g.closestVertexId(destlon,destlat);
        HashSet<Long> visited =new HashSet<>();
        HashMap<Long,VertexInfo> vi=new HashMap<>();
        Comparator<Long> c1 =(o1, o2)-> vi.get(o1).compareTo(vi.get(o2));
        PriorityQueue<Long> distancePriorityQueue = new PriorityQueue<>(c1);
        vi.put(sourceId,new VertexInfo(null,0.0,g.distance(sourceId,targetId)));
        distancePriorityQueue.add(sourceId);

        while (!distancePriorityQueue.isEmpty()){
            Long currentSourceId=distancePriorityQueue.remove();
            visited.add(currentSourceId);

            if(currentSourceId.equals(targetId)){
                break;
            }

            Iterator<Long>adjacent=g.adjacent(currentSourceId).iterator();
            while (adjacent.hasNext()){
                Long currentTargetId=adjacent.next();

                if (!visited.contains(currentTargetId)&& g.contains(currentTargetId)) {
                    double edgeDistance = g.distance(currentSourceId,currentTargetId);
                    double newDistance = vi.get(currentSourceId).getShortestDistance()+ edgeDistance;
                    if (!vi.containsKey(currentTargetId) || newDistance < vi.get(currentTargetId).getShortestDistance()){
                        double heuristic=g.distance(currentTargetId,targetId);
                        vi.put(currentTargetId,new VertexInfo(currentSourceId,newDistance,heuristic));
                    }
                    distancePriorityQueue.add(currentTargetId);
                }
            }

        }

        return getResult(sourceId,targetId,vi);
    }


    private static List<Long>getResult(Long sourceId,Long targetId,HashMap<Long,VertexInfo> info){
        ArrayList result = new ArrayList();
        while (!targetId.equals(sourceId)){
            result.add(targetId);
            targetId=info.get(targetId).getParent();
        }
        result.add(targetId);
        Collections.reverse(result);

        return result;
    }

    private static class VertexInfo implements Comparable<VertexInfo>{
        private final double shortestDistance;
        private final double heuristic;
        private final Long parent;

        public VertexInfo(Long parent,double shortestDistance,double heuristic) {
            this.parent=parent;
            this.shortestDistance = shortestDistance;
            this.heuristic=heuristic;
        }

        public double getShortestDistance() {
            return shortestDistance;
        }

        public Long getParent() {
            return parent;
        }

        public double getHeuristic() {
            return heuristic;
        }

        @Override
        public int compareTo(VertexInfo anotherVertice) {
            Double d1 =this.getShortestDistance()+this.getHeuristic();
            Double d2=anotherVertice.getShortestDistance()+anotherVertice.getHeuristic();
            if (d1 > d2)
                return 1;
            if (d1 < d2)
                return -1;
            return 0;
        }


    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigationDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null;
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }

}

import java.util.HashSet;

public class Vertex {
    private final long id;
    private final double lon;
    private final double lat;
    private String name;
    private HashSet<Long> adjacent;


    Vertex(long id, double lon, double lat){
        this.id=id;
        this.lon=lon;
        this.lat=lat;
        this.adjacent=new HashSet<>();
    }

    public long getId() {
        return id;
    }


    public double getLon() {

        return lon;
    }


    public double getLat() {
        return lat;
    }



    public HashSet<Long> getAdjacent() {
        return adjacent;
    }

    void addEdge(long target){
        adjacent.add(target);
    }

    @Override
    public int hashCode() {
        return (int) (id/607);
    }


}

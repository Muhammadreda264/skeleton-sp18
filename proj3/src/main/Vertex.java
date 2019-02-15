import java.util.HashMap;

public class Vertex {
    private long id;
    private double lon;
    private double lat;
    private HashMap<String, String> extraInfo=new HashMap<>();

    Vertex(){

    }


    Vertex(long id,double lon,double lat){
        this.id=id;
        this.lon=lon;
        this.lat=lat;
        extraInfo=new HashMap<>();
    }

    public long getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public double getLon() {

        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public HashMap<String, String> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(HashMap<String, String> extraInfo) {
        this.extraInfo = extraInfo;
    }



}

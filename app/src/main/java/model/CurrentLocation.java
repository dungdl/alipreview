package model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class CurrentLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    private double lat;
    private double lon;
    @Generated(hash = 1809281099)
    public CurrentLocation(String id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
    @Generated(hash = 1646613274)
    public CurrentLocation() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public double getLat() {
        return this.lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLon() {
        return this.lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }


}

package model;

public class Destination {
    private String id;
    private String name;
    private String address;
    private String ava;
    private Location location;

    public Destination(String id, String name, String address, String ava) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.ava = ava;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Location getLocation() {
        return location;
    }

    public String getAva() {
        return ava;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

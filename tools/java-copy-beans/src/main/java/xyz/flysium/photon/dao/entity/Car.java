package xyz.flysium.photon.dao.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import xyz.flysium.photon.dao.entity.support.CarType;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */

public class Car {

    private String make;

    private int numberOfSeats;

    private CarType type;

    private Date createdAt;

    private String[] lights;

    private List<String> lightList;

    private Map<String, Object> attributes;

    //constructor, getters, setters etc.

    public Car() {
    }

    public Car(String make, int numberOfSeats, CarType type, Date createdAt) {
        this.make = make;
        this.numberOfSeats = numberOfSeats;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String[] getLights() {
        return lights;
    }

    public void setLights(String[] lights) {
        this.lights = lights;
    }

    public List<String> getLightList() {
        return lightList;
    }

    public void setLightList(List<String> lightList) {
        this.lightList = lightList;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}


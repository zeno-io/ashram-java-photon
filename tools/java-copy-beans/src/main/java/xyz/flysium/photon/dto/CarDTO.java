package xyz.flysium.photon.dto;

import com.googlecode.jmapper.annotations.JMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class CarDTO /*implements ICar */ {

    @JMap
    private String make;

    @JMap("numberOfSeats")
    private int seatCount;

    //   @JMap // TODO JMapper not support enum to String
    private String type;

    @JMap("createdAt")
    private Date createTime;

    //    @JMap("createdAt")
    private String createAtFormat;

    //    @JMap("createdAt")
    private String createAtExpressionFormat;

    private String email;

    @JMap
    private String[] lights;

    @JMap
    private List<String> lightList;

    @JMap
    private Map<String, Object> attributes;

    //constructor, getters, setters etc.

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateAtFormat() {
        return createAtFormat;
    }

    public void setCreateAtFormat(String createAtFormat) {
        this.createAtFormat = createAtFormat;
    }

    public String getCreateAtExpressionFormat() {
        return createAtExpressionFormat;
    }

    public void setCreateAtExpressionFormat(String createAtExpressionFormat) {
        this.createAtExpressionFormat = createAtExpressionFormat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


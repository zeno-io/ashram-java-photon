package xyz.flysium.photon.support.spring;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public interface ICar {
    String getMake();

    void setMake(String make);
    //
    //    int getNumberOfSeats();
    //
    //    void setNumberOfSeats(int numberOfSeats);
    //
    //    CarType getType();
    //
    //    void setType(CarType type);
    //
    //    Date getCreatedAt();
    //
    //    void setCreatedAt(Date createdAt);

    String[] getLights();

    void setLights(String[] lights);

    List<String> getLightList();

    void setLightList(List<String> lightList);

    Map<String, Object> getAttributes();

    void setAttributes(Map<String, Object> attributes);
}

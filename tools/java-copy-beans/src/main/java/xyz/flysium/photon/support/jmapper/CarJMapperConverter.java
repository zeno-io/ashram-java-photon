package xyz.flysium.photon.support.jmapper;

import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.annotations.JMapConversion;
import com.googlecode.jmapper.api.JMapperAPI;
import xyz.flysium.photon.dao.entity.Car;
import xyz.flysium.photon.dao.entity.support.CarType;
import xyz.flysium.photon.dto.CarDTO;
import xyz.flysium.photon.support.BeanConverter;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class CarJMapperConverter implements BeanConverter<Car, CarDTO> {

    private final JMapper realLifeMapper;

    //    private final JMapper simpleMapper;

    public CarJMapperConverter() {
        JMapperAPI api = new JMapperAPI().add(JMapperAPI.mappedClass(CarDTO.class));
        realLifeMapper = new JMapper(CarDTO.class, Car.class, api);
    }

    @Override
    public CarDTO domain2dto(Car car) {
        return (CarDTO) realLifeMapper.getDestination(car);
    }

    @JMapConversion(from = "carType", to = "carType")
    public String conversion(CarType type) {
        return type.name();
    }

}


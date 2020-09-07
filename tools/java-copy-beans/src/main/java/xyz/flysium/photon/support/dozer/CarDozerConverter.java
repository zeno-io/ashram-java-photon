package xyz.flysium.photon.support.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import xyz.flysium.photon.dao.entity.Car;
import xyz.flysium.photon.dto.CarDTO;
import xyz.flysium.photon.support.BeanConverter;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class CarDozerConverter implements BeanConverter<Car, CarDTO> {

    private final Mapper mapper;

    public CarDozerConverter() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(DozerConverter.class.getResourceAsStream("/dozer/v5/car-mapping.xml"));
        this.mapper = mapper;
    }

    @Override
    public CarDTO domain2dto(Car car) {
        return mapper.map(car, CarDTO.class);
    }

}

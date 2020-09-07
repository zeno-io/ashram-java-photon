package xyz.flysium.photon.support.dozer;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import xyz.flysium.photon.dao.entity.Car;
import xyz.flysium.photon.dto.CarDTO;
import xyz.flysium.photon.support.BeanConverter;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class CarDozerMapperConverter implements BeanConverter<Car, CarDTO> {

    private final Mapper mapper;

    public CarDozerMapperConverter() {
        this.mapper = DozerBeanMapperBuilder.create().withMappingFiles("dozer/car-mapping.xml").build();
    }

    @Override
    public CarDTO domain2dto(Car car) {
        return mapper.map(car, CarDTO.class);
    }

}

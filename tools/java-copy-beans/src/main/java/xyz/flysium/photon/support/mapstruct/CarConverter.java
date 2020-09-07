package xyz.flysium.photon.support.mapstruct;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import xyz.flysium.photon.dao.entity.Car;
import xyz.flysium.photon.dto.CarDTO;
import xyz.flysium.photon.support.BeanConverter;

// @Mapper
@Mapper(componentModel = "spring")
public interface CarConverter extends BeanConverter<Car, CarDTO> {

    //    CarConverter INSTANCE = Mappers.getMapper(CarConverter.class);

    @Mappings({ @Mapping(source = "numberOfSeats", target = "seatCount"),
        @Mapping(source = "createdAt", target = "createTime"),
        @Mapping(source = "createdAt", target = "createAtFormat", dateFormat = "yyyy-MM-dd HH:mm:ss"),
        @Mapping(target = "createAtExpressionFormat", expression = "java(org.apache.commons.lang3.time.DateFormatUtils.format(car.getCreatedAt(),\"yyyy-MM-dd HH:mm:ss\"))"),
        @Mapping(target = "email", ignore = true)
    })
    @Override
    CarDTO domain2dto(Car car);

    @Override
    List<CarDTO> domain2dto(List<Car> cars);
}


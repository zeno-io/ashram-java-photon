package xyz.flysium.photon.support.orika;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import xyz.flysium.photon.dao.entity.Car;
import xyz.flysium.photon.dto.CarDTO;
import xyz.flysium.photon.support.BeanConverter;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class CarOrikaConverter implements BeanConverter<Car, CarDTO> {

    private final MapperFacade mapperFacade;

    public CarOrikaConverter() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Car.class, CarDTO.class)
            //            .field("numberOfSeats", "seatCount")
            //            .field("createdAt", "createTime")
            .customize(new Mapper<Car, CarDTO>() {

                @Override
                public void mapAtoB(Car car, CarDTO carDTO, MappingContext mappingContext) {
                    if (car == null) {
                        return;
                    }

                    if (car.getCreatedAt() != null) {
                        carDTO
                            .setCreateAtFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(car.getCreatedAt()));
                    }
                    carDTO.setCreateTime(car.getCreatedAt());
                    carDTO.setSeatCount(car.getNumberOfSeats());
                    carDTO.setMake(car.getMake());
                    if (car.getType() != null) {
                        carDTO.setType(car.getType().name());
                    }
                    String[] lights = car.getLights();
                    if (lights != null) {
                        carDTO.setLights(Arrays.copyOf(lights, lights.length));
                    }
                    List<String> list = car.getLightList();
                    if (list != null) {
                        carDTO.setLightList(new ArrayList<String>(list));
                    }
                    Map<String, Object> map = car.getAttributes();
                    if (map != null) {
                        carDTO.setAttributes(new HashMap<String, Object>(map));
                    }

                    carDTO.setCreateAtExpressionFormat(org.apache.commons.lang3.time.DateFormatUtils
                        .format(car.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"));
                }

                @Override
                public void mapBtoA(CarDTO carDTO, Car car, MappingContext mappingContext) {
                    //TODO
                }

                @Override
                public void setMapperFacade(MapperFacade mapperFacade) {

                }

                @Override
                public void setUsedMappers(Mapper<Object, Object>[] mappers) {

                }

                @Override
                public Boolean favorsExtension() {
                    return null;
                }

                @Override
                public Type<Car> getAType() {
                    return null;
                }

                @Override
                public Type<CarDTO> getBType() {
                    return null;
                }
            }).exclude("email").byDefault().register();
        mapperFacade = mapperFactory.getMapperFacade();
    }

    @Override
    public CarDTO domain2dto(Car car) {
        return mapperFacade.map(car, CarDTO.class);
    }

}

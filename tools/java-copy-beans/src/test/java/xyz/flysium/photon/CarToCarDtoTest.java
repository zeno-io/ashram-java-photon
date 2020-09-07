package xyz.flysium.photon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.google.common.collect.ImmutableMap;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.flysium.photon.dao.entity.Car;
import xyz.flysium.photon.dao.entity.NotTypeCar;
import xyz.flysium.photon.dao.entity.support.CarType;
import xyz.flysium.photon.dto.CarDTO;
import xyz.flysium.photon.support.dozer.CarDozerConverter;
import xyz.flysium.photon.support.dozer.CarDozerMapperConverter;
import xyz.flysium.photon.support.jmapper.CarJMapperConverter;
import xyz.flysium.photon.support.mapstruct.CarConverter;
import xyz.flysium.photon.support.orika.CarOrikaConverter;

/**
 * 一对一测试
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarToCarDtoTest {

    private final CarDozerConverter carDozerConverter = new CarDozerConverter();

    private final CarDozerMapperConverter carDozerMapperConverter = new CarDozerMapperConverter();

    private final CarOrikaConverter carOrikaConverter = new CarOrikaConverter();

    private final CarJMapperConverter carJMapperConverter = new CarJMapperConverter();

    @Autowired
    private CarConverter carConverter;

    private Car car;

    private final List<Car> cars = new ArrayList<>();

    @Before
    public void setUp() {
        //given
        car = new Car("Morris", 5, CarType.SEDAN, new Date());
        car.setLights(new String[] { "Front", "Behind" });
        car.setLightList(Arrays.asList("Front", "Behind"));
        //        car.setAttributes(ImmutableMap.of("a", "k", "b", 2));
        Map<String, Object> map = new HashMap<>();
        map.putIfAbsent("a", "k");
        map.putIfAbsent("b", 2);
        car.setAttributes(map);

        cars.add(car);
    }

    @Test
    public void apacheBeanUtils() throws InvocationTargetException, IllegalAccessException {
        //when
        CarDTO carDto = new CarDTO();
        org.apache.commons.beanutils.BeanUtils.copyProperties(carDto, car);

        //then
        assertNotNull(carDto);
        assertEquals(car.getMake(), carDto.getMake());
        // TODO 不支持不同名的字段映射
        assertNotEquals(car.getNumberOfSeats(), carDto.getSeatCount());
        assertEquals(car.getType().name(), carDto.getType());

        assertEquals(car.getLights().length, carDto.getLights().length);
        for (int i = 0; i < car.getLights().length; i++) {
            assertEquals(car.getLights()[i], carDto.getLights()[i]);
        }

        assertEquals(car.getLightList().size(), carDto.getLightList().size());
        for (int i = 0; i < car.getLightList().size(); i++) {
            assertEquals(car.getLightList().get(i), carDto.getLightList().get(i));
        }

        assertEquals(car.getAttributes().size(), carDto.getAttributes().size());
        car.getAttributes().forEach((k, v) -> {
            assertEquals(v, carDto.getAttributes().get(k));
        });

        // TODO 不支持不同名的字段映射
        assertNotEquals(carDto.getCreateTime(), car.getCreatedAt());
        assertNull(carDto.getCreateTime());

        // TODO 不支持 Date-String 转换
        assertNull(carDto.getCreateAtFormat());
        assertNull(carDto.getCreateAtExpressionFormat());
    }

    @Test
    public void apachePropertyUtils() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //when
        CarDTO carDto = new CarDTO();
        Throwable ex = null;
        try {
            org.apache.commons.beanutils.PropertyUtils.copyProperties(carDto, car);
        }
        catch (Throwable e) {
            ex = e;
        }

        //then
        // TODO 转换需要类型一致，否则抛错
        assertNotNull(ex);
        assertEquals(IllegalArgumentException.class, ex.getClass());
        assertNotEquals(car.getMake(), carDto.getMake());

        //given
        NotTypeCar notTypeCar = new NotTypeCar("Morris", 5, new Date());
        notTypeCar.setLights(new String[] { "Front", "Behind" });
        notTypeCar.setLightList(Arrays.asList("Front", "Behind"));
        notTypeCar.setAttributes(ImmutableMap.of("a", "k", "b", 2));

        //when
        org.apache.commons.beanutils.PropertyUtils.copyProperties(carDto, notTypeCar);
        assertNotNull(carDto);
        assertEquals(car.getMake(), carDto.getMake());
        // TODO 不支持不同名的字段映射
        assertNotEquals(car.getNumberOfSeats(), carDto.getSeatCount());
        // TODO 不支持 enum 类型自动转换
        assertNull(carDto.getType());

        assertEquals(car.getLights().length, carDto.getLights().length);
        for (int i = 0; i < car.getLights().length; i++) {
            assertEquals(car.getLights()[i], carDto.getLights()[i]);
        }

        assertEquals(car.getLightList().size(), carDto.getLightList().size());
        for (int i = 0; i < car.getLightList().size(); i++) {
            assertEquals(car.getLightList().get(i), carDto.getLightList().get(i));
        }

        assertEquals(car.getAttributes().size(), carDto.getAttributes().size());
        car.getAttributes().forEach((k, v) -> {
            assertEquals(v, carDto.getAttributes().get(k));
        });

        // TODO 不支持不同名的字段映射
        assertNotEquals(carDto.getCreateTime(), car.getCreatedAt());
        assertNull(carDto.getCreateTime());

        // TODO 不支持 Date-String 转换
        assertNull(carDto.getCreateAtFormat());
        assertNull(carDto.getCreateAtExpressionFormat());
    }

    @Test
    public void springBeanUtils() {
        //when
        CarDTO carDto = new CarDTO();
        org.springframework.beans.BeanUtils.copyProperties(car, carDto, "email");
        //        org.springframework.beans.BeanUtils.copyProperties(car, carDto, ICar.class);

        //then
        assertNotNull(carDto);
        assertEquals(car.getMake(), carDto.getMake());
        // TODO 不支持不同名的字段映射
        assertNotEquals(car.getNumberOfSeats(), carDto.getSeatCount());
        // TODO 不支持 enum 类型自动转换
        assertNotEquals(car.getType().name(), carDto.getType());

        assertEquals(car.getLights().length, carDto.getLights().length);
        for (int i = 0; i < car.getLights().length; i++) {
            assertEquals(car.getLights()[i], carDto.getLights()[i]);
        }

        assertEquals(car.getLightList().size(), carDto.getLightList().size());
        for (int i = 0; i < car.getLightList().size(); i++) {
            assertEquals(car.getLightList().get(i), carDto.getLightList().get(i));
        }

        assertEquals(car.getAttributes().size(), carDto.getAttributes().size());
        car.getAttributes().forEach((k, v) -> {
            assertEquals(v, carDto.getAttributes().get(k));
        });

        // TODO 不支持不同名的字段映射
        assertNotEquals(carDto.getCreateTime(), car.getCreatedAt());
        assertNull(carDto.getCreateTime());

        // TODO 不支持 Date-String 转换
        assertNull(carDto.getCreateAtFormat());
        assertNull(carDto.getCreateAtExpressionFormat());
    }

    @Test
    public void cglibBeanCopier() {
        //when
        CarDTO carDto = new CarDTO();
        org.springframework.cglib.beans.BeanCopier.create(Car.class, CarDTO.class, true)
            .copy(car, carDto, (o, aClass, o1) -> {
                if ("setType".equals(o1)) {
                    if (o instanceof Enum) {
                        return ((Enum<?>) o).name();
                    }
                }
                return o;
            });

        //then
        assertNotNull(carDto);
        assertEquals(car.getMake(), carDto.getMake());

        // TODO 不支持不同名的字段映射
        assertNotEquals(car.getNumberOfSeats(), carDto.getSeatCount());
        assertEquals(car.getType().name(), carDto.getType());

        assertEquals(car.getLights().length, carDto.getLights().length);
        for (int i = 0; i < car.getLights().length; i++) {
            assertEquals(car.getLights()[i], carDto.getLights()[i]);
        }

        assertEquals(car.getLightList().size(), carDto.getLightList().size());
        for (int i = 0; i < car.getLightList().size(); i++) {
            assertEquals(car.getLightList().get(i), carDto.getLightList().get(i));
        }

        assertEquals(car.getAttributes().size(), carDto.getAttributes().size());
        car.getAttributes().forEach((k, v) -> {
            assertEquals(v, carDto.getAttributes().get(k));
        });

        // TODO 不支持不同名的字段映射
        assertNotEquals(carDto.getCreateTime(), car.getCreatedAt());
        assertNull(carDto.getCreateTime());

        // TODO 不支持 Date-String 转换
        assertNull(carDto.getCreateAtFormat());
        assertNull(carDto.getCreateAtExpressionFormat());
    }

    @Test
    public void dozer() {
        //when
        //        CarDto carDto = CarConverter.INSTANCE.domain2dto(car);
        CarDTO carDto = carDozerConverter.domain2dto(car);

        //then
        assertNotNull(carDto);
        assertEquals(car.getMake(), carDto.getMake());
        assertEquals(car.getNumberOfSeats(), carDto.getSeatCount());
        assertEquals(car.getType().name(), carDto.getType());

        assertEquals(car.getLights().length, carDto.getLights().length);
        for (int i = 0; i < car.getLights().length; i++) {
            assertEquals(car.getLights()[i], carDto.getLights()[i]);
        }

        assertEquals(car.getLightList().size(), carDto.getLightList().size());
        for (int i = 0; i < car.getLightList().size(); i++) {
            assertEquals(car.getLightList().get(i), carDto.getLightList().get(i));
        }

        assertEquals(car.getAttributes().size(), carDto.getAttributes().size());
        car.getAttributes().forEach((k, v) -> {
            assertEquals(v, carDto.getAttributes().get(k));
        });

        assertEquals(carDto.getCreateTime(), car.getCreatedAt());

        // TODO 支持 Date-String 转换 ？？
        assertNull(carDto.getCreateAtFormat());
        assertNull(carDto.getCreateAtExpressionFormat());

        //        List<CarDto> carDtos = CarConverter.INSTANCE.domain2dto(people);
        List<CarDTO> carDtos = carDozerConverter.domain2dto(cars);
        //        System.out.println(carDtos);
        assertNotNull(carDtos);
    }

    @Test
    public void dozerMapper() {
        //when
        //        CarDto carDto = CarConverter.INSTANCE.domain2dto(car);
        CarDTO carDto = carDozerMapperConverter.domain2dto(car);

        //then
        assertNotNull(carDto);
        assertEquals(car.getMake(), carDto.getMake());
        assertEquals(car.getNumberOfSeats(), carDto.getSeatCount());
        assertEquals(car.getType().name(), carDto.getType());

        assertEquals(car.getLights().length, carDto.getLights().length);
        for (int i = 0; i < car.getLights().length; i++) {
            assertEquals(car.getLights()[i], carDto.getLights()[i]);
        }

        assertEquals(car.getLightList().size(), carDto.getLightList().size());
        for (int i = 0; i < car.getLightList().size(); i++) {
            assertEquals(car.getLightList().get(i), carDto.getLightList().get(i));
        }

        assertEquals(car.getAttributes().size(), carDto.getAttributes().size());
        car.getAttributes().forEach((k, v) -> {
            assertEquals(v, carDto.getAttributes().get(k));
        });

        assertEquals(carDto.getCreateTime(), car.getCreatedAt());

        // TODO 支持 Date-String 转换 ？？
        assertNull(carDto.getCreateAtFormat());
        assertNull(carDto.getCreateAtExpressionFormat());

        //        List<CarDto> carDtos = CarConverter.INSTANCE.domain2dto(people);
        List<CarDTO> carDtos = carDozerMapperConverter.domain2dto(cars);
        //        System.out.println(carDtos);
        assertNotNull(carDtos);
    }

    @Test
    public void orika() {
        //when
        //        CarDto carDto = CarConverter.INSTANCE.domain2dto(car);
        CarDTO carDto = carOrikaConverter.domain2dto(car);

        //then
        assertNotNull(carDto);
        assertEquals(car.getMake(), carDto.getMake());
        assertEquals(car.getNumberOfSeats(), carDto.getSeatCount());
        assertEquals(car.getType().name(), carDto.getType());

        assertEquals(car.getLights().length, carDto.getLights().length);
        for (int i = 0; i < car.getLights().length; i++) {
            assertEquals(car.getLights()[i], carDto.getLights()[i]);
        }

        assertEquals(car.getLightList().size(), carDto.getLightList().size());
        for (int i = 0; i < car.getLightList().size(); i++) {
            assertEquals(car.getLightList().get(i), carDto.getLightList().get(i));
        }

        assertEquals(car.getAttributes().size(), carDto.getAttributes().size());
        car.getAttributes().forEach((k, v) -> {
            assertEquals(v, carDto.getAttributes().get(k));
        });

        assertEquals(carDto.getCreateTime(), car.getCreatedAt());
        String format = DateFormatUtils.format(carDto.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
        assertEquals(carDto.getCreateAtFormat(), format);
        assertEquals(carDto.getCreateAtExpressionFormat(), format);

        //        List<CarDto> carDtos = CarConverter.INSTANCE.domain2dto(people);
        List<CarDTO> carDtos = carOrikaConverter.domain2dto(cars);
        //        System.out.println(carDtos);
        assertNotNull(carDtos);
    }

    @Test
    public void jmapper() {
        //when
        //        CarDto carDto = CarConverter.INSTANCE.domain2dto(car);
        CarDTO carDto = carJMapperConverter.domain2dto(car);

        //then
        assertNotNull(carDto);
        assertEquals(car.getMake(), carDto.getMake());
        assertEquals(car.getNumberOfSeats(), carDto.getSeatCount());
        // TODO 支持 enum 类型自动转换 ？？
        assertNotEquals(car.getType().name(), carDto.getType());

        assertEquals(car.getLights().length, carDto.getLights().length);
        for (int i = 0; i < car.getLights().length; i++) {
            assertEquals(car.getLights()[i], carDto.getLights()[i]);
        }

        assertEquals(car.getLightList().size(), carDto.getLightList().size());
        for (int i = 0; i < car.getLightList().size(); i++) {
            assertEquals(car.getLightList().get(i), carDto.getLightList().get(i));
        }

        assertEquals(car.getAttributes().size(), carDto.getAttributes().size());
        car.getAttributes().forEach((k, v) -> {
            assertEquals(v, carDto.getAttributes().get(k));
        });

        assertEquals(carDto.getCreateTime(), car.getCreatedAt());

        // TODO 支持 Date-String 转换 ？？
        assertNull(carDto.getCreateAtFormat());
        assertNull(carDto.getCreateAtExpressionFormat());

        //        List<CarDto> carDtos = CarConverter.INSTANCE.domain2dto(people);
        List<CarDTO> carDtos = carJMapperConverter.domain2dto(cars);
        //        System.out.println(carDtos);
        assertNotNull(carDtos);
    }

    @Test
    public void mapStruct() {
        //when
        //        CarDto carDto = CarConverter.INSTANCE.domain2dto(car);
        CarDTO carDto = carConverter.domain2dto(car);

        //then
        assertNotNull(carDto);
        assertEquals(car.getMake(), carDto.getMake());
        assertEquals(car.getNumberOfSeats(), carDto.getSeatCount());
        assertEquals(car.getType().name(), carDto.getType());

        assertEquals(car.getLights().length, carDto.getLights().length);
        for (int i = 0; i < car.getLights().length; i++) {
            assertEquals(car.getLights()[i], carDto.getLights()[i]);
        }

        assertEquals(car.getLightList().size(), carDto.getLightList().size());
        for (int i = 0; i < car.getLightList().size(); i++) {
            assertEquals(car.getLightList().get(i), carDto.getLightList().get(i));
        }

        assertEquals(car.getAttributes().size(), carDto.getAttributes().size());
        car.getAttributes().forEach((k, v) -> {
            assertEquals(v, carDto.getAttributes().get(k));
        });

        assertEquals(carDto.getCreateTime(), car.getCreatedAt());
        String format = DateFormatUtils.format(carDto.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
        assertEquals(carDto.getCreateAtFormat(), format);
        assertEquals(carDto.getCreateAtExpressionFormat(), format);

        //        List<CarDto> carDtos = CarConverter.INSTANCE.domain2dto(people);
        List<CarDTO> carDtos = carConverter.domain2dto(cars);
        //        System.out.println(carDtos);
        assertNotNull(carDtos);
    }

}
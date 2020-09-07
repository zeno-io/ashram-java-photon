package xyz.flysium.photon.support;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public interface BeanConverter<E, D> {

    D domain2dto(E car);

    default List<D> domain2dto(List<E> cars) {
        return cars.stream().map(this::domain2dto).collect(Collectors.toList());
    }
}

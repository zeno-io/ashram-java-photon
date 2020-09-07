package xyz.flysium.photon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.flysium.photon.dao.entity.Ant;
import xyz.flysium.photon.dao.entity.Point;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AntCloneTest {

    @Test
    public void apacheBeanUtils()
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        //given
        Ant ant = new Ant(1L, new Point(10, 15));

        //when
        Ant copyAnt = (Ant) org.apache.commons.beanutils.BeanUtils.cloneBean(ant);

        //then
        assertNotNull(copyAnt);
        assertEquals(ant.getId(), copyAnt.getId());
        assertEquals(ant.getPoint(), copyAnt.getPoint());
        assertEquals(ant.getPoint().getX(), copyAnt.getPoint().getX());
        assertEquals(ant.getPoint().getY(), copyAnt.getPoint().getY());
    }

    @Test
    public void apachePropertyUtils() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //given
        Ant ant = new Ant(1L, new Point(10, 15));

        //when
        Ant copyAnt = new Ant();
        org.apache.commons.beanutils.PropertyUtils.copyProperties(copyAnt, ant);

        //then
        assertNotNull(copyAnt);
        assertEquals(ant.getId(), copyAnt.getId());
        assertEquals(ant.getPoint(), copyAnt.getPoint());
        assertEquals(ant.getPoint().getX(), copyAnt.getPoint().getX());
        assertEquals(ant.getPoint().getY(), copyAnt.getPoint().getY());
    }

    @Test
    public void springBeanUtils() {
        //given
        Ant ant = new Ant(1L, new Point(10, 15));

        //when
        Ant copyAnt = new Ant();
        org.springframework.beans.BeanUtils.copyProperties(ant, copyAnt);

        //then
        assertNotNull(copyAnt);
        assertEquals(ant.getId(), copyAnt.getId());
        assertEquals(ant.getPoint(), copyAnt.getPoint());
        assertEquals(ant.getPoint().getX(), copyAnt.getPoint().getX());
        assertEquals(ant.getPoint().getY(), copyAnt.getPoint().getY());
    }

    @Test
    public void cglibBeanCopier() {
        //given
        Ant ant = new Ant(1L, new Point(10, 15));

        //when
        Ant copyAnt = new Ant();
        org.springframework.cglib.beans.BeanCopier.create(Ant.class, Ant.class, false).copy(ant, copyAnt, null);

        //then
        assertNotNull(copyAnt);
        assertEquals(ant.getId(), copyAnt.getId());
        assertEquals(ant.getPoint(), copyAnt.getPoint());
        assertEquals(ant.getPoint().getX(), copyAnt.getPoint().getX());
        assertEquals(ant.getPoint().getY(), copyAnt.getPoint().getY());
    }

}
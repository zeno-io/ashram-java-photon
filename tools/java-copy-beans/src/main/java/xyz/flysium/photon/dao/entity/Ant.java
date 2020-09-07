package xyz.flysium.photon.dao.entity;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class Ant {

    private Long id;

    private Point point;

    public Ant() {
    }

    public Ant(Long id, Point point) {
        this.id = id;
        this.point = point;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}

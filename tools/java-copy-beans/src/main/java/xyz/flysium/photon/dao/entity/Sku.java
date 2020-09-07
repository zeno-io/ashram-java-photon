package xyz.flysium.photon.dao.entity;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class Sku {

    private Long id;

    private String code;

    private Integer price;

    public Sku() {
    }

    public Sku(Long id, String code, Integer price) {
        this.id = id;
        this.code = code;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

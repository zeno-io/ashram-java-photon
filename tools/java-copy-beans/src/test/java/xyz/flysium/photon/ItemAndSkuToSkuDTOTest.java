package xyz.flysium.photon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.flysium.photon.dao.entity.Item;
import xyz.flysium.photon.dao.entity.Sku;
import xyz.flysium.photon.dto.SkuDTO;
import xyz.flysium.photon.support.mapstruct.ItemConverter;

/**
 *
 * 多对一测试
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemAndSkuToSkuDTOTest {

    @Autowired
    private ItemConverter itemConverter;

    // MapStruct 可以将几种类型的对象映射为另外一种类型，比如将多个 DO 对象转换为 DTO
    @Test
    public void mapStruct() {
        //given
        Item item = new Item(1L, "iPhone X");
        Sku sku = new Sku(2L, "phone12345", 1000000);

        //when
        //        SkuDTO skuDTO = ItemConverter.INSTANCE.domain2dto(item, sku);
        SkuDTO skuDTO = itemConverter.domain2dto(item, sku);

        //then
        assertNotNull(skuDTO);
        assertEquals(skuDTO.getSkuId(), sku.getId());
        assertEquals(skuDTO.getSkuCode(), sku.getCode());
        assertEquals(skuDTO.getSkuPrice(), sku.getPrice());
        assertEquals(skuDTO.getItemId(), item.getId());
        assertEquals(skuDTO.getItemName(), item.getTitle());
    }
}

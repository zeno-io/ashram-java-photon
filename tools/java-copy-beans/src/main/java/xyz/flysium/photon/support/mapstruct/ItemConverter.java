package xyz.flysium.photon.support.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import xyz.flysium.photon.dao.entity.Item;
import xyz.flysium.photon.dao.entity.Sku;
import xyz.flysium.photon.dto.SkuDTO;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
// @Mapper
@Mapper(componentModel = "spring")
public interface ItemConverter {

    //    ItemConverter INSTANCE = Mappers.getMapper(ItemConverter.class);

    @Mappings({ @Mapping(source = "sku.id", target = "skuId"), @Mapping(source = "sku.code", target = "skuCode"),
        @Mapping(source = "sku.price", target = "skuPrice"), @Mapping(source = "item.id", target = "itemId"),
        @Mapping(source = "item.title", target = "itemName")
    })
    SkuDTO domain2dto(Item item, Sku sku);
}
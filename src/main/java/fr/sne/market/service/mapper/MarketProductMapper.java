package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.MarketProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MarketProduct and its DTO MarketProductDTO.
 */
@Mapper(componentModel = "spring", uses = {MarketOrderlineMapper.class, MarketProductCategoryMapper.class, })
public interface MarketProductMapper extends EntityMapper <MarketProductDTO, MarketProduct> {

    @Mapping(source = "marketOrderline.id", target = "marketOrderlineId")

    @Mapping(source = "category.id", target = "categoryId")
    MarketProductDTO toDto(MarketProduct marketProduct); 

    @Mapping(source = "marketOrderlineId", target = "marketOrderline")
    @Mapping(target = "attributes", ignore = true)

    @Mapping(source = "categoryId", target = "category")
    MarketProduct toEntity(MarketProductDTO marketProductDTO); 
    default MarketProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarketProduct marketProduct = new MarketProduct();
        marketProduct.setId(id);
        return marketProduct;
    }
}

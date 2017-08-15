package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.MarketProductAttributesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MarketProductAttributes and its DTO MarketProductAttributesDTO.
 */
@Mapper(componentModel = "spring", uses = {MarketProductMapper.class, })
public interface MarketProductAttributesMapper extends EntityMapper <MarketProductAttributesDTO, MarketProductAttributes> {

    @Mapping(source = "marketProduct.id", target = "marketProductId")
    MarketProductAttributesDTO toDto(MarketProductAttributes marketProductAttributes); 

    @Mapping(source = "marketProductId", target = "marketProduct")
    MarketProductAttributes toEntity(MarketProductAttributesDTO marketProductAttributesDTO); 
    default MarketProductAttributes fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarketProductAttributes marketProductAttributes = new MarketProductAttributes();
        marketProductAttributes.setId(id);
        return marketProductAttributes;
    }
}

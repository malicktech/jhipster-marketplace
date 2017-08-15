package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.MarketProductCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MarketProductCategory and its DTO MarketProductCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MarketProductCategoryMapper extends EntityMapper <MarketProductCategoryDTO, MarketProductCategory> {
    
    
    default MarketProductCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarketProductCategory marketProductCategory = new MarketProductCategory();
        marketProductCategory.setId(id);
        return marketProductCategory;
    }
}

package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.MarketOrderItemsDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MarketOrderItemsDetails and its DTO MarketOrderItemsDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {MarketOrderlineMapper.class, })
public interface MarketOrderItemsDetailsMapper extends EntityMapper <MarketOrderItemsDetailsDTO, MarketOrderItemsDetails> {

    @Mapping(source = "marketOrderline.id", target = "marketOrderlineId")
    MarketOrderItemsDetailsDTO toDto(MarketOrderItemsDetails marketOrderItemsDetails); 

    @Mapping(source = "marketOrderlineId", target = "marketOrderline")
    MarketOrderItemsDetails toEntity(MarketOrderItemsDetailsDTO marketOrderItemsDetailsDTO); 
    default MarketOrderItemsDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarketOrderItemsDetails marketOrderItemsDetails = new MarketOrderItemsDetails();
        marketOrderItemsDetails.setId(id);
        return marketOrderItemsDetails;
    }
}

package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.*;
import fr.sne.marketplace.service.dto.MarketOrderItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MarketOrderItems and its DTO MarketOrderItemsDTO.
 */
@Mapper(componentModel = "spring", uses = {MarketOrderMapper.class, })
public interface MarketOrderItemsMapper extends EntityMapper <MarketOrderItemsDTO, MarketOrderItems> {
    @Mapping(source = "marketOrder.id", target = "marketOrderId")
    @Mapping(source = "order.id", target = "orderId")
    MarketOrderItemsDTO toDto(MarketOrderItems marketOrderItems); 
    @Mapping(source = "marketOrderId", target = "marketOrder")
    @Mapping(source = "orderId", target = "order")
    MarketOrderItems toEntity(MarketOrderItemsDTO marketOrderItemsDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default MarketOrderItems fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarketOrderItems marketOrderItems = new MarketOrderItems();
        marketOrderItems.setId(id);
        return marketOrderItems;
    }
}

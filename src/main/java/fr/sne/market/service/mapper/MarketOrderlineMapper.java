package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.MarketOrderlineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MarketOrderline and its DTO MarketOrderlineDTO.
 */
@Mapper(componentModel = "spring", uses = {MarketOrdersMapper.class, })
public interface MarketOrderlineMapper extends EntityMapper <MarketOrderlineDTO, MarketOrderline> {

    @Mapping(source = "marketOrders.id", target = "marketOrdersId")
    MarketOrderlineDTO toDto(MarketOrderline marketOrderline); 

    @Mapping(source = "marketOrdersId", target = "marketOrders")
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "details", ignore = true)
    MarketOrderline toEntity(MarketOrderlineDTO marketOrderlineDTO); 
    default MarketOrderline fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarketOrderline marketOrderline = new MarketOrderline();
        marketOrderline.setId(id);
        return marketOrderline;
    }
}

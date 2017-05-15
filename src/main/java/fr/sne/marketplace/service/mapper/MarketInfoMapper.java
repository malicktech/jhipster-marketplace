package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.*;
import fr.sne.marketplace.service.dto.MarketInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MarketInfo and its DTO MarketInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {MarketMapper.class, })
public interface MarketInfoMapper extends EntityMapper <MarketInfoDTO, MarketInfo> {
    @Mapping(source = "market.id", target = "marketId")
    MarketInfoDTO toDto(MarketInfo marketInfo); 
    @Mapping(source = "marketId", target = "market")
    MarketInfo toEntity(MarketInfoDTO marketInfoDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default MarketInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarketInfo marketInfo = new MarketInfo();
        marketInfo.setId(id);
        return marketInfo;
    }
}

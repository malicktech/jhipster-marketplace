package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.MarketInfoDTO;

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
    default MarketInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarketInfo marketInfo = new MarketInfo();
        marketInfo.setId(id);
        return marketInfo;
    }
}

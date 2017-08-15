package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.ShipmentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Shipments and its DTO ShipmentsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShipmentsMapper extends EntityMapper <ShipmentsDTO, Shipments> {
    
    @Mapping(target = "order", ignore = true)
    Shipments toEntity(ShipmentsDTO shipmentsDTO); 
    default Shipments fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shipments shipments = new Shipments();
        shipments.setId(id);
        return shipments;
    }
}

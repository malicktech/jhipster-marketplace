package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.*;
import fr.sne.marketplace.service.dto.MarketOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MarketOrder and its DTO MarketOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {PaymentMapper.class, CustomerMapper.class, })
public interface MarketOrderMapper extends EntityMapper <MarketOrderDTO, MarketOrder> {
    @Mapping(source = "payment.id", target = "paymentId")
    @Mapping(source = "customer.id", target = "customerId")
    MarketOrderDTO toDto(MarketOrder marketOrder); 
    @Mapping(source = "paymentId", target = "payment")
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "quantities", ignore = true)
    @Mapping(source = "customerId", target = "customer")
    MarketOrder toEntity(MarketOrderDTO marketOrderDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default MarketOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setId(id);
        return marketOrder;
    }
}

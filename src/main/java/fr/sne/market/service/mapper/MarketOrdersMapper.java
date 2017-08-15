package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.MarketOrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MarketOrders and its DTO MarketOrdersDTO.
 */
@Mapper(componentModel = "spring", uses = {PaymentMapper.class, ShipmentsMapper.class, CustomerMapper.class, })
public interface MarketOrdersMapper extends EntityMapper <MarketOrdersDTO, MarketOrders> {

    @Mapping(source = "payment.id", target = "paymentId")

    @Mapping(source = "shipper.id", target = "shipperId")

    @Mapping(source = "customer.id", target = "customerId")
    MarketOrdersDTO toDto(MarketOrders marketOrders); 

    @Mapping(source = "paymentId", target = "payment")

    @Mapping(source = "shipperId", target = "shipper")
    @Mapping(target = "quantities", ignore = true)

    @Mapping(source = "customerId", target = "customer")
    MarketOrders toEntity(MarketOrdersDTO marketOrdersDTO); 
    default MarketOrders fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarketOrders marketOrders = new MarketOrders();
        marketOrders.setId(id);
        return marketOrders;
    }
}

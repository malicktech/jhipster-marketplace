package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.CustomerAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustomerAddress and its DTO CustomerAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class, CustomerMapper.class, })
public interface CustomerAddressMapper extends EntityMapper <CustomerAddressDTO, CustomerAddress> {

    @Mapping(source = "country.id", target = "countryId")

    @Mapping(source = "customer.id", target = "customerId")
    CustomerAddressDTO toDto(CustomerAddress customerAddress); 

    @Mapping(source = "countryId", target = "country")

    @Mapping(source = "customerId", target = "customer")
    CustomerAddress toEntity(CustomerAddressDTO customerAddressDTO); 
    default CustomerAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setId(id);
        return customerAddress;
    }
}

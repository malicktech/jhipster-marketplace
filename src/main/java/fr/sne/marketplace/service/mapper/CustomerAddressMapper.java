package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.*;
import fr.sne.marketplace.service.dto.CustomerAddressDTO;

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
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default CustomerAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setId(id);
        return customerAddress;
    }
}

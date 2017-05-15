package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.*;
import fr.sne.marketplace.service.dto.CountryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Country and its DTO CountryDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class, })
public interface CountryMapper extends EntityMapper <CountryDTO, Country> {
    @Mapping(source = "region.id", target = "regionId")
    CountryDTO toDto(Country country); 
    @Mapping(source = "regionId", target = "region")
    Country toEntity(CountryDTO countryDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Country fromId(Long id) {
        if (id == null) {
            return null;
        }
        Country country = new Country();
        country.setId(id);
        return country;
    }
}

package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.Country;

import fr.sne.marketplace.domain.Region;

import fr.sne.marketplace.service.dto.CountryDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-05-15T15:16:20+0000",

    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_121 (Oracle Corporation)"

)

@Component

public class CountryMapperImpl implements CountryMapper {

    @Autowired

    private RegionMapper regionMapper;

    @Override

    public List<Country> toEntity(List<CountryDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Country> list = new ArrayList<Country>();

        for ( CountryDTO countryDTO : dtoList ) {

            list.add( toEntity( countryDTO ) );
        }

        return list;
    }

    @Override

    public List<CountryDTO> toDto(List<Country> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<CountryDTO> list = new ArrayList<CountryDTO>();

        for ( Country country : entityList ) {

            list.add( toDto( country ) );
        }

        return list;
    }

    @Override

    public CountryDTO toDto(Country country) {

        if ( country == null ) {

            return null;
        }

        CountryDTO countryDTO_ = new CountryDTO();

        countryDTO_.setRegionId( countryRegionId( country ) );

        countryDTO_.setId( country.getId() );

        countryDTO_.setCountryName( country.getCountryName() );

        return countryDTO_;
    }

    @Override

    public Country toEntity(CountryDTO countryDTO) {

        if ( countryDTO == null ) {

            return null;
        }

        Country country_ = new Country();

        country_.setRegion( regionMapper.fromId( countryDTO.getRegionId() ) );

        country_.setId( countryDTO.getId() );

        country_.setCountryName( countryDTO.getCountryName() );

        return country_;
    }

    private Long countryRegionId(Country country) {

        if ( country == null ) {

            return null;
        }

        Region region = country.getRegion();

        if ( region == null ) {

            return null;
        }

        Long id = region.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}


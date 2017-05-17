package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.Region;

import fr.sne.marketplace.service.dto.RegionDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-05-17T16:31:43+0000",

    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_121 (Oracle Corporation)"

)

@Component

public class RegionMapperImpl implements RegionMapper {

    @Override

    public Region toEntity(RegionDTO dto) {

        if ( dto == null ) {

            return null;
        }

        Region region = new Region();

        region.setId( dto.getId() );

        region.setRegionName( dto.getRegionName() );

        return region;
    }

    @Override

    public RegionDTO toDto(Region entity) {

        if ( entity == null ) {

            return null;
        }

        RegionDTO regionDTO = new RegionDTO();

        regionDTO.setId( entity.getId() );

        regionDTO.setRegionName( entity.getRegionName() );

        return regionDTO;
    }

    @Override

    public List<Region> toEntity(List<RegionDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Region> list = new ArrayList<Region>();

        for ( RegionDTO regionDTO : dtoList ) {

            list.add( toEntity( regionDTO ) );
        }

        return list;
    }

    @Override

    public List<RegionDTO> toDto(List<Region> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<RegionDTO> list = new ArrayList<RegionDTO>();

        for ( Region region : entityList ) {

            list.add( toDto( region ) );
        }

        return list;
    }
}


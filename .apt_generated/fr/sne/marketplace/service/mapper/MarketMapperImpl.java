package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.Market;

import fr.sne.marketplace.service.dto.MarketDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-05-15T15:16:20+0000",

    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_121 (Oracle Corporation)"

)

@Component

public class MarketMapperImpl implements MarketMapper {

    @Override

    public MarketDTO toDto(Market entity) {

        if ( entity == null ) {

            return null;
        }

        MarketDTO marketDTO = new MarketDTO();

        marketDTO.setId( entity.getId() );

        marketDTO.setName( entity.getName() );

        marketDTO.setContent( entity.getContent() );

        return marketDTO;
    }

    @Override

    public List<Market> toEntity(List<MarketDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Market> list = new ArrayList<Market>();

        for ( MarketDTO marketDTO : dtoList ) {

            list.add( toEntity( marketDTO ) );
        }

        return list;
    }

    @Override

    public List<MarketDTO> toDto(List<Market> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<MarketDTO> list = new ArrayList<MarketDTO>();

        for ( Market market : entityList ) {

            list.add( toDto( market ) );
        }

        return list;
    }

    @Override

    public Market toEntity(MarketDTO marketDTO) {

        if ( marketDTO == null ) {

            return null;
        }

        Market market_ = new Market();

        market_.setId( marketDTO.getId() );

        market_.setName( marketDTO.getName() );

        market_.setContent( marketDTO.getContent() );

        return market_;
    }
}


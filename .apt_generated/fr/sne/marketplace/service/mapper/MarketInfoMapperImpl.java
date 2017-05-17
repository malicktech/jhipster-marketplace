package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.Market;

import fr.sne.marketplace.domain.MarketInfo;

import fr.sne.marketplace.service.dto.MarketInfoDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-05-17T16:31:43+0000",

    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_121 (Oracle Corporation)"

)

@Component

public class MarketInfoMapperImpl implements MarketInfoMapper {

    @Autowired

    private MarketMapper marketMapper;

    @Override

    public List<MarketInfo> toEntity(List<MarketInfoDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<MarketInfo> list = new ArrayList<MarketInfo>();

        for ( MarketInfoDTO marketInfoDTO : dtoList ) {

            list.add( toEntity( marketInfoDTO ) );
        }

        return list;
    }

    @Override

    public List<MarketInfoDTO> toDto(List<MarketInfo> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<MarketInfoDTO> list = new ArrayList<MarketInfoDTO>();

        for ( MarketInfo marketInfo : entityList ) {

            list.add( toDto( marketInfo ) );
        }

        return list;
    }

    @Override

    public MarketInfoDTO toDto(MarketInfo marketInfo) {

        if ( marketInfo == null ) {

            return null;
        }

        MarketInfoDTO marketInfoDTO_ = new MarketInfoDTO();

        marketInfoDTO_.setMarketId( marketInfoMarketId( marketInfo ) );

        marketInfoDTO_.setId( marketInfo.getId() );

        marketInfoDTO_.setKey( marketInfo.getKey() );

        marketInfoDTO_.setValue( marketInfo.getValue() );

        return marketInfoDTO_;
    }

    @Override

    public MarketInfo toEntity(MarketInfoDTO marketInfoDTO) {

        if ( marketInfoDTO == null ) {

            return null;
        }

        MarketInfo marketInfo_ = new MarketInfo();

        marketInfo_.setMarket( marketMapper.fromId( marketInfoDTO.getMarketId() ) );

        marketInfo_.setId( marketInfoDTO.getId() );

        marketInfo_.setKey( marketInfoDTO.getKey() );

        marketInfo_.setValue( marketInfoDTO.getValue() );

        return marketInfo_;
    }

    private Long marketInfoMarketId(MarketInfo marketInfo) {

        if ( marketInfo == null ) {

            return null;
        }

        Market market = marketInfo.getMarket();

        if ( market == null ) {

            return null;
        }

        Long id = market.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}


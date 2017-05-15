package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.MarketOrder;

import fr.sne.marketplace.domain.MarketOrderItems;

import fr.sne.marketplace.service.dto.MarketOrderItemsDTO;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-05-15T15:16:19+0000",

    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_121 (Oracle Corporation)"

)

@Component

public class MarketOrderItemsMapperImpl implements MarketOrderItemsMapper {

    @Autowired

    private MarketOrderMapper marketOrderMapper;

    @Override

    public List<MarketOrderItems> toEntity(List<MarketOrderItemsDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<MarketOrderItems> list = new ArrayList<MarketOrderItems>();

        for ( MarketOrderItemsDTO marketOrderItemsDTO : dtoList ) {

            list.add( toEntity( marketOrderItemsDTO ) );
        }

        return list;
    }

    @Override

    public List<MarketOrderItemsDTO> toDto(List<MarketOrderItems> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<MarketOrderItemsDTO> list = new ArrayList<MarketOrderItemsDTO>();

        for ( MarketOrderItems marketOrderItems : entityList ) {

            list.add( toDto( marketOrderItems ) );
        }

        return list;
    }

    @Override

    public MarketOrderItemsDTO toDto(MarketOrderItems marketOrderItems) {

        if ( marketOrderItems == null ) {

            return null;
        }

        MarketOrderItemsDTO marketOrderItemsDTO_ = new MarketOrderItemsDTO();

        marketOrderItemsDTO_.setOrderId( marketOrderItemsOrderId( marketOrderItems ) );

        marketOrderItemsDTO_.setMarketOrderId( marketOrderItemsMarketOrderId( marketOrderItems ) );

        marketOrderItemsDTO_.setId( marketOrderItems.getId() );

        marketOrderItemsDTO_.setTitle( marketOrderItems.getTitle() );

        marketOrderItemsDTO_.setDescription( marketOrderItems.getDescription() );

        marketOrderItemsDTO_.setPrice( marketOrderItems.getPrice() );

        if ( marketOrderItems.getImg() != null ) {

            byte[] img = marketOrderItems.getImg();

            marketOrderItemsDTO_.setImg( Arrays.copyOf( img, img.length ) );
        }

        marketOrderItemsDTO_.setImgContentType( marketOrderItems.getImgContentType() );

        marketOrderItemsDTO_.setName( marketOrderItems.getName() );

        return marketOrderItemsDTO_;
    }

    @Override

    public MarketOrderItems toEntity(MarketOrderItemsDTO marketOrderItemsDTO) {

        if ( marketOrderItemsDTO == null ) {

            return null;
        }

        MarketOrderItems marketOrderItems_ = new MarketOrderItems();

        marketOrderItems_.setMarketOrder( marketOrderMapper.fromId( marketOrderItemsDTO.getMarketOrderId() ) );

        marketOrderItems_.setOrder( marketOrderMapper.fromId( marketOrderItemsDTO.getOrderId() ) );

        marketOrderItems_.setId( marketOrderItemsDTO.getId() );

        marketOrderItems_.setTitle( marketOrderItemsDTO.getTitle() );

        marketOrderItems_.setDescription( marketOrderItemsDTO.getDescription() );

        marketOrderItems_.setPrice( marketOrderItemsDTO.getPrice() );

        if ( marketOrderItemsDTO.getImg() != null ) {

            byte[] img = marketOrderItemsDTO.getImg();

            marketOrderItems_.setImg( Arrays.copyOf( img, img.length ) );
        }

        marketOrderItems_.setImgContentType( marketOrderItemsDTO.getImgContentType() );

        marketOrderItems_.setName( marketOrderItemsDTO.getName() );

        return marketOrderItems_;
    }

    private Long marketOrderItemsOrderId(MarketOrderItems marketOrderItems) {

        if ( marketOrderItems == null ) {

            return null;
        }

        MarketOrder order = marketOrderItems.getOrder();

        if ( order == null ) {

            return null;
        }

        Long id = order.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long marketOrderItemsMarketOrderId(MarketOrderItems marketOrderItems) {

        if ( marketOrderItems == null ) {

            return null;
        }

        MarketOrder marketOrder = marketOrderItems.getMarketOrder();

        if ( marketOrder == null ) {

            return null;
        }

        Long id = marketOrder.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}


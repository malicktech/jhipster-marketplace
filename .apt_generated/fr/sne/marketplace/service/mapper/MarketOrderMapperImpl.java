package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.Customer;

import fr.sne.marketplace.domain.MarketOrder;

import fr.sne.marketplace.domain.Payment;

import fr.sne.marketplace.service.dto.MarketOrderDTO;

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

public class MarketOrderMapperImpl implements MarketOrderMapper {

    @Autowired

    private PaymentMapper paymentMapper;

    @Autowired

    private CustomerMapper customerMapper;

    @Override

    public List<MarketOrder> toEntity(List<MarketOrderDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<MarketOrder> list = new ArrayList<MarketOrder>();

        for ( MarketOrderDTO marketOrderDTO : dtoList ) {

            list.add( toEntity( marketOrderDTO ) );
        }

        return list;
    }

    @Override

    public List<MarketOrderDTO> toDto(List<MarketOrder> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<MarketOrderDTO> list = new ArrayList<MarketOrderDTO>();

        for ( MarketOrder marketOrder : entityList ) {

            list.add( toDto( marketOrder ) );
        }

        return list;
    }

    @Override

    public MarketOrderDTO toDto(MarketOrder marketOrder) {

        if ( marketOrder == null ) {

            return null;
        }

        MarketOrderDTO marketOrderDTO_ = new MarketOrderDTO();

        marketOrderDTO_.setCustomerId( marketOrderCustomerId( marketOrder ) );

        marketOrderDTO_.setPaymentId( marketOrderPaymentId( marketOrder ) );

        marketOrderDTO_.setId( marketOrder.getId() );

        marketOrderDTO_.setOrderDate( marketOrder.getOrderDate() );

        marketOrderDTO_.setDate( marketOrder.getDate() );

        marketOrderDTO_.setStatus( marketOrder.getStatus() );

        marketOrderDTO_.setRemoteVirtualCardId( marketOrder.getRemoteVirtualCardId() );

        marketOrderDTO_.setMarketOrderId( marketOrder.getMarketOrderId() );

        return marketOrderDTO_;
    }

    @Override

    public MarketOrder toEntity(MarketOrderDTO marketOrderDTO) {

        if ( marketOrderDTO == null ) {

            return null;
        }

        MarketOrder marketOrder_ = new MarketOrder();

        marketOrder_.setPayment( paymentMapper.fromId( marketOrderDTO.getPaymentId() ) );

        marketOrder_.setCustomer( customerMapper.fromId( marketOrderDTO.getCustomerId() ) );

        marketOrder_.setId( marketOrderDTO.getId() );

        marketOrder_.setOrderDate( marketOrderDTO.getOrderDate() );

        marketOrder_.setDate( marketOrderDTO.getDate() );

        marketOrder_.setStatus( marketOrderDTO.getStatus() );

        marketOrder_.setRemoteVirtualCardId( marketOrderDTO.getRemoteVirtualCardId() );

        marketOrder_.setMarketOrderId( marketOrderDTO.getMarketOrderId() );

        return marketOrder_;
    }

    private Long marketOrderCustomerId(MarketOrder marketOrder) {

        if ( marketOrder == null ) {

            return null;
        }

        Customer customer = marketOrder.getCustomer();

        if ( customer == null ) {

            return null;
        }

        Long id = customer.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long marketOrderPaymentId(MarketOrder marketOrder) {

        if ( marketOrder == null ) {

            return null;
        }

        Payment payment = marketOrder.getPayment();

        if ( payment == null ) {

            return null;
        }

        Long id = payment.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}


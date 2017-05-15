package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.Payment;

import fr.sne.marketplace.service.dto.PaymentDTO;

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

public class PaymentMapperImpl implements PaymentMapper {

    @Override

    public PaymentDTO toDto(Payment entity) {

        if ( entity == null ) {

            return null;
        }

        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setId( entity.getId() );

        paymentDTO.setDate( entity.getDate() );

        paymentDTO.setAmount( entity.getAmount() );

        return paymentDTO;
    }

    @Override

    public List<Payment> toEntity(List<PaymentDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Payment> list = new ArrayList<Payment>();

        for ( PaymentDTO paymentDTO : dtoList ) {

            list.add( toEntity( paymentDTO ) );
        }

        return list;
    }

    @Override

    public List<PaymentDTO> toDto(List<Payment> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<PaymentDTO> list = new ArrayList<PaymentDTO>();

        for ( Payment payment : entityList ) {

            list.add( toDto( payment ) );
        }

        return list;
    }

    @Override

    public Payment toEntity(PaymentDTO paymentDTO) {

        if ( paymentDTO == null ) {

            return null;
        }

        Payment payment_ = new Payment();

        payment_.setId( paymentDTO.getId() );

        payment_.setDate( paymentDTO.getDate() );

        payment_.setAmount( paymentDTO.getAmount() );

        return payment_;
    }
}


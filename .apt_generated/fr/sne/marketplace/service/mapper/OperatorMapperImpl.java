package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.Operator;

import fr.sne.marketplace.domain.User;

import fr.sne.marketplace.service.dto.OperatorDTO;

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

public class OperatorMapperImpl implements OperatorMapper {

    @Autowired

    private UserMapper userMapper;

    @Override

    public List<Operator> toEntity(List<OperatorDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Operator> list = new ArrayList<Operator>();

        for ( OperatorDTO operatorDTO : dtoList ) {

            list.add( toEntity( operatorDTO ) );
        }

        return list;
    }

    @Override

    public List<OperatorDTO> toDto(List<Operator> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<OperatorDTO> list = new ArrayList<OperatorDTO>();

        for ( Operator operator : entityList ) {

            list.add( toDto( operator ) );
        }

        return list;
    }

    @Override

    public OperatorDTO toDto(Operator operator) {

        if ( operator == null ) {

            return null;
        }

        OperatorDTO operatorDTO_ = new OperatorDTO();

        operatorDTO_.setUserId( operatorUserId( operator ) );

        operatorDTO_.setId( operator.getId() );

        operatorDTO_.setFirstName( operator.getFirstName() );

        operatorDTO_.setLastName( operator.getLastName() );

        operatorDTO_.setEmail( operator.getEmail() );

        operatorDTO_.setPhoneNumber( operator.getPhoneNumber() );

        operatorDTO_.setHireDate( operator.getHireDate() );

        operatorDTO_.setGender( operator.getGender() );

        return operatorDTO_;
    }

    @Override

    public Operator toEntity(OperatorDTO operatorDTO) {

        if ( operatorDTO == null ) {

            return null;
        }

        Operator operator_ = new Operator();

        operator_.setUser( userMapper.userFromId( operatorDTO.getUserId() ) );

        operator_.setEmail( operatorDTO.getEmail() );

        operator_.setFirstName( operatorDTO.getFirstName() );

        operator_.setGender( operatorDTO.getGender() );

        operator_.setHireDate( operatorDTO.getHireDate() );

        operator_.setId( operatorDTO.getId() );

        operator_.setLastName( operatorDTO.getLastName() );

        operator_.setPhoneNumber( operatorDTO.getPhoneNumber() );

        return operator_;
    }

    private Long operatorUserId(Operator operator) {

        if ( operator == null ) {

            return null;
        }

        User user = operator.getUser();

        if ( user == null ) {

            return null;
        }

        Long id = user.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}


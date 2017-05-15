package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.Customer;

import fr.sne.marketplace.domain.User;

import fr.sne.marketplace.service.dto.CustomerDTO;

import java.util.ArrayList;

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

public class CustomerMapperImpl implements CustomerMapper {

    @Autowired

    private UserMapper userMapper;

    @Override

    public List<Customer> toEntity(List<CustomerDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Customer> list = new ArrayList<Customer>();

        for ( CustomerDTO customerDTO : dtoList ) {

            list.add( toEntity( customerDTO ) );
        }

        return list;
    }

    @Override

    public List<CustomerDTO> toDto(List<Customer> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<CustomerDTO> list = new ArrayList<CustomerDTO>();

        for ( Customer customer : entityList ) {

            list.add( toDto( customer ) );
        }

        return list;
    }

    @Override

    public CustomerDTO toDto(Customer customer) {

        if ( customer == null ) {

            return null;
        }

        CustomerDTO customerDTO_ = new CustomerDTO();

        customerDTO_.setUserId( customerUserId( customer ) );

        customerDTO_.setId( customer.getId() );

        customerDTO_.setTelephone( customer.getTelephone() );

        customerDTO_.setGender( customer.getGender() );

        customerDTO_.setDateOfBirth( customer.getDateOfBirth() );

        return customerDTO_;
    }

    @Override

    public Customer toEntity(CustomerDTO customerDTO) {

        if ( customerDTO == null ) {

            return null;
        }

        Customer customer_ = new Customer();

        customer_.setUser( userMapper.userFromId( customerDTO.getUserId() ) );

        customer_.setId( customerDTO.getId() );

        customer_.setTelephone( customerDTO.getTelephone() );

        customer_.setGender( customerDTO.getGender() );

        customer_.setDateOfBirth( customerDTO.getDateOfBirth() );

        return customer_;
    }

    private Long customerUserId(Customer customer) {

        if ( customer == null ) {

            return null;
        }

        User user = customer.getUser();

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


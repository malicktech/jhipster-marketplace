package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.Country;

import fr.sne.marketplace.domain.Customer;

import fr.sne.marketplace.domain.CustomerAddress;

import fr.sne.marketplace.service.dto.CustomerAddressDTO;

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

public class CustomerAddressMapperImpl implements CustomerAddressMapper {

    @Autowired

    private CountryMapper countryMapper;

    @Autowired

    private CustomerMapper customerMapper;

    @Override

    public List<CustomerAddress> toEntity(List<CustomerAddressDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<CustomerAddress> list = new ArrayList<CustomerAddress>();

        for ( CustomerAddressDTO customerAddressDTO : dtoList ) {

            list.add( toEntity( customerAddressDTO ) );
        }

        return list;
    }

    @Override

    public List<CustomerAddressDTO> toDto(List<CustomerAddress> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<CustomerAddressDTO> list = new ArrayList<CustomerAddressDTO>();

        for ( CustomerAddress customerAddress : entityList ) {

            list.add( toDto( customerAddress ) );
        }

        return list;
    }

    @Override

    public CustomerAddressDTO toDto(CustomerAddress customerAddress) {

        if ( customerAddress == null ) {

            return null;
        }

        CustomerAddressDTO customerAddressDTO_ = new CustomerAddressDTO();

        customerAddressDTO_.setCustomerId( customerAddressCustomerId( customerAddress ) );

        customerAddressDTO_.setCountryId( customerAddressCountryId( customerAddress ) );

        customerAddressDTO_.setId( customerAddress.getId() );

        customerAddressDTO_.setStreetAddress( customerAddress.getStreetAddress() );

        customerAddressDTO_.setCity( customerAddress.getCity() );

        customerAddressDTO_.setPostalCode( customerAddress.getPostalCode() );

        customerAddressDTO_.setStateProvince( customerAddress.getStateProvince() );

        return customerAddressDTO_;
    }

    @Override

    public CustomerAddress toEntity(CustomerAddressDTO customerAddressDTO) {

        if ( customerAddressDTO == null ) {

            return null;
        }

        CustomerAddress customerAddress_ = new CustomerAddress();

        customerAddress_.setCountry( countryMapper.fromId( customerAddressDTO.getCountryId() ) );

        customerAddress_.setCustomer( customerMapper.fromId( customerAddressDTO.getCustomerId() ) );

        customerAddress_.setId( customerAddressDTO.getId() );

        customerAddress_.setStreetAddress( customerAddressDTO.getStreetAddress() );

        customerAddress_.setCity( customerAddressDTO.getCity() );

        customerAddress_.setPostalCode( customerAddressDTO.getPostalCode() );

        customerAddress_.setStateProvince( customerAddressDTO.getStateProvince() );

        return customerAddress_;
    }

    private Long customerAddressCustomerId(CustomerAddress customerAddress) {

        if ( customerAddress == null ) {

            return null;
        }

        Customer customer = customerAddress.getCustomer();

        if ( customer == null ) {

            return null;
        }

        Long id = customer.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long customerAddressCountryId(CustomerAddress customerAddress) {

        if ( customerAddress == null ) {

            return null;
        }

        Country country = customerAddress.getCountry();

        if ( country == null ) {

            return null;
        }

        Long id = country.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}


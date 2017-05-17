package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.Market;

import fr.sne.marketplace.domain.ProductCategory;

import fr.sne.marketplace.service.dto.ProductCategoryDTO;

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

public class ProductCategoryMapperImpl implements ProductCategoryMapper {

    @Autowired

    private MarketMapper marketMapper;

    @Override

    public List<ProductCategory> toEntity(List<ProductCategoryDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<ProductCategory> list = new ArrayList<ProductCategory>();

        for ( ProductCategoryDTO productCategoryDTO : dtoList ) {

            list.add( toEntity( productCategoryDTO ) );
        }

        return list;
    }

    @Override

    public List<ProductCategoryDTO> toDto(List<ProductCategory> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<ProductCategoryDTO> list = new ArrayList<ProductCategoryDTO>();

        for ( ProductCategory productCategory : entityList ) {

            list.add( toDto( productCategory ) );
        }

        return list;
    }

    @Override

    public ProductCategoryDTO toDto(ProductCategory productCategory) {

        if ( productCategory == null ) {

            return null;
        }

        ProductCategoryDTO productCategoryDTO_ = new ProductCategoryDTO();

        productCategoryDTO_.setMarketId( productCategoryMarketId( productCategory ) );

        productCategoryDTO_.setId( productCategory.getId() );

        productCategoryDTO_.setKey( productCategory.getKey() );

        productCategoryDTO_.setValue( productCategory.getValue() );

        productCategoryDTO_.setActivated( productCategory.isActivated() );

        productCategoryDTO_.setDefaultSortOrder( productCategory.getDefaultSortOrder() );

        productCategoryDTO_.setStatus( productCategory.getStatus() );

        return productCategoryDTO_;
    }

    @Override

    public ProductCategory toEntity(ProductCategoryDTO productCategoryDTO) {

        if ( productCategoryDTO == null ) {

            return null;
        }

        ProductCategory productCategory_ = new ProductCategory();

        productCategory_.setMarket( marketMapper.fromId( productCategoryDTO.getMarketId() ) );

        productCategory_.setId( productCategoryDTO.getId() );

        productCategory_.setKey( productCategoryDTO.getKey() );

        productCategory_.setValue( productCategoryDTO.getValue() );

        productCategory_.setActivated( productCategoryDTO.isActivated() );

        productCategory_.setDefaultSortOrder( productCategoryDTO.getDefaultSortOrder() );

        productCategory_.setStatus( productCategoryDTO.getStatus() );

        return productCategory_;
    }

    private Long productCategoryMarketId(ProductCategory productCategory) {

        if ( productCategory == null ) {

            return null;
        }

        Market market = productCategory.getMarket();

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


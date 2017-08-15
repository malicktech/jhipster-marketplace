package fr.sne.market.service.mapper;

import fr.sne.market.domain.*;
import fr.sne.market.service.dto.ProductCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductCategory and its DTO ProductCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {MarketMapper.class, })
public interface ProductCategoryMapper extends EntityMapper <ProductCategoryDTO, ProductCategory> {

    @Mapping(source = "market.id", target = "marketId")
    ProductCategoryDTO toDto(ProductCategory productCategory); 

    @Mapping(source = "marketId", target = "market")
    ProductCategory toEntity(ProductCategoryDTO productCategoryDTO); 
    default ProductCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(id);
        return productCategory;
    }
}

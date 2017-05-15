package fr.sne.marketplace.service.mapper;

import fr.sne.marketplace.domain.*;
import fr.sne.marketplace.service.dto.ProductCategoryDTO;

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
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default ProductCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(id);
        return productCategory;
    }
}

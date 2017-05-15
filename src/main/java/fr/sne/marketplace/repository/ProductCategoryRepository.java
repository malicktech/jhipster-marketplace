package fr.sne.marketplace.repository;

import fr.sne.marketplace.domain.ProductCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductCategory entity.
 */
@SuppressWarnings("unused")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {

}

package fr.sne.market.repository;

import fr.sne.market.domain.MarketProductCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MarketProductCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketProductCategoryRepository extends JpaRepository<MarketProductCategory,Long> {
    
}

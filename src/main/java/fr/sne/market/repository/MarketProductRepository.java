package fr.sne.market.repository;

import fr.sne.market.domain.MarketProduct;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MarketProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketProductRepository extends JpaRepository<MarketProduct,Long> {
    
}

package fr.sne.market.repository;

import fr.sne.market.domain.MarketProductAttributes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MarketProductAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketProductAttributesRepository extends JpaRepository<MarketProductAttributes,Long> {
    
}

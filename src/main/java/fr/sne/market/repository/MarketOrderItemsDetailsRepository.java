package fr.sne.market.repository;

import fr.sne.market.domain.MarketOrderItemsDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MarketOrderItemsDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketOrderItemsDetailsRepository extends JpaRepository<MarketOrderItemsDetails,Long> {
    
}

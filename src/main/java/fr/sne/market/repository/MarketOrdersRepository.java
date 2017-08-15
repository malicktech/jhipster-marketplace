package fr.sne.market.repository;

import fr.sne.market.domain.MarketOrders;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MarketOrders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketOrdersRepository extends JpaRepository<MarketOrders,Long> {
    
}

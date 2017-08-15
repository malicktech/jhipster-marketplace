package fr.sne.market.repository;

import fr.sne.market.domain.MarketOrderline;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MarketOrderline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketOrderlineRepository extends JpaRepository<MarketOrderline,Long> {
    
}

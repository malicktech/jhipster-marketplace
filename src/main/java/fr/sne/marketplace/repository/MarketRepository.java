package fr.sne.marketplace.repository;

import fr.sne.marketplace.domain.Market;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Market entity.
 */
@SuppressWarnings("unused")
public interface MarketRepository extends JpaRepository<Market,Long> {

}

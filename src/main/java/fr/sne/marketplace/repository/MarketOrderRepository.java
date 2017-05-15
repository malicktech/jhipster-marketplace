package fr.sne.marketplace.repository;

import fr.sne.marketplace.domain.MarketOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MarketOrder entity.
 */
@SuppressWarnings("unused")
public interface MarketOrderRepository extends JpaRepository<MarketOrder,Long> {

}

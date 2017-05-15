package fr.sne.marketplace.repository;

import fr.sne.marketplace.domain.MarketOrderItems;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MarketOrderItems entity.
 */
@SuppressWarnings("unused")
public interface MarketOrderItemsRepository extends JpaRepository<MarketOrderItems,Long> {

}

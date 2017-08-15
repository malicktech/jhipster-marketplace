package fr.sne.market.repository;

import fr.sne.market.domain.Shipments;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Shipments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentsRepository extends JpaRepository<Shipments,Long> {
    
}

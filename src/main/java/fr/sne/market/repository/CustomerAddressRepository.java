package fr.sne.market.repository;

import fr.sne.market.domain.CustomerAddress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress,Long> {
    
}

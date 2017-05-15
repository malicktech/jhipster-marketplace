package fr.sne.marketplace.repository;

import fr.sne.marketplace.domain.CustomerAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerAddress entity.
 */
@SuppressWarnings("unused")
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress,Long> {

}

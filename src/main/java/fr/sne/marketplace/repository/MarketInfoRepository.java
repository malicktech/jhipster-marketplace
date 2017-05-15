package fr.sne.marketplace.repository;

import fr.sne.marketplace.domain.MarketInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MarketInfo entity.
 */
@SuppressWarnings("unused")
public interface MarketInfoRepository extends JpaRepository<MarketInfo,Long> {

}

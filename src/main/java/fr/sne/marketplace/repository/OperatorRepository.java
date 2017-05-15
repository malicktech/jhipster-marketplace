package fr.sne.marketplace.repository;

import fr.sne.marketplace.domain.Operator;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Operator entity.
 */
@SuppressWarnings("unused")
public interface OperatorRepository extends JpaRepository<Operator,Long> {

}

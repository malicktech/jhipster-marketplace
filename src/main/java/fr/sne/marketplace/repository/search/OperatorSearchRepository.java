package fr.sne.marketplace.repository.search;

import fr.sne.marketplace.domain.Operator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Operator entity.
 */
public interface OperatorSearchRepository extends ElasticsearchRepository<Operator, Long> {
}

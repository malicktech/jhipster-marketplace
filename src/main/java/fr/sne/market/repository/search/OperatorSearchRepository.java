package fr.sne.market.repository.search;

import fr.sne.market.domain.Operator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Operator entity.
 */
public interface OperatorSearchRepository extends ElasticsearchRepository<Operator, Long> {
}

package fr.sne.marketplace.repository.search;

import fr.sne.marketplace.domain.MarketOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MarketOrder entity.
 */
public interface MarketOrderSearchRepository extends ElasticsearchRepository<MarketOrder, Long> {
}

package fr.sne.marketplace.repository.search;

import fr.sne.marketplace.domain.MarketOrderItems;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MarketOrderItems entity.
 */
public interface MarketOrderItemsSearchRepository extends ElasticsearchRepository<MarketOrderItems, Long> {
}

package fr.sne.market.repository.search;

import fr.sne.market.domain.MarketOrders;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MarketOrders entity.
 */
public interface MarketOrdersSearchRepository extends ElasticsearchRepository<MarketOrders, Long> {
}

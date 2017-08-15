package fr.sne.market.repository.search;

import fr.sne.market.domain.MarketOrderItemsDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MarketOrderItemsDetails entity.
 */
public interface MarketOrderItemsDetailsSearchRepository extends ElasticsearchRepository<MarketOrderItemsDetails, Long> {
}

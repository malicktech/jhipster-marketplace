package fr.sne.market.repository.search;

import fr.sne.market.domain.Market;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Market entity.
 */
public interface MarketSearchRepository extends ElasticsearchRepository<Market, Long> {
}

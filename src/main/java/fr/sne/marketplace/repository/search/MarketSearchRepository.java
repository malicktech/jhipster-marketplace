package fr.sne.marketplace.repository.search;

import fr.sne.marketplace.domain.Market;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Market entity.
 */
public interface MarketSearchRepository extends ElasticsearchRepository<Market, Long> {
}

package fr.sne.market.repository.search;

import fr.sne.market.domain.MarketOrderline;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MarketOrderline entity.
 */
public interface MarketOrderlineSearchRepository extends ElasticsearchRepository<MarketOrderline, Long> {
}

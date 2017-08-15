package fr.sne.market.repository.search;

import fr.sne.market.domain.MarketInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MarketInfo entity.
 */
public interface MarketInfoSearchRepository extends ElasticsearchRepository<MarketInfo, Long> {
}

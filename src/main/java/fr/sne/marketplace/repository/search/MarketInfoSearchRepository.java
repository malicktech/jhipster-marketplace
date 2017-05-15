package fr.sne.marketplace.repository.search;

import fr.sne.marketplace.domain.MarketInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MarketInfo entity.
 */
public interface MarketInfoSearchRepository extends ElasticsearchRepository<MarketInfo, Long> {
}

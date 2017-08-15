package fr.sne.market.repository.search;

import fr.sne.market.domain.MarketProductAttributes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MarketProductAttributes entity.
 */
public interface MarketProductAttributesSearchRepository extends ElasticsearchRepository<MarketProductAttributes, Long> {
}

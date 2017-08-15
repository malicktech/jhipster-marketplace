package fr.sne.market.repository.search;

import fr.sne.market.domain.MarketProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MarketProduct entity.
 */
public interface MarketProductSearchRepository extends ElasticsearchRepository<MarketProduct, Long> {
}

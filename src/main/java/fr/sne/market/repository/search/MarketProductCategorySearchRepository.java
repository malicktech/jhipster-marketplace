package fr.sne.market.repository.search;

import fr.sne.market.domain.MarketProductCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MarketProductCategory entity.
 */
public interface MarketProductCategorySearchRepository extends ElasticsearchRepository<MarketProductCategory, Long> {
}

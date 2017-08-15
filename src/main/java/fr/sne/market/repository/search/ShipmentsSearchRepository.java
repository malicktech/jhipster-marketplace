package fr.sne.market.repository.search;

import fr.sne.market.domain.Shipments;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Shipments entity.
 */
public interface ShipmentsSearchRepository extends ElasticsearchRepository<Shipments, Long> {
}

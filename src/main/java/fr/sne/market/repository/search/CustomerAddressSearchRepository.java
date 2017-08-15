package fr.sne.market.repository.search;

import fr.sne.market.domain.CustomerAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerAddress entity.
 */
public interface CustomerAddressSearchRepository extends ElasticsearchRepository<CustomerAddress, Long> {
}

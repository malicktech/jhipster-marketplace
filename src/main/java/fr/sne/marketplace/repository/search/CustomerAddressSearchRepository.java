package fr.sne.marketplace.repository.search;

import fr.sne.marketplace.domain.CustomerAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerAddress entity.
 */
public interface CustomerAddressSearchRepository extends ElasticsearchRepository<CustomerAddress, Long> {
}

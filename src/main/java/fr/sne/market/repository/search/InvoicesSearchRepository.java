package fr.sne.market.repository.search;

import fr.sne.market.domain.Invoices;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Invoices entity.
 */
public interface InvoicesSearchRepository extends ElasticsearchRepository<Invoices, Long> {
}

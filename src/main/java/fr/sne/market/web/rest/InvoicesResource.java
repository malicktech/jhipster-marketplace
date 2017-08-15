package fr.sne.market.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.market.domain.Invoices;

import fr.sne.market.repository.InvoicesRepository;
import fr.sne.market.repository.search.InvoicesSearchRepository;
import fr.sne.market.web.rest.util.HeaderUtil;
import fr.sne.market.service.dto.InvoicesDTO;
import fr.sne.market.service.mapper.InvoicesMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Invoices.
 */
@RestController
@RequestMapping("/api")
public class InvoicesResource {

    private final Logger log = LoggerFactory.getLogger(InvoicesResource.class);

    private static final String ENTITY_NAME = "invoices";

    private final InvoicesRepository invoicesRepository;

    private final InvoicesMapper invoicesMapper;

    private final InvoicesSearchRepository invoicesSearchRepository;

    public InvoicesResource(InvoicesRepository invoicesRepository, InvoicesMapper invoicesMapper, InvoicesSearchRepository invoicesSearchRepository) {
        this.invoicesRepository = invoicesRepository;
        this.invoicesMapper = invoicesMapper;
        this.invoicesSearchRepository = invoicesSearchRepository;
    }

    /**
     * POST  /invoices : Create a new invoices.
     *
     * @param invoicesDTO the invoicesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoicesDTO, or with status 400 (Bad Request) if the invoices has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoices")
    @Timed
    public ResponseEntity<InvoicesDTO> createInvoices(@RequestBody InvoicesDTO invoicesDTO) throws URISyntaxException {
        log.debug("REST request to save Invoices : {}", invoicesDTO);
        if (invoicesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new invoices cannot already have an ID")).body(null);
        }
        Invoices invoices = invoicesMapper.toEntity(invoicesDTO);
        invoices = invoicesRepository.save(invoices);
        InvoicesDTO result = invoicesMapper.toDto(invoices);
        invoicesSearchRepository.save(invoices);
        return ResponseEntity.created(new URI("/api/invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoices : Updates an existing invoices.
     *
     * @param invoicesDTO the invoicesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoicesDTO,
     * or with status 400 (Bad Request) if the invoicesDTO is not valid,
     * or with status 500 (Internal Server Error) if the invoicesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoices")
    @Timed
    public ResponseEntity<InvoicesDTO> updateInvoices(@RequestBody InvoicesDTO invoicesDTO) throws URISyntaxException {
        log.debug("REST request to update Invoices : {}", invoicesDTO);
        if (invoicesDTO.getId() == null) {
            return createInvoices(invoicesDTO);
        }
        Invoices invoices = invoicesMapper.toEntity(invoicesDTO);
        invoices = invoicesRepository.save(invoices);
        InvoicesDTO result = invoicesMapper.toDto(invoices);
        invoicesSearchRepository.save(invoices);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoices : get all the invoices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoices in body
     */
    @GetMapping("/invoices")
    @Timed
    public List<InvoicesDTO> getAllInvoices() {
        log.debug("REST request to get all Invoices");
        List<Invoices> invoices = invoicesRepository.findAll();
        return invoicesMapper.toDto(invoices);
    }

    /**
     * GET  /invoices/:id : get the "id" invoices.
     *
     * @param id the id of the invoicesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoicesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invoices/{id}")
    @Timed
    public ResponseEntity<InvoicesDTO> getInvoices(@PathVariable Long id) {
        log.debug("REST request to get Invoices : {}", id);
        Invoices invoices = invoicesRepository.findOne(id);
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(invoicesDTO));
    }

    /**
     * DELETE  /invoices/:id : delete the "id" invoices.
     *
     * @param id the id of the invoicesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoices/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvoices(@PathVariable Long id) {
        log.debug("REST request to delete Invoices : {}", id);
        invoicesRepository.delete(id);
        invoicesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/invoices?query=:query : search for the invoices corresponding
     * to the query.
     *
     * @param query the query of the invoices search
     * @return the result of the search
     */
    @GetMapping("/_search/invoices")
    @Timed
    public List<InvoicesDTO> searchInvoices(@RequestParam String query) {
        log.debug("REST request to search Invoices for query {}", query);
        return StreamSupport
            .stream(invoicesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(invoicesMapper::toDto)
            .collect(Collectors.toList());
    }

}

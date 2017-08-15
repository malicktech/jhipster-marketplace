package fr.sne.market.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.market.domain.CustomerAddress;

import fr.sne.market.repository.CustomerAddressRepository;
import fr.sne.market.repository.search.CustomerAddressSearchRepository;
import fr.sne.market.web.rest.util.HeaderUtil;
import fr.sne.market.service.dto.CustomerAddressDTO;
import fr.sne.market.service.mapper.CustomerAddressMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CustomerAddress.
 */
@RestController
@RequestMapping("/api")
public class CustomerAddressResource {

    private final Logger log = LoggerFactory.getLogger(CustomerAddressResource.class);

    private static final String ENTITY_NAME = "customerAddress";

    private final CustomerAddressRepository customerAddressRepository;

    private final CustomerAddressMapper customerAddressMapper;

    private final CustomerAddressSearchRepository customerAddressSearchRepository;

    public CustomerAddressResource(CustomerAddressRepository customerAddressRepository, CustomerAddressMapper customerAddressMapper, CustomerAddressSearchRepository customerAddressSearchRepository) {
        this.customerAddressRepository = customerAddressRepository;
        this.customerAddressMapper = customerAddressMapper;
        this.customerAddressSearchRepository = customerAddressSearchRepository;
    }

    /**
     * POST  /customer-addresses : Create a new customerAddress.
     *
     * @param customerAddressDTO the customerAddressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerAddressDTO, or with status 400 (Bad Request) if the customerAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-addresses")
    @Timed
    public ResponseEntity<CustomerAddressDTO> createCustomerAddress(@Valid @RequestBody CustomerAddressDTO customerAddressDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerAddress : {}", customerAddressDTO);
        if (customerAddressDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerAddress cannot already have an ID")).body(null);
        }
        CustomerAddress customerAddress = customerAddressMapper.toEntity(customerAddressDTO);
        customerAddress = customerAddressRepository.save(customerAddress);
        CustomerAddressDTO result = customerAddressMapper.toDto(customerAddress);
        customerAddressSearchRepository.save(customerAddress);
        return ResponseEntity.created(new URI("/api/customer-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-addresses : Updates an existing customerAddress.
     *
     * @param customerAddressDTO the customerAddressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerAddressDTO,
     * or with status 400 (Bad Request) if the customerAddressDTO is not valid,
     * or with status 500 (Internal Server Error) if the customerAddressDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-addresses")
    @Timed
    public ResponseEntity<CustomerAddressDTO> updateCustomerAddress(@Valid @RequestBody CustomerAddressDTO customerAddressDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerAddress : {}", customerAddressDTO);
        if (customerAddressDTO.getId() == null) {
            return createCustomerAddress(customerAddressDTO);
        }
        CustomerAddress customerAddress = customerAddressMapper.toEntity(customerAddressDTO);
        customerAddress = customerAddressRepository.save(customerAddress);
        CustomerAddressDTO result = customerAddressMapper.toDto(customerAddress);
        customerAddressSearchRepository.save(customerAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-addresses : get all the customerAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerAddresses in body
     */
    @GetMapping("/customer-addresses")
    @Timed
    public List<CustomerAddressDTO> getAllCustomerAddresses() {
        log.debug("REST request to get all CustomerAddresses");
        List<CustomerAddress> customerAddresses = customerAddressRepository.findAll();
        return customerAddressMapper.toDto(customerAddresses);
    }

    /**
     * GET  /customer-addresses/:id : get the "id" customerAddress.
     *
     * @param id the id of the customerAddressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerAddressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customer-addresses/{id}")
    @Timed
    public ResponseEntity<CustomerAddressDTO> getCustomerAddress(@PathVariable Long id) {
        log.debug("REST request to get CustomerAddress : {}", id);
        CustomerAddress customerAddress = customerAddressRepository.findOne(id);
        CustomerAddressDTO customerAddressDTO = customerAddressMapper.toDto(customerAddress);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerAddressDTO));
    }

    /**
     * DELETE  /customer-addresses/:id : delete the "id" customerAddress.
     *
     * @param id the id of the customerAddressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerAddress(@PathVariable Long id) {
        log.debug("REST request to delete CustomerAddress : {}", id);
        customerAddressRepository.delete(id);
        customerAddressSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/customer-addresses?query=:query : search for the customerAddress corresponding
     * to the query.
     *
     * @param query the query of the customerAddress search
     * @return the result of the search
     */
    @GetMapping("/_search/customer-addresses")
    @Timed
    public List<CustomerAddressDTO> searchCustomerAddresses(@RequestParam String query) {
        log.debug("REST request to search CustomerAddresses for query {}", query);
        return StreamSupport
            .stream(customerAddressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(customerAddressMapper::toDto)
            .collect(Collectors.toList());
    }

}

package fr.sne.market.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.market.domain.MarketOrderItemsDetails;

import fr.sne.market.repository.MarketOrderItemsDetailsRepository;
import fr.sne.market.repository.search.MarketOrderItemsDetailsSearchRepository;
import fr.sne.market.web.rest.util.HeaderUtil;
import fr.sne.market.service.dto.MarketOrderItemsDetailsDTO;
import fr.sne.market.service.mapper.MarketOrderItemsDetailsMapper;
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
 * REST controller for managing MarketOrderItemsDetails.
 */
@RestController
@RequestMapping("/api")
public class MarketOrderItemsDetailsResource {

    private final Logger log = LoggerFactory.getLogger(MarketOrderItemsDetailsResource.class);

    private static final String ENTITY_NAME = "marketOrderItemsDetails";

    private final MarketOrderItemsDetailsRepository marketOrderItemsDetailsRepository;

    private final MarketOrderItemsDetailsMapper marketOrderItemsDetailsMapper;

    private final MarketOrderItemsDetailsSearchRepository marketOrderItemsDetailsSearchRepository;

    public MarketOrderItemsDetailsResource(MarketOrderItemsDetailsRepository marketOrderItemsDetailsRepository, MarketOrderItemsDetailsMapper marketOrderItemsDetailsMapper, MarketOrderItemsDetailsSearchRepository marketOrderItemsDetailsSearchRepository) {
        this.marketOrderItemsDetailsRepository = marketOrderItemsDetailsRepository;
        this.marketOrderItemsDetailsMapper = marketOrderItemsDetailsMapper;
        this.marketOrderItemsDetailsSearchRepository = marketOrderItemsDetailsSearchRepository;
    }

    /**
     * POST  /market-order-items-details : Create a new marketOrderItemsDetails.
     *
     * @param marketOrderItemsDetailsDTO the marketOrderItemsDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketOrderItemsDetailsDTO, or with status 400 (Bad Request) if the marketOrderItemsDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-order-items-details")
    @Timed
    public ResponseEntity<MarketOrderItemsDetailsDTO> createMarketOrderItemsDetails(@RequestBody MarketOrderItemsDetailsDTO marketOrderItemsDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save MarketOrderItemsDetails : {}", marketOrderItemsDetailsDTO);
        if (marketOrderItemsDetailsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketOrderItemsDetails cannot already have an ID")).body(null);
        }
        MarketOrderItemsDetails marketOrderItemsDetails = marketOrderItemsDetailsMapper.toEntity(marketOrderItemsDetailsDTO);
        marketOrderItemsDetails = marketOrderItemsDetailsRepository.save(marketOrderItemsDetails);
        MarketOrderItemsDetailsDTO result = marketOrderItemsDetailsMapper.toDto(marketOrderItemsDetails);
        marketOrderItemsDetailsSearchRepository.save(marketOrderItemsDetails);
        return ResponseEntity.created(new URI("/api/market-order-items-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-order-items-details : Updates an existing marketOrderItemsDetails.
     *
     * @param marketOrderItemsDetailsDTO the marketOrderItemsDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketOrderItemsDetailsDTO,
     * or with status 400 (Bad Request) if the marketOrderItemsDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketOrderItemsDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-order-items-details")
    @Timed
    public ResponseEntity<MarketOrderItemsDetailsDTO> updateMarketOrderItemsDetails(@RequestBody MarketOrderItemsDetailsDTO marketOrderItemsDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update MarketOrderItemsDetails : {}", marketOrderItemsDetailsDTO);
        if (marketOrderItemsDetailsDTO.getId() == null) {
            return createMarketOrderItemsDetails(marketOrderItemsDetailsDTO);
        }
        MarketOrderItemsDetails marketOrderItemsDetails = marketOrderItemsDetailsMapper.toEntity(marketOrderItemsDetailsDTO);
        marketOrderItemsDetails = marketOrderItemsDetailsRepository.save(marketOrderItemsDetails);
        MarketOrderItemsDetailsDTO result = marketOrderItemsDetailsMapper.toDto(marketOrderItemsDetails);
        marketOrderItemsDetailsSearchRepository.save(marketOrderItemsDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketOrderItemsDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-order-items-details : get all the marketOrderItemsDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketOrderItemsDetails in body
     */
    @GetMapping("/market-order-items-details")
    @Timed
    public List<MarketOrderItemsDetailsDTO> getAllMarketOrderItemsDetails() {
        log.debug("REST request to get all MarketOrderItemsDetails");
        List<MarketOrderItemsDetails> marketOrderItemsDetails = marketOrderItemsDetailsRepository.findAll();
        return marketOrderItemsDetailsMapper.toDto(marketOrderItemsDetails);
    }

    /**
     * GET  /market-order-items-details/:id : get the "id" marketOrderItemsDetails.
     *
     * @param id the id of the marketOrderItemsDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketOrderItemsDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/market-order-items-details/{id}")
    @Timed
    public ResponseEntity<MarketOrderItemsDetailsDTO> getMarketOrderItemsDetails(@PathVariable Long id) {
        log.debug("REST request to get MarketOrderItemsDetails : {}", id);
        MarketOrderItemsDetails marketOrderItemsDetails = marketOrderItemsDetailsRepository.findOne(id);
        MarketOrderItemsDetailsDTO marketOrderItemsDetailsDTO = marketOrderItemsDetailsMapper.toDto(marketOrderItemsDetails);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketOrderItemsDetailsDTO));
    }

    /**
     * DELETE  /market-order-items-details/:id : delete the "id" marketOrderItemsDetails.
     *
     * @param id the id of the marketOrderItemsDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-order-items-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketOrderItemsDetails(@PathVariable Long id) {
        log.debug("REST request to delete MarketOrderItemsDetails : {}", id);
        marketOrderItemsDetailsRepository.delete(id);
        marketOrderItemsDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/market-order-items-details?query=:query : search for the marketOrderItemsDetails corresponding
     * to the query.
     *
     * @param query the query of the marketOrderItemsDetails search
     * @return the result of the search
     */
    @GetMapping("/_search/market-order-items-details")
    @Timed
    public List<MarketOrderItemsDetailsDTO> searchMarketOrderItemsDetails(@RequestParam String query) {
        log.debug("REST request to search MarketOrderItemsDetails for query {}", query);
        return StreamSupport
            .stream(marketOrderItemsDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(marketOrderItemsDetailsMapper::toDto)
            .collect(Collectors.toList());
    }

}

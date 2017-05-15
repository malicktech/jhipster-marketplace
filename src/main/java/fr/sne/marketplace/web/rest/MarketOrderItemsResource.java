package fr.sne.marketplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.marketplace.domain.MarketOrderItems;

import fr.sne.marketplace.repository.MarketOrderItemsRepository;
import fr.sne.marketplace.repository.search.MarketOrderItemsSearchRepository;
import fr.sne.marketplace.web.rest.util.HeaderUtil;
import fr.sne.marketplace.web.rest.util.PaginationUtil;
import fr.sne.marketplace.service.dto.MarketOrderItemsDTO;
import fr.sne.marketplace.service.mapper.MarketOrderItemsMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MarketOrderItems.
 */
@RestController
@RequestMapping("/api")
public class MarketOrderItemsResource {

    private final Logger log = LoggerFactory.getLogger(MarketOrderItemsResource.class);

    private static final String ENTITY_NAME = "marketOrderItems";
        
    private final MarketOrderItemsRepository marketOrderItemsRepository;

    private final MarketOrderItemsMapper marketOrderItemsMapper;

    private final MarketOrderItemsSearchRepository marketOrderItemsSearchRepository;

    public MarketOrderItemsResource(MarketOrderItemsRepository marketOrderItemsRepository, MarketOrderItemsMapper marketOrderItemsMapper, MarketOrderItemsSearchRepository marketOrderItemsSearchRepository) {
        this.marketOrderItemsRepository = marketOrderItemsRepository;
        this.marketOrderItemsMapper = marketOrderItemsMapper;
        this.marketOrderItemsSearchRepository = marketOrderItemsSearchRepository;
    }

    /**
     * POST  /market-order-items : Create a new marketOrderItems.
     *
     * @param marketOrderItemsDTO the marketOrderItemsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketOrderItemsDTO, or with status 400 (Bad Request) if the marketOrderItems has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-order-items")
    @Timed
    public ResponseEntity<MarketOrderItemsDTO> createMarketOrderItems(@Valid @RequestBody MarketOrderItemsDTO marketOrderItemsDTO) throws URISyntaxException {
        log.debug("REST request to save MarketOrderItems : {}", marketOrderItemsDTO);
        if (marketOrderItemsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketOrderItems cannot already have an ID")).body(null);
        }
        MarketOrderItems marketOrderItems = marketOrderItemsMapper.toEntity(marketOrderItemsDTO);
        marketOrderItems = marketOrderItemsRepository.save(marketOrderItems);
        MarketOrderItemsDTO result = marketOrderItemsMapper.toDto(marketOrderItems);
        marketOrderItemsSearchRepository.save(marketOrderItems);
        return ResponseEntity.created(new URI("/api/market-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-order-items : Updates an existing marketOrderItems.
     *
     * @param marketOrderItemsDTO the marketOrderItemsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketOrderItemsDTO,
     * or with status 400 (Bad Request) if the marketOrderItemsDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketOrderItemsDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-order-items")
    @Timed
    public ResponseEntity<MarketOrderItemsDTO> updateMarketOrderItems(@Valid @RequestBody MarketOrderItemsDTO marketOrderItemsDTO) throws URISyntaxException {
        log.debug("REST request to update MarketOrderItems : {}", marketOrderItemsDTO);
        if (marketOrderItemsDTO.getId() == null) {
            return createMarketOrderItems(marketOrderItemsDTO);
        }
        MarketOrderItems marketOrderItems = marketOrderItemsMapper.toEntity(marketOrderItemsDTO);
        marketOrderItems = marketOrderItemsRepository.save(marketOrderItems);
        MarketOrderItemsDTO result = marketOrderItemsMapper.toDto(marketOrderItems);
        marketOrderItemsSearchRepository.save(marketOrderItems);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketOrderItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-order-items : get all the marketOrderItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of marketOrderItems in body
     */
    @GetMapping("/market-order-items")
    @Timed
    public ResponseEntity<List<MarketOrderItemsDTO>> getAllMarketOrderItems(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MarketOrderItems");
        Page<MarketOrderItems> page = marketOrderItemsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/market-order-items");
        return new ResponseEntity<>(marketOrderItemsMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /market-order-items/:id : get the "id" marketOrderItems.
     *
     * @param id the id of the marketOrderItemsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketOrderItemsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/market-order-items/{id}")
    @Timed
    public ResponseEntity<MarketOrderItemsDTO> getMarketOrderItems(@PathVariable Long id) {
        log.debug("REST request to get MarketOrderItems : {}", id);
        MarketOrderItems marketOrderItems = marketOrderItemsRepository.findOne(id);
        MarketOrderItemsDTO marketOrderItemsDTO = marketOrderItemsMapper.toDto(marketOrderItems);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketOrderItemsDTO));
    }

    /**
     * DELETE  /market-order-items/:id : delete the "id" marketOrderItems.
     *
     * @param id the id of the marketOrderItemsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-order-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketOrderItems(@PathVariable Long id) {
        log.debug("REST request to delete MarketOrderItems : {}", id);
        marketOrderItemsRepository.delete(id);
        marketOrderItemsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/market-order-items?query=:query : search for the marketOrderItems corresponding
     * to the query.
     *
     * @param query the query of the marketOrderItems search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/market-order-items")
    @Timed
    public ResponseEntity<List<MarketOrderItemsDTO>> searchMarketOrderItems(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MarketOrderItems for query {}", query);
        Page<MarketOrderItems> page = marketOrderItemsSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/market-order-items");
        return new ResponseEntity<>(marketOrderItemsMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }


}

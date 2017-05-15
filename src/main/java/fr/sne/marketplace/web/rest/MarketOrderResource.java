package fr.sne.marketplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.marketplace.domain.MarketOrder;

import fr.sne.marketplace.repository.MarketOrderRepository;
import fr.sne.marketplace.repository.search.MarketOrderSearchRepository;
import fr.sne.marketplace.web.rest.util.HeaderUtil;
import fr.sne.marketplace.web.rest.util.PaginationUtil;
import fr.sne.marketplace.service.dto.MarketOrderDTO;
import fr.sne.marketplace.service.mapper.MarketOrderMapper;
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
 * REST controller for managing MarketOrder.
 */
@RestController
@RequestMapping("/api")
public class MarketOrderResource {

    private final Logger log = LoggerFactory.getLogger(MarketOrderResource.class);

    private static final String ENTITY_NAME = "marketOrder";
        
    private final MarketOrderRepository marketOrderRepository;

    private final MarketOrderMapper marketOrderMapper;

    private final MarketOrderSearchRepository marketOrderSearchRepository;

    public MarketOrderResource(MarketOrderRepository marketOrderRepository, MarketOrderMapper marketOrderMapper, MarketOrderSearchRepository marketOrderSearchRepository) {
        this.marketOrderRepository = marketOrderRepository;
        this.marketOrderMapper = marketOrderMapper;
        this.marketOrderSearchRepository = marketOrderSearchRepository;
    }

    /**
     * POST  /market-orders : Create a new marketOrder.
     *
     * @param marketOrderDTO the marketOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketOrderDTO, or with status 400 (Bad Request) if the marketOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-orders")
    @Timed
    public ResponseEntity<MarketOrderDTO> createMarketOrder(@Valid @RequestBody MarketOrderDTO marketOrderDTO) throws URISyntaxException {
        log.debug("REST request to save MarketOrder : {}", marketOrderDTO);
        if (marketOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketOrder cannot already have an ID")).body(null);
        }
        MarketOrder marketOrder = marketOrderMapper.toEntity(marketOrderDTO);
        marketOrder = marketOrderRepository.save(marketOrder);
        MarketOrderDTO result = marketOrderMapper.toDto(marketOrder);
        marketOrderSearchRepository.save(marketOrder);
        return ResponseEntity.created(new URI("/api/market-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-orders : Updates an existing marketOrder.
     *
     * @param marketOrderDTO the marketOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketOrderDTO,
     * or with status 400 (Bad Request) if the marketOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketOrderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-orders")
    @Timed
    public ResponseEntity<MarketOrderDTO> updateMarketOrder(@Valid @RequestBody MarketOrderDTO marketOrderDTO) throws URISyntaxException {
        log.debug("REST request to update MarketOrder : {}", marketOrderDTO);
        if (marketOrderDTO.getId() == null) {
            return createMarketOrder(marketOrderDTO);
        }
        MarketOrder marketOrder = marketOrderMapper.toEntity(marketOrderDTO);
        marketOrder = marketOrderRepository.save(marketOrder);
        MarketOrderDTO result = marketOrderMapper.toDto(marketOrder);
        marketOrderSearchRepository.save(marketOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-orders : get all the marketOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of marketOrders in body
     */
    @GetMapping("/market-orders")
    @Timed
    public ResponseEntity<List<MarketOrderDTO>> getAllMarketOrders(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MarketOrders");
        Page<MarketOrder> page = marketOrderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/market-orders");
        return new ResponseEntity<>(marketOrderMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /market-orders/:id : get the "id" marketOrder.
     *
     * @param id the id of the marketOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/market-orders/{id}")
    @Timed
    public ResponseEntity<MarketOrderDTO> getMarketOrder(@PathVariable Long id) {
        log.debug("REST request to get MarketOrder : {}", id);
        MarketOrder marketOrder = marketOrderRepository.findOne(id);
        MarketOrderDTO marketOrderDTO = marketOrderMapper.toDto(marketOrder);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketOrderDTO));
    }

    /**
     * DELETE  /market-orders/:id : delete the "id" marketOrder.
     *
     * @param id the id of the marketOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketOrder(@PathVariable Long id) {
        log.debug("REST request to delete MarketOrder : {}", id);
        marketOrderRepository.delete(id);
        marketOrderSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/market-orders?query=:query : search for the marketOrder corresponding
     * to the query.
     *
     * @param query the query of the marketOrder search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/market-orders")
    @Timed
    public ResponseEntity<List<MarketOrderDTO>> searchMarketOrders(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MarketOrders for query {}", query);
        Page<MarketOrder> page = marketOrderSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/market-orders");
        return new ResponseEntity<>(marketOrderMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }


}

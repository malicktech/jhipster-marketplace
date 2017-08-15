package fr.sne.market.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.market.domain.MarketOrderline;

import fr.sne.market.repository.MarketOrderlineRepository;
import fr.sne.market.repository.search.MarketOrderlineSearchRepository;
import fr.sne.market.web.rest.util.HeaderUtil;
import fr.sne.market.web.rest.util.PaginationUtil;
import fr.sne.market.service.dto.MarketOrderlineDTO;
import fr.sne.market.service.mapper.MarketOrderlineMapper;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MarketOrderline.
 */
@RestController
@RequestMapping("/api")
public class MarketOrderlineResource {

    private final Logger log = LoggerFactory.getLogger(MarketOrderlineResource.class);

    private static final String ENTITY_NAME = "marketOrderline";

    private final MarketOrderlineRepository marketOrderlineRepository;

    private final MarketOrderlineMapper marketOrderlineMapper;

    private final MarketOrderlineSearchRepository marketOrderlineSearchRepository;

    public MarketOrderlineResource(MarketOrderlineRepository marketOrderlineRepository, MarketOrderlineMapper marketOrderlineMapper, MarketOrderlineSearchRepository marketOrderlineSearchRepository) {
        this.marketOrderlineRepository = marketOrderlineRepository;
        this.marketOrderlineMapper = marketOrderlineMapper;
        this.marketOrderlineSearchRepository = marketOrderlineSearchRepository;
    }

    /**
     * POST  /market-orderlines : Create a new marketOrderline.
     *
     * @param marketOrderlineDTO the marketOrderlineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketOrderlineDTO, or with status 400 (Bad Request) if the marketOrderline has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-orderlines")
    @Timed
    public ResponseEntity<MarketOrderlineDTO> createMarketOrderline(@RequestBody MarketOrderlineDTO marketOrderlineDTO) throws URISyntaxException {
        log.debug("REST request to save MarketOrderline : {}", marketOrderlineDTO);
        if (marketOrderlineDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketOrderline cannot already have an ID")).body(null);
        }
        MarketOrderline marketOrderline = marketOrderlineMapper.toEntity(marketOrderlineDTO);
        marketOrderline = marketOrderlineRepository.save(marketOrderline);
        MarketOrderlineDTO result = marketOrderlineMapper.toDto(marketOrderline);
        marketOrderlineSearchRepository.save(marketOrderline);
        return ResponseEntity.created(new URI("/api/market-orderlines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-orderlines : Updates an existing marketOrderline.
     *
     * @param marketOrderlineDTO the marketOrderlineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketOrderlineDTO,
     * or with status 400 (Bad Request) if the marketOrderlineDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketOrderlineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-orderlines")
    @Timed
    public ResponseEntity<MarketOrderlineDTO> updateMarketOrderline(@RequestBody MarketOrderlineDTO marketOrderlineDTO) throws URISyntaxException {
        log.debug("REST request to update MarketOrderline : {}", marketOrderlineDTO);
        if (marketOrderlineDTO.getId() == null) {
            return createMarketOrderline(marketOrderlineDTO);
        }
        MarketOrderline marketOrderline = marketOrderlineMapper.toEntity(marketOrderlineDTO);
        marketOrderline = marketOrderlineRepository.save(marketOrderline);
        MarketOrderlineDTO result = marketOrderlineMapper.toDto(marketOrderline);
        marketOrderlineSearchRepository.save(marketOrderline);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketOrderlineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-orderlines : get all the marketOrderlines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of marketOrderlines in body
     */
    @GetMapping("/market-orderlines")
    @Timed
    public ResponseEntity<List<MarketOrderlineDTO>> getAllMarketOrderlines(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MarketOrderlines");
        Page<MarketOrderline> page = marketOrderlineRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/market-orderlines");
        return new ResponseEntity<>(marketOrderlineMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /market-orderlines/:id : get the "id" marketOrderline.
     *
     * @param id the id of the marketOrderlineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketOrderlineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/market-orderlines/{id}")
    @Timed
    public ResponseEntity<MarketOrderlineDTO> getMarketOrderline(@PathVariable Long id) {
        log.debug("REST request to get MarketOrderline : {}", id);
        MarketOrderline marketOrderline = marketOrderlineRepository.findOne(id);
        MarketOrderlineDTO marketOrderlineDTO = marketOrderlineMapper.toDto(marketOrderline);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketOrderlineDTO));
    }

    /**
     * DELETE  /market-orderlines/:id : delete the "id" marketOrderline.
     *
     * @param id the id of the marketOrderlineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-orderlines/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketOrderline(@PathVariable Long id) {
        log.debug("REST request to delete MarketOrderline : {}", id);
        marketOrderlineRepository.delete(id);
        marketOrderlineSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/market-orderlines?query=:query : search for the marketOrderline corresponding
     * to the query.
     *
     * @param query the query of the marketOrderline search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/market-orderlines")
    @Timed
    public ResponseEntity<List<MarketOrderlineDTO>> searchMarketOrderlines(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MarketOrderlines for query {}", query);
        Page<MarketOrderline> page = marketOrderlineSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/market-orderlines");
        return new ResponseEntity<>(marketOrderlineMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

}

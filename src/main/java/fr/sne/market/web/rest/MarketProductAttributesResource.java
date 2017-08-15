package fr.sne.market.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.market.domain.MarketProductAttributes;

import fr.sne.market.repository.MarketProductAttributesRepository;
import fr.sne.market.repository.search.MarketProductAttributesSearchRepository;
import fr.sne.market.web.rest.util.HeaderUtil;
import fr.sne.market.service.dto.MarketProductAttributesDTO;
import fr.sne.market.service.mapper.MarketProductAttributesMapper;
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
 * REST controller for managing MarketProductAttributes.
 */
@RestController
@RequestMapping("/api")
public class MarketProductAttributesResource {

    private final Logger log = LoggerFactory.getLogger(MarketProductAttributesResource.class);

    private static final String ENTITY_NAME = "marketProductAttributes";

    private final MarketProductAttributesRepository marketProductAttributesRepository;

    private final MarketProductAttributesMapper marketProductAttributesMapper;

    private final MarketProductAttributesSearchRepository marketProductAttributesSearchRepository;

    public MarketProductAttributesResource(MarketProductAttributesRepository marketProductAttributesRepository, MarketProductAttributesMapper marketProductAttributesMapper, MarketProductAttributesSearchRepository marketProductAttributesSearchRepository) {
        this.marketProductAttributesRepository = marketProductAttributesRepository;
        this.marketProductAttributesMapper = marketProductAttributesMapper;
        this.marketProductAttributesSearchRepository = marketProductAttributesSearchRepository;
    }

    /**
     * POST  /market-product-attributes : Create a new marketProductAttributes.
     *
     * @param marketProductAttributesDTO the marketProductAttributesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketProductAttributesDTO, or with status 400 (Bad Request) if the marketProductAttributes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-product-attributes")
    @Timed
    public ResponseEntity<MarketProductAttributesDTO> createMarketProductAttributes(@RequestBody MarketProductAttributesDTO marketProductAttributesDTO) throws URISyntaxException {
        log.debug("REST request to save MarketProductAttributes : {}", marketProductAttributesDTO);
        if (marketProductAttributesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketProductAttributes cannot already have an ID")).body(null);
        }
        MarketProductAttributes marketProductAttributes = marketProductAttributesMapper.toEntity(marketProductAttributesDTO);
        marketProductAttributes = marketProductAttributesRepository.save(marketProductAttributes);
        MarketProductAttributesDTO result = marketProductAttributesMapper.toDto(marketProductAttributes);
        marketProductAttributesSearchRepository.save(marketProductAttributes);
        return ResponseEntity.created(new URI("/api/market-product-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-product-attributes : Updates an existing marketProductAttributes.
     *
     * @param marketProductAttributesDTO the marketProductAttributesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketProductAttributesDTO,
     * or with status 400 (Bad Request) if the marketProductAttributesDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketProductAttributesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-product-attributes")
    @Timed
    public ResponseEntity<MarketProductAttributesDTO> updateMarketProductAttributes(@RequestBody MarketProductAttributesDTO marketProductAttributesDTO) throws URISyntaxException {
        log.debug("REST request to update MarketProductAttributes : {}", marketProductAttributesDTO);
        if (marketProductAttributesDTO.getId() == null) {
            return createMarketProductAttributes(marketProductAttributesDTO);
        }
        MarketProductAttributes marketProductAttributes = marketProductAttributesMapper.toEntity(marketProductAttributesDTO);
        marketProductAttributes = marketProductAttributesRepository.save(marketProductAttributes);
        MarketProductAttributesDTO result = marketProductAttributesMapper.toDto(marketProductAttributes);
        marketProductAttributesSearchRepository.save(marketProductAttributes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketProductAttributesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-product-attributes : get all the marketProductAttributes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketProductAttributes in body
     */
    @GetMapping("/market-product-attributes")
    @Timed
    public List<MarketProductAttributesDTO> getAllMarketProductAttributes() {
        log.debug("REST request to get all MarketProductAttributes");
        List<MarketProductAttributes> marketProductAttributes = marketProductAttributesRepository.findAll();
        return marketProductAttributesMapper.toDto(marketProductAttributes);
    }

    /**
     * GET  /market-product-attributes/:id : get the "id" marketProductAttributes.
     *
     * @param id the id of the marketProductAttributesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketProductAttributesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/market-product-attributes/{id}")
    @Timed
    public ResponseEntity<MarketProductAttributesDTO> getMarketProductAttributes(@PathVariable Long id) {
        log.debug("REST request to get MarketProductAttributes : {}", id);
        MarketProductAttributes marketProductAttributes = marketProductAttributesRepository.findOne(id);
        MarketProductAttributesDTO marketProductAttributesDTO = marketProductAttributesMapper.toDto(marketProductAttributes);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketProductAttributesDTO));
    }

    /**
     * DELETE  /market-product-attributes/:id : delete the "id" marketProductAttributes.
     *
     * @param id the id of the marketProductAttributesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-product-attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketProductAttributes(@PathVariable Long id) {
        log.debug("REST request to delete MarketProductAttributes : {}", id);
        marketProductAttributesRepository.delete(id);
        marketProductAttributesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/market-product-attributes?query=:query : search for the marketProductAttributes corresponding
     * to the query.
     *
     * @param query the query of the marketProductAttributes search
     * @return the result of the search
     */
    @GetMapping("/_search/market-product-attributes")
    @Timed
    public List<MarketProductAttributesDTO> searchMarketProductAttributes(@RequestParam String query) {
        log.debug("REST request to search MarketProductAttributes for query {}", query);
        return StreamSupport
            .stream(marketProductAttributesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(marketProductAttributesMapper::toDto)
            .collect(Collectors.toList());
    }

}

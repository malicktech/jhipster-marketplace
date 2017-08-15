package fr.sne.market.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.market.domain.MarketProductCategory;

import fr.sne.market.repository.MarketProductCategoryRepository;
import fr.sne.market.repository.search.MarketProductCategorySearchRepository;
import fr.sne.market.web.rest.util.HeaderUtil;
import fr.sne.market.service.dto.MarketProductCategoryDTO;
import fr.sne.market.service.mapper.MarketProductCategoryMapper;
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
 * REST controller for managing MarketProductCategory.
 */
@RestController
@RequestMapping("/api")
public class MarketProductCategoryResource {

    private final Logger log = LoggerFactory.getLogger(MarketProductCategoryResource.class);

    private static final String ENTITY_NAME = "marketProductCategory";

    private final MarketProductCategoryRepository marketProductCategoryRepository;

    private final MarketProductCategoryMapper marketProductCategoryMapper;

    private final MarketProductCategorySearchRepository marketProductCategorySearchRepository;

    public MarketProductCategoryResource(MarketProductCategoryRepository marketProductCategoryRepository, MarketProductCategoryMapper marketProductCategoryMapper, MarketProductCategorySearchRepository marketProductCategorySearchRepository) {
        this.marketProductCategoryRepository = marketProductCategoryRepository;
        this.marketProductCategoryMapper = marketProductCategoryMapper;
        this.marketProductCategorySearchRepository = marketProductCategorySearchRepository;
    }

    /**
     * POST  /market-product-categories : Create a new marketProductCategory.
     *
     * @param marketProductCategoryDTO the marketProductCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketProductCategoryDTO, or with status 400 (Bad Request) if the marketProductCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-product-categories")
    @Timed
    public ResponseEntity<MarketProductCategoryDTO> createMarketProductCategory(@Valid @RequestBody MarketProductCategoryDTO marketProductCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save MarketProductCategory : {}", marketProductCategoryDTO);
        if (marketProductCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketProductCategory cannot already have an ID")).body(null);
        }
        MarketProductCategory marketProductCategory = marketProductCategoryMapper.toEntity(marketProductCategoryDTO);
        marketProductCategory = marketProductCategoryRepository.save(marketProductCategory);
        MarketProductCategoryDTO result = marketProductCategoryMapper.toDto(marketProductCategory);
        marketProductCategorySearchRepository.save(marketProductCategory);
        return ResponseEntity.created(new URI("/api/market-product-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-product-categories : Updates an existing marketProductCategory.
     *
     * @param marketProductCategoryDTO the marketProductCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketProductCategoryDTO,
     * or with status 400 (Bad Request) if the marketProductCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketProductCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-product-categories")
    @Timed
    public ResponseEntity<MarketProductCategoryDTO> updateMarketProductCategory(@Valid @RequestBody MarketProductCategoryDTO marketProductCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update MarketProductCategory : {}", marketProductCategoryDTO);
        if (marketProductCategoryDTO.getId() == null) {
            return createMarketProductCategory(marketProductCategoryDTO);
        }
        MarketProductCategory marketProductCategory = marketProductCategoryMapper.toEntity(marketProductCategoryDTO);
        marketProductCategory = marketProductCategoryRepository.save(marketProductCategory);
        MarketProductCategoryDTO result = marketProductCategoryMapper.toDto(marketProductCategory);
        marketProductCategorySearchRepository.save(marketProductCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketProductCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-product-categories : get all the marketProductCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketProductCategories in body
     */
    @GetMapping("/market-product-categories")
    @Timed
    public List<MarketProductCategoryDTO> getAllMarketProductCategories() {
        log.debug("REST request to get all MarketProductCategories");
        List<MarketProductCategory> marketProductCategories = marketProductCategoryRepository.findAll();
        return marketProductCategoryMapper.toDto(marketProductCategories);
    }

    /**
     * GET  /market-product-categories/:id : get the "id" marketProductCategory.
     *
     * @param id the id of the marketProductCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketProductCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/market-product-categories/{id}")
    @Timed
    public ResponseEntity<MarketProductCategoryDTO> getMarketProductCategory(@PathVariable Long id) {
        log.debug("REST request to get MarketProductCategory : {}", id);
        MarketProductCategory marketProductCategory = marketProductCategoryRepository.findOne(id);
        MarketProductCategoryDTO marketProductCategoryDTO = marketProductCategoryMapper.toDto(marketProductCategory);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketProductCategoryDTO));
    }

    /**
     * DELETE  /market-product-categories/:id : delete the "id" marketProductCategory.
     *
     * @param id the id of the marketProductCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-product-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketProductCategory(@PathVariable Long id) {
        log.debug("REST request to delete MarketProductCategory : {}", id);
        marketProductCategoryRepository.delete(id);
        marketProductCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/market-product-categories?query=:query : search for the marketProductCategory corresponding
     * to the query.
     *
     * @param query the query of the marketProductCategory search
     * @return the result of the search
     */
    @GetMapping("/_search/market-product-categories")
    @Timed
    public List<MarketProductCategoryDTO> searchMarketProductCategories(@RequestParam String query) {
        log.debug("REST request to search MarketProductCategories for query {}", query);
        return StreamSupport
            .stream(marketProductCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(marketProductCategoryMapper::toDto)
            .collect(Collectors.toList());
    }

}

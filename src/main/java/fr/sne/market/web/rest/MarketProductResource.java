package fr.sne.market.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.market.domain.MarketProduct;

import fr.sne.market.repository.MarketProductRepository;
import fr.sne.market.repository.search.MarketProductSearchRepository;
import fr.sne.market.web.rest.util.HeaderUtil;
import fr.sne.market.service.dto.MarketProductDTO;
import fr.sne.market.service.mapper.MarketProductMapper;
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
 * REST controller for managing MarketProduct.
 */
@RestController
@RequestMapping("/api")
public class MarketProductResource {

    private final Logger log = LoggerFactory.getLogger(MarketProductResource.class);

    private static final String ENTITY_NAME = "marketProduct";

    private final MarketProductRepository marketProductRepository;

    private final MarketProductMapper marketProductMapper;

    private final MarketProductSearchRepository marketProductSearchRepository;

    public MarketProductResource(MarketProductRepository marketProductRepository, MarketProductMapper marketProductMapper, MarketProductSearchRepository marketProductSearchRepository) {
        this.marketProductRepository = marketProductRepository;
        this.marketProductMapper = marketProductMapper;
        this.marketProductSearchRepository = marketProductSearchRepository;
    }

    /**
     * POST  /market-products : Create a new marketProduct.
     *
     * @param marketProductDTO the marketProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketProductDTO, or with status 400 (Bad Request) if the marketProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-products")
    @Timed
    public ResponseEntity<MarketProductDTO> createMarketProduct(@Valid @RequestBody MarketProductDTO marketProductDTO) throws URISyntaxException {
        log.debug("REST request to save MarketProduct : {}", marketProductDTO);
        if (marketProductDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketProduct cannot already have an ID")).body(null);
        }
        MarketProduct marketProduct = marketProductMapper.toEntity(marketProductDTO);
        marketProduct = marketProductRepository.save(marketProduct);
        MarketProductDTO result = marketProductMapper.toDto(marketProduct);
        marketProductSearchRepository.save(marketProduct);
        return ResponseEntity.created(new URI("/api/market-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-products : Updates an existing marketProduct.
     *
     * @param marketProductDTO the marketProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketProductDTO,
     * or with status 400 (Bad Request) if the marketProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketProductDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-products")
    @Timed
    public ResponseEntity<MarketProductDTO> updateMarketProduct(@Valid @RequestBody MarketProductDTO marketProductDTO) throws URISyntaxException {
        log.debug("REST request to update MarketProduct : {}", marketProductDTO);
        if (marketProductDTO.getId() == null) {
            return createMarketProduct(marketProductDTO);
        }
        MarketProduct marketProduct = marketProductMapper.toEntity(marketProductDTO);
        marketProduct = marketProductRepository.save(marketProduct);
        MarketProductDTO result = marketProductMapper.toDto(marketProduct);
        marketProductSearchRepository.save(marketProduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-products : get all the marketProducts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketProducts in body
     */
    @GetMapping("/market-products")
    @Timed
    public List<MarketProductDTO> getAllMarketProducts() {
        log.debug("REST request to get all MarketProducts");
        List<MarketProduct> marketProducts = marketProductRepository.findAll();
        return marketProductMapper.toDto(marketProducts);
    }

    /**
     * GET  /market-products/:id : get the "id" marketProduct.
     *
     * @param id the id of the marketProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/market-products/{id}")
    @Timed
    public ResponseEntity<MarketProductDTO> getMarketProduct(@PathVariable Long id) {
        log.debug("REST request to get MarketProduct : {}", id);
        MarketProduct marketProduct = marketProductRepository.findOne(id);
        MarketProductDTO marketProductDTO = marketProductMapper.toDto(marketProduct);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketProductDTO));
    }

    /**
     * DELETE  /market-products/:id : delete the "id" marketProduct.
     *
     * @param id the id of the marketProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-products/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketProduct(@PathVariable Long id) {
        log.debug("REST request to delete MarketProduct : {}", id);
        marketProductRepository.delete(id);
        marketProductSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/market-products?query=:query : search for the marketProduct corresponding
     * to the query.
     *
     * @param query the query of the marketProduct search
     * @return the result of the search
     */
    @GetMapping("/_search/market-products")
    @Timed
    public List<MarketProductDTO> searchMarketProducts(@RequestParam String query) {
        log.debug("REST request to search MarketProducts for query {}", query);
        return StreamSupport
            .stream(marketProductSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(marketProductMapper::toDto)
            .collect(Collectors.toList());
    }

}

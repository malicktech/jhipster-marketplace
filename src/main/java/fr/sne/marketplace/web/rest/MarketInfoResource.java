package fr.sne.marketplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.marketplace.domain.MarketInfo;

import fr.sne.marketplace.repository.MarketInfoRepository;
import fr.sne.marketplace.repository.search.MarketInfoSearchRepository;
import fr.sne.marketplace.web.rest.util.HeaderUtil;
import fr.sne.marketplace.service.dto.MarketInfoDTO;
import fr.sne.marketplace.service.mapper.MarketInfoMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MarketInfo.
 */
@RestController
@RequestMapping("/api")
public class MarketInfoResource {

    private final Logger log = LoggerFactory.getLogger(MarketInfoResource.class);

    private static final String ENTITY_NAME = "marketInfo";
        
    private final MarketInfoRepository marketInfoRepository;

    private final MarketInfoMapper marketInfoMapper;

    private final MarketInfoSearchRepository marketInfoSearchRepository;

    public MarketInfoResource(MarketInfoRepository marketInfoRepository, MarketInfoMapper marketInfoMapper, MarketInfoSearchRepository marketInfoSearchRepository) {
        this.marketInfoRepository = marketInfoRepository;
        this.marketInfoMapper = marketInfoMapper;
        this.marketInfoSearchRepository = marketInfoSearchRepository;
    }

    /**
     * POST  /market-infos : Create a new marketInfo.
     *
     * @param marketInfoDTO the marketInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketInfoDTO, or with status 400 (Bad Request) if the marketInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-infos")
    @Timed
    public ResponseEntity<MarketInfoDTO> createMarketInfo(@RequestBody MarketInfoDTO marketInfoDTO) throws URISyntaxException {
        log.debug("REST request to save MarketInfo : {}", marketInfoDTO);
        if (marketInfoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketInfo cannot already have an ID")).body(null);
        }
        MarketInfo marketInfo = marketInfoMapper.toEntity(marketInfoDTO);
        marketInfo = marketInfoRepository.save(marketInfo);
        MarketInfoDTO result = marketInfoMapper.toDto(marketInfo);
        marketInfoSearchRepository.save(marketInfo);
        return ResponseEntity.created(new URI("/api/market-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-infos : Updates an existing marketInfo.
     *
     * @param marketInfoDTO the marketInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketInfoDTO,
     * or with status 400 (Bad Request) if the marketInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the marketInfoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-infos")
    @Timed
    public ResponseEntity<MarketInfoDTO> updateMarketInfo(@RequestBody MarketInfoDTO marketInfoDTO) throws URISyntaxException {
        log.debug("REST request to update MarketInfo : {}", marketInfoDTO);
        if (marketInfoDTO.getId() == null) {
            return createMarketInfo(marketInfoDTO);
        }
        MarketInfo marketInfo = marketInfoMapper.toEntity(marketInfoDTO);
        marketInfo = marketInfoRepository.save(marketInfo);
        MarketInfoDTO result = marketInfoMapper.toDto(marketInfo);
        marketInfoSearchRepository.save(marketInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-infos : get all the marketInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketInfos in body
     */
    @GetMapping("/market-infos")
    @Timed
    public List<MarketInfoDTO> getAllMarketInfos() {
        log.debug("REST request to get all MarketInfos");
        List<MarketInfo> marketInfos = marketInfoRepository.findAll();
        return marketInfoMapper.toDto(marketInfos);
    }

    /**
     * GET  /market-infos/:id : get the "id" marketInfo.
     *
     * @param id the id of the marketInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/market-infos/{id}")
    @Timed
    public ResponseEntity<MarketInfoDTO> getMarketInfo(@PathVariable Long id) {
        log.debug("REST request to get MarketInfo : {}", id);
        MarketInfo marketInfo = marketInfoRepository.findOne(id);
        MarketInfoDTO marketInfoDTO = marketInfoMapper.toDto(marketInfo);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketInfoDTO));
    }

    /**
     * DELETE  /market-infos/:id : delete the "id" marketInfo.
     *
     * @param id the id of the marketInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketInfo(@PathVariable Long id) {
        log.debug("REST request to delete MarketInfo : {}", id);
        marketInfoRepository.delete(id);
        marketInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/market-infos?query=:query : search for the marketInfo corresponding
     * to the query.
     *
     * @param query the query of the marketInfo search 
     * @return the result of the search
     */
    @GetMapping("/_search/market-infos")
    @Timed
    public List<MarketInfoDTO> searchMarketInfos(@RequestParam String query) {
        log.debug("REST request to search MarketInfos for query {}", query);
        return StreamSupport
            .stream(marketInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(marketInfoMapper::toDto)
            .collect(Collectors.toList());
    }


}

package fr.sne.marketplace.service;

import fr.sne.marketplace.domain.Market;
import fr.sne.marketplace.repository.MarketRepository;
import fr.sne.marketplace.repository.search.MarketSearchRepository;
import fr.sne.marketplace.service.dto.MarketDTO;
import fr.sne.marketplace.service.mapper.MarketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Market.
 */
@Service
@Transactional
public class MarketService {

    private final Logger log = LoggerFactory.getLogger(MarketService.class);
    
    private final MarketRepository marketRepository;

    private final MarketMapper marketMapper;

    private final MarketSearchRepository marketSearchRepository;

    public MarketService(MarketRepository marketRepository, MarketMapper marketMapper, MarketSearchRepository marketSearchRepository) {
        this.marketRepository = marketRepository;
        this.marketMapper = marketMapper;
        this.marketSearchRepository = marketSearchRepository;
    }

    /**
     * Save a market.
     *
     * @param marketDTO the entity to save
     * @return the persisted entity
     */
    public MarketDTO save(MarketDTO marketDTO) {
        log.debug("Request to save Market : {}", marketDTO);
        Market market = marketMapper.toEntity(marketDTO);
        market = marketRepository.save(market);
        MarketDTO result = marketMapper.toDto(market);
        marketSearchRepository.save(market);
        return result;
    }

    /**
     *  Get all the markets.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MarketDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Markets");
        Page<Market> result = marketRepository.findAll(pageable);
        return result.map(market -> marketMapper.toDto(market));
    }

    /**
     *  Get one market by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MarketDTO findOne(Long id) {
        log.debug("Request to get Market : {}", id);
        Market market = marketRepository.findOne(id);
        MarketDTO marketDTO = marketMapper.toDto(market);
        return marketDTO;
    }

    /**
     *  Delete the  market by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Market : {}", id);
        marketRepository.delete(id);
        marketSearchRepository.delete(id);
    }

    /**
     * Search for the market corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MarketDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Markets for query {}", query);
        Page<Market> result = marketSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(market -> marketMapper.toDto(market));
    }
}

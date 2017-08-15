package fr.sne.market.web.rest;

import fr.sne.market.MarketApp;

import fr.sne.market.domain.Market;
import fr.sne.market.repository.MarketRepository;
import fr.sne.market.service.MarketService;
import fr.sne.market.repository.search.MarketSearchRepository;
import fr.sne.market.service.dto.MarketDTO;
import fr.sne.market.service.mapper.MarketMapper;
import fr.sne.market.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MarketResource REST controller.
 *
 * @see MarketResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketApp.class)
public class MarketResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private MarketMapper marketMapper;

    @Autowired
    private MarketService marketService;

    @Autowired
    private MarketSearchRepository marketSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketMockMvc;

    private Market market;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketResource marketResource = new MarketResource(marketService);
        this.restMarketMockMvc = MockMvcBuilders.standaloneSetup(marketResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Market createEntity(EntityManager em) {
        Market market = new Market()
            .name(DEFAULT_NAME)
            .content(DEFAULT_CONTENT);
        return market;
    }

    @Before
    public void initTest() {
        marketSearchRepository.deleteAll();
        market = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarket() throws Exception {
        int databaseSizeBeforeCreate = marketRepository.findAll().size();

        // Create the Market
        MarketDTO marketDTO = marketMapper.toDto(market);
        restMarketMockMvc.perform(post("/api/markets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketDTO)))
            .andExpect(status().isCreated());

        // Validate the Market in the database
        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeCreate + 1);
        Market testMarket = marketList.get(marketList.size() - 1);
        assertThat(testMarket.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMarket.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the Market in Elasticsearch
        Market marketEs = marketSearchRepository.findOne(testMarket.getId());
        assertThat(marketEs).isEqualToComparingFieldByField(testMarket);
    }

    @Test
    @Transactional
    public void createMarketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketRepository.findAll().size();

        // Create the Market with an existing ID
        market.setId(1L);
        MarketDTO marketDTO = marketMapper.toDto(market);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketMockMvc.perform(post("/api/markets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketRepository.findAll().size();
        // set the field null
        market.setName(null);

        // Create the Market, which fails.
        MarketDTO marketDTO = marketMapper.toDto(market);

        restMarketMockMvc.perform(post("/api/markets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketDTO)))
            .andExpect(status().isBadRequest());

        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketRepository.findAll().size();
        // set the field null
        market.setContent(null);

        // Create the Market, which fails.
        MarketDTO marketDTO = marketMapper.toDto(market);

        restMarketMockMvc.perform(post("/api/markets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketDTO)))
            .andExpect(status().isBadRequest());

        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarkets() throws Exception {
        // Initialize the database
        marketRepository.saveAndFlush(market);

        // Get all the marketList
        restMarketMockMvc.perform(get("/api/markets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(market.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getMarket() throws Exception {
        // Initialize the database
        marketRepository.saveAndFlush(market);

        // Get the market
        restMarketMockMvc.perform(get("/api/markets/{id}", market.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(market.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarket() throws Exception {
        // Get the market
        restMarketMockMvc.perform(get("/api/markets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarket() throws Exception {
        // Initialize the database
        marketRepository.saveAndFlush(market);
        marketSearchRepository.save(market);
        int databaseSizeBeforeUpdate = marketRepository.findAll().size();

        // Update the market
        Market updatedMarket = marketRepository.findOne(market.getId());
        updatedMarket
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT);
        MarketDTO marketDTO = marketMapper.toDto(updatedMarket);

        restMarketMockMvc.perform(put("/api/markets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketDTO)))
            .andExpect(status().isOk());

        // Validate the Market in the database
        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeUpdate);
        Market testMarket = marketList.get(marketList.size() - 1);
        assertThat(testMarket.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMarket.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the Market in Elasticsearch
        Market marketEs = marketSearchRepository.findOne(testMarket.getId());
        assertThat(marketEs).isEqualToComparingFieldByField(testMarket);
    }

    @Test
    @Transactional
    public void updateNonExistingMarket() throws Exception {
        int databaseSizeBeforeUpdate = marketRepository.findAll().size();

        // Create the Market
        MarketDTO marketDTO = marketMapper.toDto(market);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketMockMvc.perform(put("/api/markets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketDTO)))
            .andExpect(status().isCreated());

        // Validate the Market in the database
        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarket() throws Exception {
        // Initialize the database
        marketRepository.saveAndFlush(market);
        marketSearchRepository.save(market);
        int databaseSizeBeforeDelete = marketRepository.findAll().size();

        // Get the market
        restMarketMockMvc.perform(delete("/api/markets/{id}", market.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean marketExistsInEs = marketSearchRepository.exists(market.getId());
        assertThat(marketExistsInEs).isFalse();

        // Validate the database is empty
        List<Market> marketList = marketRepository.findAll();
        assertThat(marketList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMarket() throws Exception {
        // Initialize the database
        marketRepository.saveAndFlush(market);
        marketSearchRepository.save(market);

        // Search the market
        restMarketMockMvc.perform(get("/api/_search/markets?query=id:" + market.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(market.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Market.class);
        Market market1 = new Market();
        market1.setId(1L);
        Market market2 = new Market();
        market2.setId(market1.getId());
        assertThat(market1).isEqualTo(market2);
        market2.setId(2L);
        assertThat(market1).isNotEqualTo(market2);
        market1.setId(null);
        assertThat(market1).isNotEqualTo(market2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketDTO.class);
        MarketDTO marketDTO1 = new MarketDTO();
        marketDTO1.setId(1L);
        MarketDTO marketDTO2 = new MarketDTO();
        assertThat(marketDTO1).isNotEqualTo(marketDTO2);
        marketDTO2.setId(marketDTO1.getId());
        assertThat(marketDTO1).isEqualTo(marketDTO2);
        marketDTO2.setId(2L);
        assertThat(marketDTO1).isNotEqualTo(marketDTO2);
        marketDTO1.setId(null);
        assertThat(marketDTO1).isNotEqualTo(marketDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marketMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marketMapper.fromId(null)).isNull();
    }
}

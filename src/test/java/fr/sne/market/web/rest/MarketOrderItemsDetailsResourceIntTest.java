package fr.sne.market.web.rest;

import fr.sne.market.MarketApp;

import fr.sne.market.domain.MarketOrderItemsDetails;
import fr.sne.market.repository.MarketOrderItemsDetailsRepository;
import fr.sne.market.repository.search.MarketOrderItemsDetailsSearchRepository;
import fr.sne.market.service.dto.MarketOrderItemsDetailsDTO;
import fr.sne.market.service.mapper.MarketOrderItemsDetailsMapper;
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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MarketOrderItemsDetailsResource REST controller.
 *
 * @see MarketOrderItemsDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketApp.class)
public class MarketOrderItemsDetailsResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private MarketOrderItemsDetailsRepository marketOrderItemsDetailsRepository;

    @Autowired
    private MarketOrderItemsDetailsMapper marketOrderItemsDetailsMapper;

    @Autowired
    private MarketOrderItemsDetailsSearchRepository marketOrderItemsDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketOrderItemsDetailsMockMvc;

    private MarketOrderItemsDetails marketOrderItemsDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketOrderItemsDetailsResource marketOrderItemsDetailsResource = new MarketOrderItemsDetailsResource(marketOrderItemsDetailsRepository, marketOrderItemsDetailsMapper, marketOrderItemsDetailsSearchRepository);
        this.restMarketOrderItemsDetailsMockMvc = MockMvcBuilders.standaloneSetup(marketOrderItemsDetailsResource)
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
    public static MarketOrderItemsDetails createEntity(EntityManager em) {
        MarketOrderItemsDetails marketOrderItemsDetails = new MarketOrderItemsDetails()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return marketOrderItemsDetails;
    }

    @Before
    public void initTest() {
        marketOrderItemsDetailsSearchRepository.deleteAll();
        marketOrderItemsDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketOrderItemsDetails() throws Exception {
        int databaseSizeBeforeCreate = marketOrderItemsDetailsRepository.findAll().size();

        // Create the MarketOrderItemsDetails
        MarketOrderItemsDetailsDTO marketOrderItemsDetailsDTO = marketOrderItemsDetailsMapper.toDto(marketOrderItemsDetails);
        restMarketOrderItemsDetailsMockMvc.perform(post("/api/market-order-items-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderItemsDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketOrderItemsDetails in the database
        List<MarketOrderItemsDetails> marketOrderItemsDetailsList = marketOrderItemsDetailsRepository.findAll();
        assertThat(marketOrderItemsDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        MarketOrderItemsDetails testMarketOrderItemsDetails = marketOrderItemsDetailsList.get(marketOrderItemsDetailsList.size() - 1);
        assertThat(testMarketOrderItemsDetails.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testMarketOrderItemsDetails.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the MarketOrderItemsDetails in Elasticsearch
        MarketOrderItemsDetails marketOrderItemsDetailsEs = marketOrderItemsDetailsSearchRepository.findOne(testMarketOrderItemsDetails.getId());
        assertThat(marketOrderItemsDetailsEs).isEqualToComparingFieldByField(testMarketOrderItemsDetails);
    }

    @Test
    @Transactional
    public void createMarketOrderItemsDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketOrderItemsDetailsRepository.findAll().size();

        // Create the MarketOrderItemsDetails with an existing ID
        marketOrderItemsDetails.setId(1L);
        MarketOrderItemsDetailsDTO marketOrderItemsDetailsDTO = marketOrderItemsDetailsMapper.toDto(marketOrderItemsDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketOrderItemsDetailsMockMvc.perform(post("/api/market-order-items-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderItemsDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketOrderItemsDetails> marketOrderItemsDetailsList = marketOrderItemsDetailsRepository.findAll();
        assertThat(marketOrderItemsDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketOrderItemsDetails() throws Exception {
        // Initialize the database
        marketOrderItemsDetailsRepository.saveAndFlush(marketOrderItemsDetails);

        // Get all the marketOrderItemsDetailsList
        restMarketOrderItemsDetailsMockMvc.perform(get("/api/market-order-items-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketOrderItemsDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getMarketOrderItemsDetails() throws Exception {
        // Initialize the database
        marketOrderItemsDetailsRepository.saveAndFlush(marketOrderItemsDetails);

        // Get the marketOrderItemsDetails
        restMarketOrderItemsDetailsMockMvc.perform(get("/api/market-order-items-details/{id}", marketOrderItemsDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketOrderItemsDetails.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketOrderItemsDetails() throws Exception {
        // Get the marketOrderItemsDetails
        restMarketOrderItemsDetailsMockMvc.perform(get("/api/market-order-items-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketOrderItemsDetails() throws Exception {
        // Initialize the database
        marketOrderItemsDetailsRepository.saveAndFlush(marketOrderItemsDetails);
        marketOrderItemsDetailsSearchRepository.save(marketOrderItemsDetails);
        int databaseSizeBeforeUpdate = marketOrderItemsDetailsRepository.findAll().size();

        // Update the marketOrderItemsDetails
        MarketOrderItemsDetails updatedMarketOrderItemsDetails = marketOrderItemsDetailsRepository.findOne(marketOrderItemsDetails.getId());
        updatedMarketOrderItemsDetails
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        MarketOrderItemsDetailsDTO marketOrderItemsDetailsDTO = marketOrderItemsDetailsMapper.toDto(updatedMarketOrderItemsDetails);

        restMarketOrderItemsDetailsMockMvc.perform(put("/api/market-order-items-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderItemsDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the MarketOrderItemsDetails in the database
        List<MarketOrderItemsDetails> marketOrderItemsDetailsList = marketOrderItemsDetailsRepository.findAll();
        assertThat(marketOrderItemsDetailsList).hasSize(databaseSizeBeforeUpdate);
        MarketOrderItemsDetails testMarketOrderItemsDetails = marketOrderItemsDetailsList.get(marketOrderItemsDetailsList.size() - 1);
        assertThat(testMarketOrderItemsDetails.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testMarketOrderItemsDetails.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the MarketOrderItemsDetails in Elasticsearch
        MarketOrderItemsDetails marketOrderItemsDetailsEs = marketOrderItemsDetailsSearchRepository.findOne(testMarketOrderItemsDetails.getId());
        assertThat(marketOrderItemsDetailsEs).isEqualToComparingFieldByField(testMarketOrderItemsDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketOrderItemsDetails() throws Exception {
        int databaseSizeBeforeUpdate = marketOrderItemsDetailsRepository.findAll().size();

        // Create the MarketOrderItemsDetails
        MarketOrderItemsDetailsDTO marketOrderItemsDetailsDTO = marketOrderItemsDetailsMapper.toDto(marketOrderItemsDetails);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketOrderItemsDetailsMockMvc.perform(put("/api/market-order-items-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderItemsDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketOrderItemsDetails in the database
        List<MarketOrderItemsDetails> marketOrderItemsDetailsList = marketOrderItemsDetailsRepository.findAll();
        assertThat(marketOrderItemsDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketOrderItemsDetails() throws Exception {
        // Initialize the database
        marketOrderItemsDetailsRepository.saveAndFlush(marketOrderItemsDetails);
        marketOrderItemsDetailsSearchRepository.save(marketOrderItemsDetails);
        int databaseSizeBeforeDelete = marketOrderItemsDetailsRepository.findAll().size();

        // Get the marketOrderItemsDetails
        restMarketOrderItemsDetailsMockMvc.perform(delete("/api/market-order-items-details/{id}", marketOrderItemsDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean marketOrderItemsDetailsExistsInEs = marketOrderItemsDetailsSearchRepository.exists(marketOrderItemsDetails.getId());
        assertThat(marketOrderItemsDetailsExistsInEs).isFalse();

        // Validate the database is empty
        List<MarketOrderItemsDetails> marketOrderItemsDetailsList = marketOrderItemsDetailsRepository.findAll();
        assertThat(marketOrderItemsDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMarketOrderItemsDetails() throws Exception {
        // Initialize the database
        marketOrderItemsDetailsRepository.saveAndFlush(marketOrderItemsDetails);
        marketOrderItemsDetailsSearchRepository.save(marketOrderItemsDetails);

        // Search the marketOrderItemsDetails
        restMarketOrderItemsDetailsMockMvc.perform(get("/api/_search/market-order-items-details?query=id:" + marketOrderItemsDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketOrderItemsDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketOrderItemsDetails.class);
        MarketOrderItemsDetails marketOrderItemsDetails1 = new MarketOrderItemsDetails();
        marketOrderItemsDetails1.setId(1L);
        MarketOrderItemsDetails marketOrderItemsDetails2 = new MarketOrderItemsDetails();
        marketOrderItemsDetails2.setId(marketOrderItemsDetails1.getId());
        assertThat(marketOrderItemsDetails1).isEqualTo(marketOrderItemsDetails2);
        marketOrderItemsDetails2.setId(2L);
        assertThat(marketOrderItemsDetails1).isNotEqualTo(marketOrderItemsDetails2);
        marketOrderItemsDetails1.setId(null);
        assertThat(marketOrderItemsDetails1).isNotEqualTo(marketOrderItemsDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketOrderItemsDetailsDTO.class);
        MarketOrderItemsDetailsDTO marketOrderItemsDetailsDTO1 = new MarketOrderItemsDetailsDTO();
        marketOrderItemsDetailsDTO1.setId(1L);
        MarketOrderItemsDetailsDTO marketOrderItemsDetailsDTO2 = new MarketOrderItemsDetailsDTO();
        assertThat(marketOrderItemsDetailsDTO1).isNotEqualTo(marketOrderItemsDetailsDTO2);
        marketOrderItemsDetailsDTO2.setId(marketOrderItemsDetailsDTO1.getId());
        assertThat(marketOrderItemsDetailsDTO1).isEqualTo(marketOrderItemsDetailsDTO2);
        marketOrderItemsDetailsDTO2.setId(2L);
        assertThat(marketOrderItemsDetailsDTO1).isNotEqualTo(marketOrderItemsDetailsDTO2);
        marketOrderItemsDetailsDTO1.setId(null);
        assertThat(marketOrderItemsDetailsDTO1).isNotEqualTo(marketOrderItemsDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marketOrderItemsDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marketOrderItemsDetailsMapper.fromId(null)).isNull();
    }
}

package fr.sne.market.web.rest;

import fr.sne.market.MarketApp;

import fr.sne.market.domain.MarketOrderline;
import fr.sne.market.repository.MarketOrderlineRepository;
import fr.sne.market.repository.search.MarketOrderlineSearchRepository;
import fr.sne.market.service.dto.MarketOrderlineDTO;
import fr.sne.market.service.mapper.MarketOrderlineMapper;
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
 * Test class for the MarketOrderlineResource REST controller.
 *
 * @see MarketOrderlineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketApp.class)
public class MarketOrderlineResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private MarketOrderlineRepository marketOrderlineRepository;

    @Autowired
    private MarketOrderlineMapper marketOrderlineMapper;

    @Autowired
    private MarketOrderlineSearchRepository marketOrderlineSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketOrderlineMockMvc;

    private MarketOrderline marketOrderline;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketOrderlineResource marketOrderlineResource = new MarketOrderlineResource(marketOrderlineRepository, marketOrderlineMapper, marketOrderlineSearchRepository);
        this.restMarketOrderlineMockMvc = MockMvcBuilders.standaloneSetup(marketOrderlineResource)
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
    public static MarketOrderline createEntity(EntityManager em) {
        MarketOrderline marketOrderline = new MarketOrderline()
            .quantity(DEFAULT_QUANTITY);
        return marketOrderline;
    }

    @Before
    public void initTest() {
        marketOrderlineSearchRepository.deleteAll();
        marketOrderline = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketOrderline() throws Exception {
        int databaseSizeBeforeCreate = marketOrderlineRepository.findAll().size();

        // Create the MarketOrderline
        MarketOrderlineDTO marketOrderlineDTO = marketOrderlineMapper.toDto(marketOrderline);
        restMarketOrderlineMockMvc.perform(post("/api/market-orderlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderlineDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketOrderline in the database
        List<MarketOrderline> marketOrderlineList = marketOrderlineRepository.findAll();
        assertThat(marketOrderlineList).hasSize(databaseSizeBeforeCreate + 1);
        MarketOrderline testMarketOrderline = marketOrderlineList.get(marketOrderlineList.size() - 1);
        assertThat(testMarketOrderline.getQuantity()).isEqualTo(DEFAULT_QUANTITY);

        // Validate the MarketOrderline in Elasticsearch
        MarketOrderline marketOrderlineEs = marketOrderlineSearchRepository.findOne(testMarketOrderline.getId());
        assertThat(marketOrderlineEs).isEqualToComparingFieldByField(testMarketOrderline);
    }

    @Test
    @Transactional
    public void createMarketOrderlineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketOrderlineRepository.findAll().size();

        // Create the MarketOrderline with an existing ID
        marketOrderline.setId(1L);
        MarketOrderlineDTO marketOrderlineDTO = marketOrderlineMapper.toDto(marketOrderline);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketOrderlineMockMvc.perform(post("/api/market-orderlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderlineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketOrderline> marketOrderlineList = marketOrderlineRepository.findAll();
        assertThat(marketOrderlineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketOrderlines() throws Exception {
        // Initialize the database
        marketOrderlineRepository.saveAndFlush(marketOrderline);

        // Get all the marketOrderlineList
        restMarketOrderlineMockMvc.perform(get("/api/market-orderlines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketOrderline.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    public void getMarketOrderline() throws Exception {
        // Initialize the database
        marketOrderlineRepository.saveAndFlush(marketOrderline);

        // Get the marketOrderline
        restMarketOrderlineMockMvc.perform(get("/api/market-orderlines/{id}", marketOrderline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketOrderline.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingMarketOrderline() throws Exception {
        // Get the marketOrderline
        restMarketOrderlineMockMvc.perform(get("/api/market-orderlines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketOrderline() throws Exception {
        // Initialize the database
        marketOrderlineRepository.saveAndFlush(marketOrderline);
        marketOrderlineSearchRepository.save(marketOrderline);
        int databaseSizeBeforeUpdate = marketOrderlineRepository.findAll().size();

        // Update the marketOrderline
        MarketOrderline updatedMarketOrderline = marketOrderlineRepository.findOne(marketOrderline.getId());
        updatedMarketOrderline
            .quantity(UPDATED_QUANTITY);
        MarketOrderlineDTO marketOrderlineDTO = marketOrderlineMapper.toDto(updatedMarketOrderline);

        restMarketOrderlineMockMvc.perform(put("/api/market-orderlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderlineDTO)))
            .andExpect(status().isOk());

        // Validate the MarketOrderline in the database
        List<MarketOrderline> marketOrderlineList = marketOrderlineRepository.findAll();
        assertThat(marketOrderlineList).hasSize(databaseSizeBeforeUpdate);
        MarketOrderline testMarketOrderline = marketOrderlineList.get(marketOrderlineList.size() - 1);
        assertThat(testMarketOrderline.getQuantity()).isEqualTo(UPDATED_QUANTITY);

        // Validate the MarketOrderline in Elasticsearch
        MarketOrderline marketOrderlineEs = marketOrderlineSearchRepository.findOne(testMarketOrderline.getId());
        assertThat(marketOrderlineEs).isEqualToComparingFieldByField(testMarketOrderline);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketOrderline() throws Exception {
        int databaseSizeBeforeUpdate = marketOrderlineRepository.findAll().size();

        // Create the MarketOrderline
        MarketOrderlineDTO marketOrderlineDTO = marketOrderlineMapper.toDto(marketOrderline);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketOrderlineMockMvc.perform(put("/api/market-orderlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderlineDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketOrderline in the database
        List<MarketOrderline> marketOrderlineList = marketOrderlineRepository.findAll();
        assertThat(marketOrderlineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketOrderline() throws Exception {
        // Initialize the database
        marketOrderlineRepository.saveAndFlush(marketOrderline);
        marketOrderlineSearchRepository.save(marketOrderline);
        int databaseSizeBeforeDelete = marketOrderlineRepository.findAll().size();

        // Get the marketOrderline
        restMarketOrderlineMockMvc.perform(delete("/api/market-orderlines/{id}", marketOrderline.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean marketOrderlineExistsInEs = marketOrderlineSearchRepository.exists(marketOrderline.getId());
        assertThat(marketOrderlineExistsInEs).isFalse();

        // Validate the database is empty
        List<MarketOrderline> marketOrderlineList = marketOrderlineRepository.findAll();
        assertThat(marketOrderlineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMarketOrderline() throws Exception {
        // Initialize the database
        marketOrderlineRepository.saveAndFlush(marketOrderline);
        marketOrderlineSearchRepository.save(marketOrderline);

        // Search the marketOrderline
        restMarketOrderlineMockMvc.perform(get("/api/_search/market-orderlines?query=id:" + marketOrderline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketOrderline.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketOrderline.class);
        MarketOrderline marketOrderline1 = new MarketOrderline();
        marketOrderline1.setId(1L);
        MarketOrderline marketOrderline2 = new MarketOrderline();
        marketOrderline2.setId(marketOrderline1.getId());
        assertThat(marketOrderline1).isEqualTo(marketOrderline2);
        marketOrderline2.setId(2L);
        assertThat(marketOrderline1).isNotEqualTo(marketOrderline2);
        marketOrderline1.setId(null);
        assertThat(marketOrderline1).isNotEqualTo(marketOrderline2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketOrderlineDTO.class);
        MarketOrderlineDTO marketOrderlineDTO1 = new MarketOrderlineDTO();
        marketOrderlineDTO1.setId(1L);
        MarketOrderlineDTO marketOrderlineDTO2 = new MarketOrderlineDTO();
        assertThat(marketOrderlineDTO1).isNotEqualTo(marketOrderlineDTO2);
        marketOrderlineDTO2.setId(marketOrderlineDTO1.getId());
        assertThat(marketOrderlineDTO1).isEqualTo(marketOrderlineDTO2);
        marketOrderlineDTO2.setId(2L);
        assertThat(marketOrderlineDTO1).isNotEqualTo(marketOrderlineDTO2);
        marketOrderlineDTO1.setId(null);
        assertThat(marketOrderlineDTO1).isNotEqualTo(marketOrderlineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marketOrderlineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marketOrderlineMapper.fromId(null)).isNull();
    }
}

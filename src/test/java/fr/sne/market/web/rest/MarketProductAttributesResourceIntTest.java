package fr.sne.market.web.rest;

import fr.sne.market.MarketApp;

import fr.sne.market.domain.MarketProductAttributes;
import fr.sne.market.repository.MarketProductAttributesRepository;
import fr.sne.market.repository.search.MarketProductAttributesSearchRepository;
import fr.sne.market.service.dto.MarketProductAttributesDTO;
import fr.sne.market.service.mapper.MarketProductAttributesMapper;
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
 * Test class for the MarketProductAttributesResource REST controller.
 *
 * @see MarketProductAttributesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketApp.class)
public class MarketProductAttributesResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private MarketProductAttributesRepository marketProductAttributesRepository;

    @Autowired
    private MarketProductAttributesMapper marketProductAttributesMapper;

    @Autowired
    private MarketProductAttributesSearchRepository marketProductAttributesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketProductAttributesMockMvc;

    private MarketProductAttributes marketProductAttributes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketProductAttributesResource marketProductAttributesResource = new MarketProductAttributesResource(marketProductAttributesRepository, marketProductAttributesMapper, marketProductAttributesSearchRepository);
        this.restMarketProductAttributesMockMvc = MockMvcBuilders.standaloneSetup(marketProductAttributesResource)
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
    public static MarketProductAttributes createEntity(EntityManager em) {
        MarketProductAttributes marketProductAttributes = new MarketProductAttributes()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return marketProductAttributes;
    }

    @Before
    public void initTest() {
        marketProductAttributesSearchRepository.deleteAll();
        marketProductAttributes = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketProductAttributes() throws Exception {
        int databaseSizeBeforeCreate = marketProductAttributesRepository.findAll().size();

        // Create the MarketProductAttributes
        MarketProductAttributesDTO marketProductAttributesDTO = marketProductAttributesMapper.toDto(marketProductAttributes);
        restMarketProductAttributesMockMvc.perform(post("/api/market-product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductAttributesDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketProductAttributes in the database
        List<MarketProductAttributes> marketProductAttributesList = marketProductAttributesRepository.findAll();
        assertThat(marketProductAttributesList).hasSize(databaseSizeBeforeCreate + 1);
        MarketProductAttributes testMarketProductAttributes = marketProductAttributesList.get(marketProductAttributesList.size() - 1);
        assertThat(testMarketProductAttributes.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testMarketProductAttributes.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the MarketProductAttributes in Elasticsearch
        MarketProductAttributes marketProductAttributesEs = marketProductAttributesSearchRepository.findOne(testMarketProductAttributes.getId());
        assertThat(marketProductAttributesEs).isEqualToComparingFieldByField(testMarketProductAttributes);
    }

    @Test
    @Transactional
    public void createMarketProductAttributesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketProductAttributesRepository.findAll().size();

        // Create the MarketProductAttributes with an existing ID
        marketProductAttributes.setId(1L);
        MarketProductAttributesDTO marketProductAttributesDTO = marketProductAttributesMapper.toDto(marketProductAttributes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketProductAttributesMockMvc.perform(post("/api/market-product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductAttributesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketProductAttributes> marketProductAttributesList = marketProductAttributesRepository.findAll();
        assertThat(marketProductAttributesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketProductAttributes() throws Exception {
        // Initialize the database
        marketProductAttributesRepository.saveAndFlush(marketProductAttributes);

        // Get all the marketProductAttributesList
        restMarketProductAttributesMockMvc.perform(get("/api/market-product-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketProductAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getMarketProductAttributes() throws Exception {
        // Initialize the database
        marketProductAttributesRepository.saveAndFlush(marketProductAttributes);

        // Get the marketProductAttributes
        restMarketProductAttributesMockMvc.perform(get("/api/market-product-attributes/{id}", marketProductAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketProductAttributes.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketProductAttributes() throws Exception {
        // Get the marketProductAttributes
        restMarketProductAttributesMockMvc.perform(get("/api/market-product-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketProductAttributes() throws Exception {
        // Initialize the database
        marketProductAttributesRepository.saveAndFlush(marketProductAttributes);
        marketProductAttributesSearchRepository.save(marketProductAttributes);
        int databaseSizeBeforeUpdate = marketProductAttributesRepository.findAll().size();

        // Update the marketProductAttributes
        MarketProductAttributes updatedMarketProductAttributes = marketProductAttributesRepository.findOne(marketProductAttributes.getId());
        updatedMarketProductAttributes
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        MarketProductAttributesDTO marketProductAttributesDTO = marketProductAttributesMapper.toDto(updatedMarketProductAttributes);

        restMarketProductAttributesMockMvc.perform(put("/api/market-product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductAttributesDTO)))
            .andExpect(status().isOk());

        // Validate the MarketProductAttributes in the database
        List<MarketProductAttributes> marketProductAttributesList = marketProductAttributesRepository.findAll();
        assertThat(marketProductAttributesList).hasSize(databaseSizeBeforeUpdate);
        MarketProductAttributes testMarketProductAttributes = marketProductAttributesList.get(marketProductAttributesList.size() - 1);
        assertThat(testMarketProductAttributes.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testMarketProductAttributes.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the MarketProductAttributes in Elasticsearch
        MarketProductAttributes marketProductAttributesEs = marketProductAttributesSearchRepository.findOne(testMarketProductAttributes.getId());
        assertThat(marketProductAttributesEs).isEqualToComparingFieldByField(testMarketProductAttributes);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketProductAttributes() throws Exception {
        int databaseSizeBeforeUpdate = marketProductAttributesRepository.findAll().size();

        // Create the MarketProductAttributes
        MarketProductAttributesDTO marketProductAttributesDTO = marketProductAttributesMapper.toDto(marketProductAttributes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketProductAttributesMockMvc.perform(put("/api/market-product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductAttributesDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketProductAttributes in the database
        List<MarketProductAttributes> marketProductAttributesList = marketProductAttributesRepository.findAll();
        assertThat(marketProductAttributesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketProductAttributes() throws Exception {
        // Initialize the database
        marketProductAttributesRepository.saveAndFlush(marketProductAttributes);
        marketProductAttributesSearchRepository.save(marketProductAttributes);
        int databaseSizeBeforeDelete = marketProductAttributesRepository.findAll().size();

        // Get the marketProductAttributes
        restMarketProductAttributesMockMvc.perform(delete("/api/market-product-attributes/{id}", marketProductAttributes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean marketProductAttributesExistsInEs = marketProductAttributesSearchRepository.exists(marketProductAttributes.getId());
        assertThat(marketProductAttributesExistsInEs).isFalse();

        // Validate the database is empty
        List<MarketProductAttributes> marketProductAttributesList = marketProductAttributesRepository.findAll();
        assertThat(marketProductAttributesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMarketProductAttributes() throws Exception {
        // Initialize the database
        marketProductAttributesRepository.saveAndFlush(marketProductAttributes);
        marketProductAttributesSearchRepository.save(marketProductAttributes);

        // Search the marketProductAttributes
        restMarketProductAttributesMockMvc.perform(get("/api/_search/market-product-attributes?query=id:" + marketProductAttributes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketProductAttributes.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketProductAttributes.class);
        MarketProductAttributes marketProductAttributes1 = new MarketProductAttributes();
        marketProductAttributes1.setId(1L);
        MarketProductAttributes marketProductAttributes2 = new MarketProductAttributes();
        marketProductAttributes2.setId(marketProductAttributes1.getId());
        assertThat(marketProductAttributes1).isEqualTo(marketProductAttributes2);
        marketProductAttributes2.setId(2L);
        assertThat(marketProductAttributes1).isNotEqualTo(marketProductAttributes2);
        marketProductAttributes1.setId(null);
        assertThat(marketProductAttributes1).isNotEqualTo(marketProductAttributes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketProductAttributesDTO.class);
        MarketProductAttributesDTO marketProductAttributesDTO1 = new MarketProductAttributesDTO();
        marketProductAttributesDTO1.setId(1L);
        MarketProductAttributesDTO marketProductAttributesDTO2 = new MarketProductAttributesDTO();
        assertThat(marketProductAttributesDTO1).isNotEqualTo(marketProductAttributesDTO2);
        marketProductAttributesDTO2.setId(marketProductAttributesDTO1.getId());
        assertThat(marketProductAttributesDTO1).isEqualTo(marketProductAttributesDTO2);
        marketProductAttributesDTO2.setId(2L);
        assertThat(marketProductAttributesDTO1).isNotEqualTo(marketProductAttributesDTO2);
        marketProductAttributesDTO1.setId(null);
        assertThat(marketProductAttributesDTO1).isNotEqualTo(marketProductAttributesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marketProductAttributesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marketProductAttributesMapper.fromId(null)).isNull();
    }
}

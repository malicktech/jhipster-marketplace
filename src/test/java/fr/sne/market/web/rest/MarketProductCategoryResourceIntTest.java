package fr.sne.market.web.rest;

import fr.sne.market.MarketApp;

import fr.sne.market.domain.MarketProductCategory;
import fr.sne.market.repository.MarketProductCategoryRepository;
import fr.sne.market.repository.search.MarketProductCategorySearchRepository;
import fr.sne.market.service.dto.MarketProductCategoryDTO;
import fr.sne.market.service.mapper.MarketProductCategoryMapper;
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
 * Test class for the MarketProductCategoryResource REST controller.
 *
 * @see MarketProductCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketApp.class)
public class MarketProductCategoryResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MarketProductCategoryRepository marketProductCategoryRepository;

    @Autowired
    private MarketProductCategoryMapper marketProductCategoryMapper;

    @Autowired
    private MarketProductCategorySearchRepository marketProductCategorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketProductCategoryMockMvc;

    private MarketProductCategory marketProductCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketProductCategoryResource marketProductCategoryResource = new MarketProductCategoryResource(marketProductCategoryRepository, marketProductCategoryMapper, marketProductCategorySearchRepository);
        this.restMarketProductCategoryMockMvc = MockMvcBuilders.standaloneSetup(marketProductCategoryResource)
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
    public static MarketProductCategory createEntity(EntityManager em) {
        MarketProductCategory marketProductCategory = new MarketProductCategory()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION);
        return marketProductCategory;
    }

    @Before
    public void initTest() {
        marketProductCategorySearchRepository.deleteAll();
        marketProductCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketProductCategory() throws Exception {
        int databaseSizeBeforeCreate = marketProductCategoryRepository.findAll().size();

        // Create the MarketProductCategory
        MarketProductCategoryDTO marketProductCategoryDTO = marketProductCategoryMapper.toDto(marketProductCategory);
        restMarketProductCategoryMockMvc.perform(post("/api/market-product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketProductCategory in the database
        List<MarketProductCategory> marketProductCategoryList = marketProductCategoryRepository.findAll();
        assertThat(marketProductCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        MarketProductCategory testMarketProductCategory = marketProductCategoryList.get(marketProductCategoryList.size() - 1);
        assertThat(testMarketProductCategory.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMarketProductCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the MarketProductCategory in Elasticsearch
        MarketProductCategory marketProductCategoryEs = marketProductCategorySearchRepository.findOne(testMarketProductCategory.getId());
        assertThat(marketProductCategoryEs).isEqualToComparingFieldByField(testMarketProductCategory);
    }

    @Test
    @Transactional
    public void createMarketProductCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketProductCategoryRepository.findAll().size();

        // Create the MarketProductCategory with an existing ID
        marketProductCategory.setId(1L);
        MarketProductCategoryDTO marketProductCategoryDTO = marketProductCategoryMapper.toDto(marketProductCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketProductCategoryMockMvc.perform(post("/api/market-product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketProductCategory> marketProductCategoryList = marketProductCategoryRepository.findAll();
        assertThat(marketProductCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketProductCategoryRepository.findAll().size();
        // set the field null
        marketProductCategory.setTitle(null);

        // Create the MarketProductCategory, which fails.
        MarketProductCategoryDTO marketProductCategoryDTO = marketProductCategoryMapper.toDto(marketProductCategory);

        restMarketProductCategoryMockMvc.perform(post("/api/market-product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<MarketProductCategory> marketProductCategoryList = marketProductCategoryRepository.findAll();
        assertThat(marketProductCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarketProductCategories() throws Exception {
        // Initialize the database
        marketProductCategoryRepository.saveAndFlush(marketProductCategory);

        // Get all the marketProductCategoryList
        restMarketProductCategoryMockMvc.perform(get("/api/market-product-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketProductCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getMarketProductCategory() throws Exception {
        // Initialize the database
        marketProductCategoryRepository.saveAndFlush(marketProductCategory);

        // Get the marketProductCategory
        restMarketProductCategoryMockMvc.perform(get("/api/market-product-categories/{id}", marketProductCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketProductCategory.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketProductCategory() throws Exception {
        // Get the marketProductCategory
        restMarketProductCategoryMockMvc.perform(get("/api/market-product-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketProductCategory() throws Exception {
        // Initialize the database
        marketProductCategoryRepository.saveAndFlush(marketProductCategory);
        marketProductCategorySearchRepository.save(marketProductCategory);
        int databaseSizeBeforeUpdate = marketProductCategoryRepository.findAll().size();

        // Update the marketProductCategory
        MarketProductCategory updatedMarketProductCategory = marketProductCategoryRepository.findOne(marketProductCategory.getId());
        updatedMarketProductCategory
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        MarketProductCategoryDTO marketProductCategoryDTO = marketProductCategoryMapper.toDto(updatedMarketProductCategory);

        restMarketProductCategoryMockMvc.perform(put("/api/market-product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the MarketProductCategory in the database
        List<MarketProductCategory> marketProductCategoryList = marketProductCategoryRepository.findAll();
        assertThat(marketProductCategoryList).hasSize(databaseSizeBeforeUpdate);
        MarketProductCategory testMarketProductCategory = marketProductCategoryList.get(marketProductCategoryList.size() - 1);
        assertThat(testMarketProductCategory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMarketProductCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the MarketProductCategory in Elasticsearch
        MarketProductCategory marketProductCategoryEs = marketProductCategorySearchRepository.findOne(testMarketProductCategory.getId());
        assertThat(marketProductCategoryEs).isEqualToComparingFieldByField(testMarketProductCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = marketProductCategoryRepository.findAll().size();

        // Create the MarketProductCategory
        MarketProductCategoryDTO marketProductCategoryDTO = marketProductCategoryMapper.toDto(marketProductCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketProductCategoryMockMvc.perform(put("/api/market-product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketProductCategory in the database
        List<MarketProductCategory> marketProductCategoryList = marketProductCategoryRepository.findAll();
        assertThat(marketProductCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketProductCategory() throws Exception {
        // Initialize the database
        marketProductCategoryRepository.saveAndFlush(marketProductCategory);
        marketProductCategorySearchRepository.save(marketProductCategory);
        int databaseSizeBeforeDelete = marketProductCategoryRepository.findAll().size();

        // Get the marketProductCategory
        restMarketProductCategoryMockMvc.perform(delete("/api/market-product-categories/{id}", marketProductCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean marketProductCategoryExistsInEs = marketProductCategorySearchRepository.exists(marketProductCategory.getId());
        assertThat(marketProductCategoryExistsInEs).isFalse();

        // Validate the database is empty
        List<MarketProductCategory> marketProductCategoryList = marketProductCategoryRepository.findAll();
        assertThat(marketProductCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMarketProductCategory() throws Exception {
        // Initialize the database
        marketProductCategoryRepository.saveAndFlush(marketProductCategory);
        marketProductCategorySearchRepository.save(marketProductCategory);

        // Search the marketProductCategory
        restMarketProductCategoryMockMvc.perform(get("/api/_search/market-product-categories?query=id:" + marketProductCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketProductCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketProductCategory.class);
        MarketProductCategory marketProductCategory1 = new MarketProductCategory();
        marketProductCategory1.setId(1L);
        MarketProductCategory marketProductCategory2 = new MarketProductCategory();
        marketProductCategory2.setId(marketProductCategory1.getId());
        assertThat(marketProductCategory1).isEqualTo(marketProductCategory2);
        marketProductCategory2.setId(2L);
        assertThat(marketProductCategory1).isNotEqualTo(marketProductCategory2);
        marketProductCategory1.setId(null);
        assertThat(marketProductCategory1).isNotEqualTo(marketProductCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketProductCategoryDTO.class);
        MarketProductCategoryDTO marketProductCategoryDTO1 = new MarketProductCategoryDTO();
        marketProductCategoryDTO1.setId(1L);
        MarketProductCategoryDTO marketProductCategoryDTO2 = new MarketProductCategoryDTO();
        assertThat(marketProductCategoryDTO1).isNotEqualTo(marketProductCategoryDTO2);
        marketProductCategoryDTO2.setId(marketProductCategoryDTO1.getId());
        assertThat(marketProductCategoryDTO1).isEqualTo(marketProductCategoryDTO2);
        marketProductCategoryDTO2.setId(2L);
        assertThat(marketProductCategoryDTO1).isNotEqualTo(marketProductCategoryDTO2);
        marketProductCategoryDTO1.setId(null);
        assertThat(marketProductCategoryDTO1).isNotEqualTo(marketProductCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marketProductCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marketProductCategoryMapper.fromId(null)).isNull();
    }
}

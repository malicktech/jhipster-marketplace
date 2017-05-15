package fr.sne.marketplace.web.rest;

import fr.sne.marketplace.MarketplacejhipsterApp;

import fr.sne.marketplace.domain.MarketOrderItems;
import fr.sne.marketplace.repository.MarketOrderItemsRepository;
import fr.sne.marketplace.repository.search.MarketOrderItemsSearchRepository;
import fr.sne.marketplace.service.dto.MarketOrderItemsDTO;
import fr.sne.marketplace.service.mapper.MarketOrderItemsMapper;
import fr.sne.marketplace.web.rest.errors.ExceptionTranslator;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MarketOrderItemsResource REST controller.
 *
 * @see MarketOrderItemsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketplacejhipsterApp.class)
public class MarketOrderItemsResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MarketOrderItemsRepository marketOrderItemsRepository;

    @Autowired
    private MarketOrderItemsMapper marketOrderItemsMapper;

    @Autowired
    private MarketOrderItemsSearchRepository marketOrderItemsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketOrderItemsMockMvc;

    private MarketOrderItems marketOrderItems;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketOrderItemsResource marketOrderItemsResource = new MarketOrderItemsResource(marketOrderItemsRepository, marketOrderItemsMapper, marketOrderItemsSearchRepository);
        this.restMarketOrderItemsMockMvc = MockMvcBuilders.standaloneSetup(marketOrderItemsResource)
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
    public static MarketOrderItems createEntity(EntityManager em) {
        MarketOrderItems marketOrderItems = new MarketOrderItems()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .name(DEFAULT_NAME);
        return marketOrderItems;
    }

    @Before
    public void initTest() {
        marketOrderItemsSearchRepository.deleteAll();
        marketOrderItems = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketOrderItems() throws Exception {
        int databaseSizeBeforeCreate = marketOrderItemsRepository.findAll().size();

        // Create the MarketOrderItems
        MarketOrderItemsDTO marketOrderItemsDTO = marketOrderItemsMapper.toDto(marketOrderItems);
        restMarketOrderItemsMockMvc.perform(post("/api/market-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketOrderItems in the database
        List<MarketOrderItems> marketOrderItemsList = marketOrderItemsRepository.findAll();
        assertThat(marketOrderItemsList).hasSize(databaseSizeBeforeCreate + 1);
        MarketOrderItems testMarketOrderItems = marketOrderItemsList.get(marketOrderItemsList.size() - 1);
        assertThat(testMarketOrderItems.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMarketOrderItems.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketOrderItems.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMarketOrderItems.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testMarketOrderItems.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testMarketOrderItems.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the MarketOrderItems in Elasticsearch
        MarketOrderItems marketOrderItemsEs = marketOrderItemsSearchRepository.findOne(testMarketOrderItems.getId());
        assertThat(marketOrderItemsEs).isEqualToComparingFieldByField(testMarketOrderItems);
    }

    @Test
    @Transactional
    public void createMarketOrderItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketOrderItemsRepository.findAll().size();

        // Create the MarketOrderItems with an existing ID
        marketOrderItems.setId(1L);
        MarketOrderItemsDTO marketOrderItemsDTO = marketOrderItemsMapper.toDto(marketOrderItems);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketOrderItemsMockMvc.perform(post("/api/market-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketOrderItems> marketOrderItemsList = marketOrderItemsRepository.findAll();
        assertThat(marketOrderItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketOrderItemsRepository.findAll().size();
        // set the field null
        marketOrderItems.setTitle(null);

        // Create the MarketOrderItems, which fails.
        MarketOrderItemsDTO marketOrderItemsDTO = marketOrderItemsMapper.toDto(marketOrderItems);

        restMarketOrderItemsMockMvc.perform(post("/api/market-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderItemsDTO)))
            .andExpect(status().isBadRequest());

        List<MarketOrderItems> marketOrderItemsList = marketOrderItemsRepository.findAll();
        assertThat(marketOrderItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarketOrderItems() throws Exception {
        // Initialize the database
        marketOrderItemsRepository.saveAndFlush(marketOrderItems);

        // Get all the marketOrderItemsList
        restMarketOrderItemsMockMvc.perform(get("/api/market-order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketOrderItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMarketOrderItems() throws Exception {
        // Initialize the database
        marketOrderItemsRepository.saveAndFlush(marketOrderItems);

        // Get the marketOrderItems
        restMarketOrderItemsMockMvc.perform(get("/api/market-order-items/{id}", marketOrderItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketOrderItems.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketOrderItems() throws Exception {
        // Get the marketOrderItems
        restMarketOrderItemsMockMvc.perform(get("/api/market-order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketOrderItems() throws Exception {
        // Initialize the database
        marketOrderItemsRepository.saveAndFlush(marketOrderItems);
        marketOrderItemsSearchRepository.save(marketOrderItems);
        int databaseSizeBeforeUpdate = marketOrderItemsRepository.findAll().size();

        // Update the marketOrderItems
        MarketOrderItems updatedMarketOrderItems = marketOrderItemsRepository.findOne(marketOrderItems.getId());
        updatedMarketOrderItems
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .name(UPDATED_NAME);
        MarketOrderItemsDTO marketOrderItemsDTO = marketOrderItemsMapper.toDto(updatedMarketOrderItems);

        restMarketOrderItemsMockMvc.perform(put("/api/market-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderItemsDTO)))
            .andExpect(status().isOk());

        // Validate the MarketOrderItems in the database
        List<MarketOrderItems> marketOrderItemsList = marketOrderItemsRepository.findAll();
        assertThat(marketOrderItemsList).hasSize(databaseSizeBeforeUpdate);
        MarketOrderItems testMarketOrderItems = marketOrderItemsList.get(marketOrderItemsList.size() - 1);
        assertThat(testMarketOrderItems.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMarketOrderItems.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketOrderItems.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMarketOrderItems.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testMarketOrderItems.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testMarketOrderItems.getName()).isEqualTo(UPDATED_NAME);

        // Validate the MarketOrderItems in Elasticsearch
        MarketOrderItems marketOrderItemsEs = marketOrderItemsSearchRepository.findOne(testMarketOrderItems.getId());
        assertThat(marketOrderItemsEs).isEqualToComparingFieldByField(testMarketOrderItems);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketOrderItems() throws Exception {
        int databaseSizeBeforeUpdate = marketOrderItemsRepository.findAll().size();

        // Create the MarketOrderItems
        MarketOrderItemsDTO marketOrderItemsDTO = marketOrderItemsMapper.toDto(marketOrderItems);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketOrderItemsMockMvc.perform(put("/api/market-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketOrderItems in the database
        List<MarketOrderItems> marketOrderItemsList = marketOrderItemsRepository.findAll();
        assertThat(marketOrderItemsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketOrderItems() throws Exception {
        // Initialize the database
        marketOrderItemsRepository.saveAndFlush(marketOrderItems);
        marketOrderItemsSearchRepository.save(marketOrderItems);
        int databaseSizeBeforeDelete = marketOrderItemsRepository.findAll().size();

        // Get the marketOrderItems
        restMarketOrderItemsMockMvc.perform(delete("/api/market-order-items/{id}", marketOrderItems.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean marketOrderItemsExistsInEs = marketOrderItemsSearchRepository.exists(marketOrderItems.getId());
        assertThat(marketOrderItemsExistsInEs).isFalse();

        // Validate the database is empty
        List<MarketOrderItems> marketOrderItemsList = marketOrderItemsRepository.findAll();
        assertThat(marketOrderItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMarketOrderItems() throws Exception {
        // Initialize the database
        marketOrderItemsRepository.saveAndFlush(marketOrderItems);
        marketOrderItemsSearchRepository.save(marketOrderItems);

        // Search the marketOrderItems
        restMarketOrderItemsMockMvc.perform(get("/api/_search/market-order-items?query=id:" + marketOrderItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketOrderItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketOrderItems.class);
        MarketOrderItems marketOrderItems1 = new MarketOrderItems();
        marketOrderItems1.setId(1L);
        MarketOrderItems marketOrderItems2 = new MarketOrderItems();
        marketOrderItems2.setId(marketOrderItems1.getId());
        assertThat(marketOrderItems1).isEqualTo(marketOrderItems2);
        marketOrderItems2.setId(2L);
        assertThat(marketOrderItems1).isNotEqualTo(marketOrderItems2);
        marketOrderItems1.setId(null);
        assertThat(marketOrderItems1).isNotEqualTo(marketOrderItems2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketOrderItemsDTO.class);
        MarketOrderItemsDTO marketOrderItemsDTO1 = new MarketOrderItemsDTO();
        marketOrderItemsDTO1.setId(1L);
        MarketOrderItemsDTO marketOrderItemsDTO2 = new MarketOrderItemsDTO();
        assertThat(marketOrderItemsDTO1).isNotEqualTo(marketOrderItemsDTO2);
        marketOrderItemsDTO2.setId(marketOrderItemsDTO1.getId());
        assertThat(marketOrderItemsDTO1).isEqualTo(marketOrderItemsDTO2);
        marketOrderItemsDTO2.setId(2L);
        assertThat(marketOrderItemsDTO1).isNotEqualTo(marketOrderItemsDTO2);
        marketOrderItemsDTO1.setId(null);
        assertThat(marketOrderItemsDTO1).isNotEqualTo(marketOrderItemsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marketOrderItemsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marketOrderItemsMapper.fromId(null)).isNull();
    }
}

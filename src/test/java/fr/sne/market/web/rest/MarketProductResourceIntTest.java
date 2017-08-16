package fr.sne.market.web.rest;

import fr.sne.market.MarketApp;

import fr.sne.market.domain.MarketProduct;
import fr.sne.market.repository.MarketProductRepository;
import fr.sne.market.repository.search.MarketProductSearchRepository;
import fr.sne.market.service.dto.MarketProductDTO;
import fr.sne.market.service.mapper.MarketProductMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MarketProductResource REST controller.
 *
 * @see MarketProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketApp.class)
public class MarketProductResourceIntTest {

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

    @Autowired
    private MarketProductRepository marketProductRepository;

    @Autowired
    private MarketProductMapper marketProductMapper;

    @Autowired
    private MarketProductSearchRepository marketProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketProductMockMvc;

    private MarketProduct marketProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketProductResource marketProductResource = new MarketProductResource(marketProductRepository, marketProductMapper, marketProductSearchRepository);
        this.restMarketProductMockMvc = MockMvcBuilders.standaloneSetup(marketProductResource)
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
    public static MarketProduct createEntity(EntityManager em) {
        MarketProduct marketProduct = new MarketProduct()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE);
        return marketProduct;
    }

    @Before
    public void initTest() {
        marketProductSearchRepository.deleteAll();
        marketProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketProduct() throws Exception {
        int databaseSizeBeforeCreate = marketProductRepository.findAll().size();

        // Create the MarketProduct
        MarketProductDTO marketProductDTO = marketProductMapper.toDto(marketProduct);
        restMarketProductMockMvc.perform(post("/api/market-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketProduct in the database
        List<MarketProduct> marketProductList = marketProductRepository.findAll();
        assertThat(marketProductList).hasSize(databaseSizeBeforeCreate + 1);
        MarketProduct testMarketProduct = marketProductList.get(marketProductList.size() - 1);
        assertThat(testMarketProduct.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMarketProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarketProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMarketProduct.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testMarketProduct.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);

        // Validate the MarketProduct in Elasticsearch
        MarketProduct marketProductEs = marketProductSearchRepository.findOne(testMarketProduct.getId());
        assertThat(marketProductEs).isEqualToComparingFieldByField(testMarketProduct);
    }

    @Test
    @Transactional
    public void createMarketProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketProductRepository.findAll().size();

        // Create the MarketProduct with an existing ID
        marketProduct.setId(1L);
        MarketProductDTO marketProductDTO = marketProductMapper.toDto(marketProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketProductMockMvc.perform(post("/api/market-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketProduct> marketProductList = marketProductRepository.findAll();
        assertThat(marketProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketProductRepository.findAll().size();
        // set the field null
        marketProduct.setTitle(null);

        // Create the MarketProduct, which fails.
        MarketProductDTO marketProductDTO = marketProductMapper.toDto(marketProduct);

        restMarketProductMockMvc.perform(post("/api/market-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductDTO)))
            .andExpect(status().isBadRequest());

        List<MarketProduct> marketProductList = marketProductRepository.findAll();
        assertThat(marketProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarketProducts() throws Exception {
        // Initialize the database
        marketProductRepository.saveAndFlush(marketProduct);

        // Get all the marketProductList
        restMarketProductMockMvc.perform(get("/api/market-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))));
    }

    @Test
    @Transactional
    public void getMarketProduct() throws Exception {
        // Initialize the database
        marketProductRepository.saveAndFlush(marketProduct);

        // Get the marketProduct
        restMarketProductMockMvc.perform(get("/api/market-products/{id}", marketProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketProduct.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)));
    }

    @Test
    @Transactional
    public void getNonExistingMarketProduct() throws Exception {
        // Get the marketProduct
        restMarketProductMockMvc.perform(get("/api/market-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketProduct() throws Exception {
        // Initialize the database
        marketProductRepository.saveAndFlush(marketProduct);
        marketProductSearchRepository.save(marketProduct);
        int databaseSizeBeforeUpdate = marketProductRepository.findAll().size();

        // Update the marketProduct
        MarketProduct updatedMarketProduct = marketProductRepository.findOne(marketProduct.getId());
        updatedMarketProduct
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        MarketProductDTO marketProductDTO = marketProductMapper.toDto(updatedMarketProduct);

        restMarketProductMockMvc.perform(put("/api/market-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductDTO)))
            .andExpect(status().isOk());

        // Validate the MarketProduct in the database
        List<MarketProduct> marketProductList = marketProductRepository.findAll();
        assertThat(marketProductList).hasSize(databaseSizeBeforeUpdate);
        MarketProduct testMarketProduct = marketProductList.get(marketProductList.size() - 1);
        assertThat(testMarketProduct.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMarketProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarketProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMarketProduct.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testMarketProduct.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);

        // Validate the MarketProduct in Elasticsearch
        MarketProduct marketProductEs = marketProductSearchRepository.findOne(testMarketProduct.getId());
        assertThat(marketProductEs).isEqualToComparingFieldByField(testMarketProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketProduct() throws Exception {
        int databaseSizeBeforeUpdate = marketProductRepository.findAll().size();

        // Create the MarketProduct
        MarketProductDTO marketProductDTO = marketProductMapper.toDto(marketProduct);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketProductMockMvc.perform(put("/api/market-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketProductDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketProduct in the database
        List<MarketProduct> marketProductList = marketProductRepository.findAll();
        assertThat(marketProductList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketProduct() throws Exception {
        // Initialize the database
        marketProductRepository.saveAndFlush(marketProduct);
        marketProductSearchRepository.save(marketProduct);
        int databaseSizeBeforeDelete = marketProductRepository.findAll().size();

        // Get the marketProduct
        restMarketProductMockMvc.perform(delete("/api/market-products/{id}", marketProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean marketProductExistsInEs = marketProductSearchRepository.exists(marketProduct.getId());
        assertThat(marketProductExistsInEs).isFalse();

        // Validate the database is empty
        List<MarketProduct> marketProductList = marketProductRepository.findAll();
        assertThat(marketProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMarketProduct() throws Exception {
        // Initialize the database
        marketProductRepository.saveAndFlush(marketProduct);
        marketProductSearchRepository.save(marketProduct);

        // Search the marketProduct
        restMarketProductMockMvc.perform(get("/api/_search/market-products?query=id:" + marketProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketProduct.class);
        MarketProduct marketProduct1 = new MarketProduct();
        marketProduct1.setId(1L);
        MarketProduct marketProduct2 = new MarketProduct();
        marketProduct2.setId(marketProduct1.getId());
        assertThat(marketProduct1).isEqualTo(marketProduct2);
        marketProduct2.setId(2L);
        assertThat(marketProduct1).isNotEqualTo(marketProduct2);
        marketProduct1.setId(null);
        assertThat(marketProduct1).isNotEqualTo(marketProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketProductDTO.class);
        MarketProductDTO marketProductDTO1 = new MarketProductDTO();
        marketProductDTO1.setId(1L);
        MarketProductDTO marketProductDTO2 = new MarketProductDTO();
        assertThat(marketProductDTO1).isNotEqualTo(marketProductDTO2);
        marketProductDTO2.setId(marketProductDTO1.getId());
        assertThat(marketProductDTO1).isEqualTo(marketProductDTO2);
        marketProductDTO2.setId(2L);
        assertThat(marketProductDTO1).isNotEqualTo(marketProductDTO2);
        marketProductDTO1.setId(null);
        assertThat(marketProductDTO1).isNotEqualTo(marketProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marketProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marketProductMapper.fromId(null)).isNull();
    }
}

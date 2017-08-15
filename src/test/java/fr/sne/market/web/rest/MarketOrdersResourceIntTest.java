package fr.sne.market.web.rest;

import fr.sne.market.MarketApp;

import fr.sne.market.domain.MarketOrders;
import fr.sne.market.repository.MarketOrdersRepository;
import fr.sne.market.repository.search.MarketOrdersSearchRepository;
import fr.sne.market.service.dto.MarketOrdersDTO;
import fr.sne.market.service.mapper.MarketOrdersMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static fr.sne.market.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.sne.market.domain.enumeration.OrderStatus;
/**
 * Test class for the MarketOrdersResource REST controller.
 *
 * @see MarketOrdersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketApp.class)
public class MarketOrdersResourceIntTest {

    private static final ZonedDateTime DEFAULT_ORDER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ORDER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_SHIPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHIPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);

    private static final String DEFAULT_DISCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_WEIGHT = new BigDecimal(2);

    private static final String DEFAULT_TRACKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_NUMBER = "BBBBBBBBBB";

    private static final OrderStatus DEFAULT_ORDERTATUS = OrderStatus.PAID;
    private static final OrderStatus UPDATED_ORDERTATUS = OrderStatus.VALIDATED;

    private static final String DEFAULT_REMOTE_VIRTUAL_CARD_ID = "AAAAAAAAAA";
    private static final String UPDATED_REMOTE_VIRTUAL_CARD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MARKET_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MARKET_ORDER_ID = "BBBBBBBBBB";

    @Autowired
    private MarketOrdersRepository marketOrdersRepository;

    @Autowired
    private MarketOrdersMapper marketOrdersMapper;

    @Autowired
    private MarketOrdersSearchRepository marketOrdersSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketOrdersMockMvc;

    private MarketOrders marketOrders;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketOrdersResource marketOrdersResource = new MarketOrdersResource(marketOrdersRepository, marketOrdersMapper, marketOrdersSearchRepository);
        this.restMarketOrdersMockMvc = MockMvcBuilders.standaloneSetup(marketOrdersResource)
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
    public static MarketOrders createEntity(EntityManager em) {
        MarketOrders marketOrders = new MarketOrders()
            .orderDate(DEFAULT_ORDER_DATE)
            .shipdate(DEFAULT_SHIPDATE)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .weight(DEFAULT_WEIGHT)
            .trackingNumber(DEFAULT_TRACKING_NUMBER)
            .ordertatus(DEFAULT_ORDERTATUS)
            .remoteVirtualCardId(DEFAULT_REMOTE_VIRTUAL_CARD_ID)
            .marketOrderId(DEFAULT_MARKET_ORDER_ID);
        return marketOrders;
    }

    @Before
    public void initTest() {
        marketOrdersSearchRepository.deleteAll();
        marketOrders = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketOrders() throws Exception {
        int databaseSizeBeforeCreate = marketOrdersRepository.findAll().size();

        // Create the MarketOrders
        MarketOrdersDTO marketOrdersDTO = marketOrdersMapper.toDto(marketOrders);
        restMarketOrdersMockMvc.perform(post("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrdersDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketOrders in the database
        List<MarketOrders> marketOrdersList = marketOrdersRepository.findAll();
        assertThat(marketOrdersList).hasSize(databaseSizeBeforeCreate + 1);
        MarketOrders testMarketOrders = marketOrdersList.get(marketOrdersList.size() - 1);
        assertThat(testMarketOrders.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testMarketOrders.getShipdate()).isEqualTo(DEFAULT_SHIPDATE);
        assertThat(testMarketOrders.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testMarketOrders.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testMarketOrders.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testMarketOrders.getTrackingNumber()).isEqualTo(DEFAULT_TRACKING_NUMBER);
        assertThat(testMarketOrders.getOrdertatus()).isEqualTo(DEFAULT_ORDERTATUS);
        assertThat(testMarketOrders.getRemoteVirtualCardId()).isEqualTo(DEFAULT_REMOTE_VIRTUAL_CARD_ID);
        assertThat(testMarketOrders.getMarketOrderId()).isEqualTo(DEFAULT_MARKET_ORDER_ID);

        // Validate the MarketOrders in Elasticsearch
        MarketOrders marketOrdersEs = marketOrdersSearchRepository.findOne(testMarketOrders.getId());
        assertThat(marketOrdersEs).isEqualToComparingFieldByField(testMarketOrders);
    }

    @Test
    @Transactional
    public void createMarketOrdersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketOrdersRepository.findAll().size();

        // Create the MarketOrders with an existing ID
        marketOrders.setId(1L);
        MarketOrdersDTO marketOrdersDTO = marketOrdersMapper.toDto(marketOrders);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketOrdersMockMvc.perform(post("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrdersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketOrders> marketOrdersList = marketOrdersRepository.findAll();
        assertThat(marketOrdersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShipdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketOrdersRepository.findAll().size();
        // set the field null
        marketOrders.setShipdate(null);

        // Create the MarketOrders, which fails.
        MarketOrdersDTO marketOrdersDTO = marketOrdersMapper.toDto(marketOrders);

        restMarketOrdersMockMvc.perform(post("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrdersDTO)))
            .andExpect(status().isBadRequest());

        List<MarketOrders> marketOrdersList = marketOrdersRepository.findAll();
        assertThat(marketOrdersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrdertatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketOrdersRepository.findAll().size();
        // set the field null
        marketOrders.setOrdertatus(null);

        // Create the MarketOrders, which fails.
        MarketOrdersDTO marketOrdersDTO = marketOrdersMapper.toDto(marketOrders);

        restMarketOrdersMockMvc.perform(post("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrdersDTO)))
            .andExpect(status().isBadRequest());

        List<MarketOrders> marketOrdersList = marketOrdersRepository.findAll();
        assertThat(marketOrdersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarketOrders() throws Exception {
        // Initialize the database
        marketOrdersRepository.saveAndFlush(marketOrders);

        // Get all the marketOrdersList
        restMarketOrdersMockMvc.perform(get("/api/market-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketOrders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(sameInstant(DEFAULT_ORDER_DATE))))
            .andExpect(jsonPath("$.[*].shipdate").value(hasItem(DEFAULT_SHIPDATE.toString())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].trackingNumber").value(hasItem(DEFAULT_TRACKING_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].ordertatus").value(hasItem(DEFAULT_ORDERTATUS.toString())))
            .andExpect(jsonPath("$.[*].remoteVirtualCardId").value(hasItem(DEFAULT_REMOTE_VIRTUAL_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].marketOrderId").value(hasItem(DEFAULT_MARKET_ORDER_ID.toString())));
    }

    @Test
    @Transactional
    public void getMarketOrders() throws Exception {
        // Initialize the database
        marketOrdersRepository.saveAndFlush(marketOrders);

        // Get the marketOrders
        restMarketOrdersMockMvc.perform(get("/api/market-orders/{id}", marketOrders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketOrders.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(sameInstant(DEFAULT_ORDER_DATE)))
            .andExpect(jsonPath("$.shipdate").value(DEFAULT_SHIPDATE.toString()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.trackingNumber").value(DEFAULT_TRACKING_NUMBER.toString()))
            .andExpect(jsonPath("$.ordertatus").value(DEFAULT_ORDERTATUS.toString()))
            .andExpect(jsonPath("$.remoteVirtualCardId").value(DEFAULT_REMOTE_VIRTUAL_CARD_ID.toString()))
            .andExpect(jsonPath("$.marketOrderId").value(DEFAULT_MARKET_ORDER_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketOrders() throws Exception {
        // Get the marketOrders
        restMarketOrdersMockMvc.perform(get("/api/market-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketOrders() throws Exception {
        // Initialize the database
        marketOrdersRepository.saveAndFlush(marketOrders);
        marketOrdersSearchRepository.save(marketOrders);
        int databaseSizeBeforeUpdate = marketOrdersRepository.findAll().size();

        // Update the marketOrders
        MarketOrders updatedMarketOrders = marketOrdersRepository.findOne(marketOrders.getId());
        updatedMarketOrders
            .orderDate(UPDATED_ORDER_DATE)
            .shipdate(UPDATED_SHIPDATE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .discount(UPDATED_DISCOUNT)
            .weight(UPDATED_WEIGHT)
            .trackingNumber(UPDATED_TRACKING_NUMBER)
            .ordertatus(UPDATED_ORDERTATUS)
            .remoteVirtualCardId(UPDATED_REMOTE_VIRTUAL_CARD_ID)
            .marketOrderId(UPDATED_MARKET_ORDER_ID);
        MarketOrdersDTO marketOrdersDTO = marketOrdersMapper.toDto(updatedMarketOrders);

        restMarketOrdersMockMvc.perform(put("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrdersDTO)))
            .andExpect(status().isOk());

        // Validate the MarketOrders in the database
        List<MarketOrders> marketOrdersList = marketOrdersRepository.findAll();
        assertThat(marketOrdersList).hasSize(databaseSizeBeforeUpdate);
        MarketOrders testMarketOrders = marketOrdersList.get(marketOrdersList.size() - 1);
        assertThat(testMarketOrders.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testMarketOrders.getShipdate()).isEqualTo(UPDATED_SHIPDATE);
        assertThat(testMarketOrders.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testMarketOrders.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testMarketOrders.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testMarketOrders.getTrackingNumber()).isEqualTo(UPDATED_TRACKING_NUMBER);
        assertThat(testMarketOrders.getOrdertatus()).isEqualTo(UPDATED_ORDERTATUS);
        assertThat(testMarketOrders.getRemoteVirtualCardId()).isEqualTo(UPDATED_REMOTE_VIRTUAL_CARD_ID);
        assertThat(testMarketOrders.getMarketOrderId()).isEqualTo(UPDATED_MARKET_ORDER_ID);

        // Validate the MarketOrders in Elasticsearch
        MarketOrders marketOrdersEs = marketOrdersSearchRepository.findOne(testMarketOrders.getId());
        assertThat(marketOrdersEs).isEqualToComparingFieldByField(testMarketOrders);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketOrders() throws Exception {
        int databaseSizeBeforeUpdate = marketOrdersRepository.findAll().size();

        // Create the MarketOrders
        MarketOrdersDTO marketOrdersDTO = marketOrdersMapper.toDto(marketOrders);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketOrdersMockMvc.perform(put("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrdersDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketOrders in the database
        List<MarketOrders> marketOrdersList = marketOrdersRepository.findAll();
        assertThat(marketOrdersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketOrders() throws Exception {
        // Initialize the database
        marketOrdersRepository.saveAndFlush(marketOrders);
        marketOrdersSearchRepository.save(marketOrders);
        int databaseSizeBeforeDelete = marketOrdersRepository.findAll().size();

        // Get the marketOrders
        restMarketOrdersMockMvc.perform(delete("/api/market-orders/{id}", marketOrders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean marketOrdersExistsInEs = marketOrdersSearchRepository.exists(marketOrders.getId());
        assertThat(marketOrdersExistsInEs).isFalse();

        // Validate the database is empty
        List<MarketOrders> marketOrdersList = marketOrdersRepository.findAll();
        assertThat(marketOrdersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMarketOrders() throws Exception {
        // Initialize the database
        marketOrdersRepository.saveAndFlush(marketOrders);
        marketOrdersSearchRepository.save(marketOrders);

        // Search the marketOrders
        restMarketOrdersMockMvc.perform(get("/api/_search/market-orders?query=id:" + marketOrders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketOrders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(sameInstant(DEFAULT_ORDER_DATE))))
            .andExpect(jsonPath("$.[*].shipdate").value(hasItem(DEFAULT_SHIPDATE.toString())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].trackingNumber").value(hasItem(DEFAULT_TRACKING_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].ordertatus").value(hasItem(DEFAULT_ORDERTATUS.toString())))
            .andExpect(jsonPath("$.[*].remoteVirtualCardId").value(hasItem(DEFAULT_REMOTE_VIRTUAL_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].marketOrderId").value(hasItem(DEFAULT_MARKET_ORDER_ID.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketOrders.class);
        MarketOrders marketOrders1 = new MarketOrders();
        marketOrders1.setId(1L);
        MarketOrders marketOrders2 = new MarketOrders();
        marketOrders2.setId(marketOrders1.getId());
        assertThat(marketOrders1).isEqualTo(marketOrders2);
        marketOrders2.setId(2L);
        assertThat(marketOrders1).isNotEqualTo(marketOrders2);
        marketOrders1.setId(null);
        assertThat(marketOrders1).isNotEqualTo(marketOrders2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketOrdersDTO.class);
        MarketOrdersDTO marketOrdersDTO1 = new MarketOrdersDTO();
        marketOrdersDTO1.setId(1L);
        MarketOrdersDTO marketOrdersDTO2 = new MarketOrdersDTO();
        assertThat(marketOrdersDTO1).isNotEqualTo(marketOrdersDTO2);
        marketOrdersDTO2.setId(marketOrdersDTO1.getId());
        assertThat(marketOrdersDTO1).isEqualTo(marketOrdersDTO2);
        marketOrdersDTO2.setId(2L);
        assertThat(marketOrdersDTO1).isNotEqualTo(marketOrdersDTO2);
        marketOrdersDTO1.setId(null);
        assertThat(marketOrdersDTO1).isNotEqualTo(marketOrdersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marketOrdersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marketOrdersMapper.fromId(null)).isNull();
    }
}

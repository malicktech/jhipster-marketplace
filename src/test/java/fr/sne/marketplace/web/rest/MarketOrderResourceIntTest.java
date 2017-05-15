package fr.sne.marketplace.web.rest;

import fr.sne.marketplace.MarketplacejhipsterApp;

import fr.sne.marketplace.domain.MarketOrder;
import fr.sne.marketplace.repository.MarketOrderRepository;
import fr.sne.marketplace.repository.search.MarketOrderSearchRepository;
import fr.sne.marketplace.service.dto.MarketOrderDTO;
import fr.sne.marketplace.service.mapper.MarketOrderMapper;
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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static fr.sne.marketplace.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.sne.marketplace.domain.enumeration.Status;
/**
 * Test class for the MarketOrderResource REST controller.
 *
 * @see MarketOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketplacejhipsterApp.class)
public class MarketOrderResourceIntTest {

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Status DEFAULT_STATUS = Status.PAYED;
    private static final Status UPDATED_STATUS = Status.VALIDATED;

    private static final String DEFAULT_REMOTE_VIRTUAL_CARD_ID = "AAAAAAAAAA";
    private static final String UPDATED_REMOTE_VIRTUAL_CARD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MARKET_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MARKET_ORDER_ID = "BBBBBBBBBB";

    @Autowired
    private MarketOrderRepository marketOrderRepository;

    @Autowired
    private MarketOrderMapper marketOrderMapper;

    @Autowired
    private MarketOrderSearchRepository marketOrderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketOrderMockMvc;

    private MarketOrder marketOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketOrderResource marketOrderResource = new MarketOrderResource(marketOrderRepository, marketOrderMapper, marketOrderSearchRepository);
        this.restMarketOrderMockMvc = MockMvcBuilders.standaloneSetup(marketOrderResource)
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
    public static MarketOrder createEntity(EntityManager em) {
        MarketOrder marketOrder = new MarketOrder()
            .orderDate(DEFAULT_ORDER_DATE)
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS)
            .remoteVirtualCardId(DEFAULT_REMOTE_VIRTUAL_CARD_ID)
            .marketOrderId(DEFAULT_MARKET_ORDER_ID);
        return marketOrder;
    }

    @Before
    public void initTest() {
        marketOrderSearchRepository.deleteAll();
        marketOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketOrder() throws Exception {
        int databaseSizeBeforeCreate = marketOrderRepository.findAll().size();

        // Create the MarketOrder
        MarketOrderDTO marketOrderDTO = marketOrderMapper.toDto(marketOrder);
        restMarketOrderMockMvc.perform(post("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketOrder in the database
        List<MarketOrder> marketOrderList = marketOrderRepository.findAll();
        assertThat(marketOrderList).hasSize(databaseSizeBeforeCreate + 1);
        MarketOrder testMarketOrder = marketOrderList.get(marketOrderList.size() - 1);
        assertThat(testMarketOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testMarketOrder.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMarketOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMarketOrder.getRemoteVirtualCardId()).isEqualTo(DEFAULT_REMOTE_VIRTUAL_CARD_ID);
        assertThat(testMarketOrder.getMarketOrderId()).isEqualTo(DEFAULT_MARKET_ORDER_ID);

        // Validate the MarketOrder in Elasticsearch
        MarketOrder marketOrderEs = marketOrderSearchRepository.findOne(testMarketOrder.getId());
        assertThat(marketOrderEs).isEqualToComparingFieldByField(testMarketOrder);
    }

    @Test
    @Transactional
    public void createMarketOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketOrderRepository.findAll().size();

        // Create the MarketOrder with an existing ID
        marketOrder.setId(1L);
        MarketOrderDTO marketOrderDTO = marketOrderMapper.toDto(marketOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketOrderMockMvc.perform(post("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketOrder> marketOrderList = marketOrderRepository.findAll();
        assertThat(marketOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketOrderRepository.findAll().size();
        // set the field null
        marketOrder.setDate(null);

        // Create the MarketOrder, which fails.
        MarketOrderDTO marketOrderDTO = marketOrderMapper.toDto(marketOrder);

        restMarketOrderMockMvc.perform(post("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderDTO)))
            .andExpect(status().isBadRequest());

        List<MarketOrder> marketOrderList = marketOrderRepository.findAll();
        assertThat(marketOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = marketOrderRepository.findAll().size();
        // set the field null
        marketOrder.setStatus(null);

        // Create the MarketOrder, which fails.
        MarketOrderDTO marketOrderDTO = marketOrderMapper.toDto(marketOrder);

        restMarketOrderMockMvc.perform(post("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderDTO)))
            .andExpect(status().isBadRequest());

        List<MarketOrder> marketOrderList = marketOrderRepository.findAll();
        assertThat(marketOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarketOrders() throws Exception {
        // Initialize the database
        marketOrderRepository.saveAndFlush(marketOrder);

        // Get all the marketOrderList
        restMarketOrderMockMvc.perform(get("/api/market-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remoteVirtualCardId").value(hasItem(DEFAULT_REMOTE_VIRTUAL_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].marketOrderId").value(hasItem(DEFAULT_MARKET_ORDER_ID.toString())));
    }

    @Test
    @Transactional
    public void getMarketOrder() throws Exception {
        // Initialize the database
        marketOrderRepository.saveAndFlush(marketOrder);

        // Get the marketOrder
        restMarketOrderMockMvc.perform(get("/api/market-orders/{id}", marketOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.remoteVirtualCardId").value(DEFAULT_REMOTE_VIRTUAL_CARD_ID.toString()))
            .andExpect(jsonPath("$.marketOrderId").value(DEFAULT_MARKET_ORDER_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketOrder() throws Exception {
        // Get the marketOrder
        restMarketOrderMockMvc.perform(get("/api/market-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketOrder() throws Exception {
        // Initialize the database
        marketOrderRepository.saveAndFlush(marketOrder);
        marketOrderSearchRepository.save(marketOrder);
        int databaseSizeBeforeUpdate = marketOrderRepository.findAll().size();

        // Update the marketOrder
        MarketOrder updatedMarketOrder = marketOrderRepository.findOne(marketOrder.getId());
        updatedMarketOrder
            .orderDate(UPDATED_ORDER_DATE)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS)
            .remoteVirtualCardId(UPDATED_REMOTE_VIRTUAL_CARD_ID)
            .marketOrderId(UPDATED_MARKET_ORDER_ID);
        MarketOrderDTO marketOrderDTO = marketOrderMapper.toDto(updatedMarketOrder);

        restMarketOrderMockMvc.perform(put("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderDTO)))
            .andExpect(status().isOk());

        // Validate the MarketOrder in the database
        List<MarketOrder> marketOrderList = marketOrderRepository.findAll();
        assertThat(marketOrderList).hasSize(databaseSizeBeforeUpdate);
        MarketOrder testMarketOrder = marketOrderList.get(marketOrderList.size() - 1);
        assertThat(testMarketOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testMarketOrder.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMarketOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMarketOrder.getRemoteVirtualCardId()).isEqualTo(UPDATED_REMOTE_VIRTUAL_CARD_ID);
        assertThat(testMarketOrder.getMarketOrderId()).isEqualTo(UPDATED_MARKET_ORDER_ID);

        // Validate the MarketOrder in Elasticsearch
        MarketOrder marketOrderEs = marketOrderSearchRepository.findOne(testMarketOrder.getId());
        assertThat(marketOrderEs).isEqualToComparingFieldByField(testMarketOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketOrder() throws Exception {
        int databaseSizeBeforeUpdate = marketOrderRepository.findAll().size();

        // Create the MarketOrder
        MarketOrderDTO marketOrderDTO = marketOrderMapper.toDto(marketOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketOrderMockMvc.perform(put("/api/market-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketOrder in the database
        List<MarketOrder> marketOrderList = marketOrderRepository.findAll();
        assertThat(marketOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketOrder() throws Exception {
        // Initialize the database
        marketOrderRepository.saveAndFlush(marketOrder);
        marketOrderSearchRepository.save(marketOrder);
        int databaseSizeBeforeDelete = marketOrderRepository.findAll().size();

        // Get the marketOrder
        restMarketOrderMockMvc.perform(delete("/api/market-orders/{id}", marketOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean marketOrderExistsInEs = marketOrderSearchRepository.exists(marketOrder.getId());
        assertThat(marketOrderExistsInEs).isFalse();

        // Validate the database is empty
        List<MarketOrder> marketOrderList = marketOrderRepository.findAll();
        assertThat(marketOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMarketOrder() throws Exception {
        // Initialize the database
        marketOrderRepository.saveAndFlush(marketOrder);
        marketOrderSearchRepository.save(marketOrder);

        // Search the marketOrder
        restMarketOrderMockMvc.perform(get("/api/_search/market-orders?query=id:" + marketOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remoteVirtualCardId").value(hasItem(DEFAULT_REMOTE_VIRTUAL_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].marketOrderId").value(hasItem(DEFAULT_MARKET_ORDER_ID.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketOrder.class);
        MarketOrder marketOrder1 = new MarketOrder();
        marketOrder1.setId(1L);
        MarketOrder marketOrder2 = new MarketOrder();
        marketOrder2.setId(marketOrder1.getId());
        assertThat(marketOrder1).isEqualTo(marketOrder2);
        marketOrder2.setId(2L);
        assertThat(marketOrder1).isNotEqualTo(marketOrder2);
        marketOrder1.setId(null);
        assertThat(marketOrder1).isNotEqualTo(marketOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketOrderDTO.class);
        MarketOrderDTO marketOrderDTO1 = new MarketOrderDTO();
        marketOrderDTO1.setId(1L);
        MarketOrderDTO marketOrderDTO2 = new MarketOrderDTO();
        assertThat(marketOrderDTO1).isNotEqualTo(marketOrderDTO2);
        marketOrderDTO2.setId(marketOrderDTO1.getId());
        assertThat(marketOrderDTO1).isEqualTo(marketOrderDTO2);
        marketOrderDTO2.setId(2L);
        assertThat(marketOrderDTO1).isNotEqualTo(marketOrderDTO2);
        marketOrderDTO1.setId(null);
        assertThat(marketOrderDTO1).isNotEqualTo(marketOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marketOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marketOrderMapper.fromId(null)).isNull();
    }
}

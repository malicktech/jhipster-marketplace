package fr.sne.market.web.rest;

import fr.sne.market.MarketApp;

import fr.sne.market.domain.Shipments;
import fr.sne.market.repository.ShipmentsRepository;
import fr.sne.market.repository.search.ShipmentsSearchRepository;
import fr.sne.market.service.dto.ShipmentsDTO;
import fr.sne.market.service.mapper.ShipmentsMapper;
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
 * Test class for the ShipmentsResource REST controller.
 *
 * @see ShipmentsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketApp.class)
public class ShipmentsResourceIntTest {

    private static final String DEFAULT_SHIPPER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private ShipmentsRepository shipmentsRepository;

    @Autowired
    private ShipmentsMapper shipmentsMapper;

    @Autowired
    private ShipmentsSearchRepository shipmentsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShipmentsMockMvc;

    private Shipments shipments;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentsResource shipmentsResource = new ShipmentsResource(shipmentsRepository, shipmentsMapper, shipmentsSearchRepository);
        this.restShipmentsMockMvc = MockMvcBuilders.standaloneSetup(shipmentsResource)
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
    public static Shipments createEntity(EntityManager em) {
        Shipments shipments = new Shipments()
            .shipperType(DEFAULT_SHIPPER_TYPE)
            .companyName(DEFAULT_COMPANY_NAME)
            .phone(DEFAULT_PHONE);
        return shipments;
    }

    @Before
    public void initTest() {
        shipmentsSearchRepository.deleteAll();
        shipments = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipments() throws Exception {
        int databaseSizeBeforeCreate = shipmentsRepository.findAll().size();

        // Create the Shipments
        ShipmentsDTO shipmentsDTO = shipmentsMapper.toDto(shipments);
        restShipmentsMockMvc.perform(post("/api/shipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Shipments in the database
        List<Shipments> shipmentsList = shipmentsRepository.findAll();
        assertThat(shipmentsList).hasSize(databaseSizeBeforeCreate + 1);
        Shipments testShipments = shipmentsList.get(shipmentsList.size() - 1);
        assertThat(testShipments.getShipperType()).isEqualTo(DEFAULT_SHIPPER_TYPE);
        assertThat(testShipments.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testShipments.getPhone()).isEqualTo(DEFAULT_PHONE);

        // Validate the Shipments in Elasticsearch
        Shipments shipmentsEs = shipmentsSearchRepository.findOne(testShipments.getId());
        assertThat(shipmentsEs).isEqualToComparingFieldByField(testShipments);
    }

    @Test
    @Transactional
    public void createShipmentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentsRepository.findAll().size();

        // Create the Shipments with an existing ID
        shipments.setId(1L);
        ShipmentsDTO shipmentsDTO = shipmentsMapper.toDto(shipments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentsMockMvc.perform(post("/api/shipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Shipments> shipmentsList = shipmentsRepository.findAll();
        assertThat(shipmentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShipments() throws Exception {
        // Initialize the database
        shipmentsRepository.saveAndFlush(shipments);

        // Get all the shipmentsList
        restShipmentsMockMvc.perform(get("/api/shipments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipments.getId().intValue())))
            .andExpect(jsonPath("$.[*].shipperType").value(hasItem(DEFAULT_SHIPPER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getShipments() throws Exception {
        // Initialize the database
        shipmentsRepository.saveAndFlush(shipments);

        // Get the shipments
        restShipmentsMockMvc.perform(get("/api/shipments/{id}", shipments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipments.getId().intValue()))
            .andExpect(jsonPath("$.shipperType").value(DEFAULT_SHIPPER_TYPE.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipments() throws Exception {
        // Get the shipments
        restShipmentsMockMvc.perform(get("/api/shipments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipments() throws Exception {
        // Initialize the database
        shipmentsRepository.saveAndFlush(shipments);
        shipmentsSearchRepository.save(shipments);
        int databaseSizeBeforeUpdate = shipmentsRepository.findAll().size();

        // Update the shipments
        Shipments updatedShipments = shipmentsRepository.findOne(shipments.getId());
        updatedShipments
            .shipperType(UPDATED_SHIPPER_TYPE)
            .companyName(UPDATED_COMPANY_NAME)
            .phone(UPDATED_PHONE);
        ShipmentsDTO shipmentsDTO = shipmentsMapper.toDto(updatedShipments);

        restShipmentsMockMvc.perform(put("/api/shipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentsDTO)))
            .andExpect(status().isOk());

        // Validate the Shipments in the database
        List<Shipments> shipmentsList = shipmentsRepository.findAll();
        assertThat(shipmentsList).hasSize(databaseSizeBeforeUpdate);
        Shipments testShipments = shipmentsList.get(shipmentsList.size() - 1);
        assertThat(testShipments.getShipperType()).isEqualTo(UPDATED_SHIPPER_TYPE);
        assertThat(testShipments.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testShipments.getPhone()).isEqualTo(UPDATED_PHONE);

        // Validate the Shipments in Elasticsearch
        Shipments shipmentsEs = shipmentsSearchRepository.findOne(testShipments.getId());
        assertThat(shipmentsEs).isEqualToComparingFieldByField(testShipments);
    }

    @Test
    @Transactional
    public void updateNonExistingShipments() throws Exception {
        int databaseSizeBeforeUpdate = shipmentsRepository.findAll().size();

        // Create the Shipments
        ShipmentsDTO shipmentsDTO = shipmentsMapper.toDto(shipments);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShipmentsMockMvc.perform(put("/api/shipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Shipments in the database
        List<Shipments> shipmentsList = shipmentsRepository.findAll();
        assertThat(shipmentsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShipments() throws Exception {
        // Initialize the database
        shipmentsRepository.saveAndFlush(shipments);
        shipmentsSearchRepository.save(shipments);
        int databaseSizeBeforeDelete = shipmentsRepository.findAll().size();

        // Get the shipments
        restShipmentsMockMvc.perform(delete("/api/shipments/{id}", shipments.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean shipmentsExistsInEs = shipmentsSearchRepository.exists(shipments.getId());
        assertThat(shipmentsExistsInEs).isFalse();

        // Validate the database is empty
        List<Shipments> shipmentsList = shipmentsRepository.findAll();
        assertThat(shipmentsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchShipments() throws Exception {
        // Initialize the database
        shipmentsRepository.saveAndFlush(shipments);
        shipmentsSearchRepository.save(shipments);

        // Search the shipments
        restShipmentsMockMvc.perform(get("/api/_search/shipments?query=id:" + shipments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipments.getId().intValue())))
            .andExpect(jsonPath("$.[*].shipperType").value(hasItem(DEFAULT_SHIPPER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shipments.class);
        Shipments shipments1 = new Shipments();
        shipments1.setId(1L);
        Shipments shipments2 = new Shipments();
        shipments2.setId(shipments1.getId());
        assertThat(shipments1).isEqualTo(shipments2);
        shipments2.setId(2L);
        assertThat(shipments1).isNotEqualTo(shipments2);
        shipments1.setId(null);
        assertThat(shipments1).isNotEqualTo(shipments2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentsDTO.class);
        ShipmentsDTO shipmentsDTO1 = new ShipmentsDTO();
        shipmentsDTO1.setId(1L);
        ShipmentsDTO shipmentsDTO2 = new ShipmentsDTO();
        assertThat(shipmentsDTO1).isNotEqualTo(shipmentsDTO2);
        shipmentsDTO2.setId(shipmentsDTO1.getId());
        assertThat(shipmentsDTO1).isEqualTo(shipmentsDTO2);
        shipmentsDTO2.setId(2L);
        assertThat(shipmentsDTO1).isNotEqualTo(shipmentsDTO2);
        shipmentsDTO1.setId(null);
        assertThat(shipmentsDTO1).isNotEqualTo(shipmentsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipmentsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipmentsMapper.fromId(null)).isNull();
    }
}

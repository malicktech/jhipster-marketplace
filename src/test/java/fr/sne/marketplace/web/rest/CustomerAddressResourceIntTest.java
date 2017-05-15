package fr.sne.marketplace.web.rest;

import fr.sne.marketplace.MarketplacejhipsterApp;

import fr.sne.marketplace.domain.CustomerAddress;
import fr.sne.marketplace.repository.CustomerAddressRepository;
import fr.sne.marketplace.repository.search.CustomerAddressSearchRepository;
import fr.sne.marketplace.service.dto.CustomerAddressDTO;
import fr.sne.marketplace.service.mapper.CustomerAddressMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerAddressResource REST controller.
 *
 * @see CustomerAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketplacejhipsterApp.class)
public class CustomerAddressResourceIntTest {

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private CustomerAddressMapper customerAddressMapper;

    @Autowired
    private CustomerAddressSearchRepository customerAddressSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerAddressMockMvc;

    private CustomerAddress customerAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerAddressResource customerAddressResource = new CustomerAddressResource(customerAddressRepository, customerAddressMapper, customerAddressSearchRepository);
        this.restCustomerAddressMockMvc = MockMvcBuilders.standaloneSetup(customerAddressResource)
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
    public static CustomerAddress createEntity(EntityManager em) {
        CustomerAddress customerAddress = new CustomerAddress()
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .stateProvince(DEFAULT_STATE_PROVINCE);
        return customerAddress;
    }

    @Before
    public void initTest() {
        customerAddressSearchRepository.deleteAll();
        customerAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerAddress() throws Exception {
        int databaseSizeBeforeCreate = customerAddressRepository.findAll().size();

        // Create the CustomerAddress
        CustomerAddressDTO customerAddressDTO = customerAddressMapper.toDto(customerAddress);
        restCustomerAddressMockMvc.perform(post("/api/customer-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerAddress in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerAddress testCustomerAddress = customerAddressList.get(customerAddressList.size() - 1);
        assertThat(testCustomerAddress.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testCustomerAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustomerAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCustomerAddress.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);

        // Validate the CustomerAddress in Elasticsearch
        CustomerAddress customerAddressEs = customerAddressSearchRepository.findOne(testCustomerAddress.getId());
        assertThat(customerAddressEs).isEqualToComparingFieldByField(testCustomerAddress);
    }

    @Test
    @Transactional
    public void createCustomerAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerAddressRepository.findAll().size();

        // Create the CustomerAddress with an existing ID
        customerAddress.setId(1L);
        CustomerAddressDTO customerAddressDTO = customerAddressMapper.toDto(customerAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerAddressMockMvc.perform(post("/api/customer-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerAddressRepository.findAll().size();
        // set the field null
        customerAddress.setCity(null);

        // Create the CustomerAddress, which fails.
        CustomerAddressDTO customerAddressDTO = customerAddressMapper.toDto(customerAddress);

        restCustomerAddressMockMvc.perform(post("/api/customer-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerAddressRepository.findAll().size();
        // set the field null
        customerAddress.setPostalCode(null);

        // Create the CustomerAddress, which fails.
        CustomerAddressDTO customerAddressDTO = customerAddressMapper.toDto(customerAddress);

        restCustomerAddressMockMvc.perform(post("/api/customer-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerAddresses() throws Exception {
        // Initialize the database
        customerAddressRepository.saveAndFlush(customerAddress);

        // Get all the customerAddressList
        restCustomerAddressMockMvc.perform(get("/api/customer-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())));
    }

    @Test
    @Transactional
    public void getCustomerAddress() throws Exception {
        // Initialize the database
        customerAddressRepository.saveAndFlush(customerAddress);

        // Get the customerAddress
        restCustomerAddressMockMvc.perform(get("/api/customer-addresses/{id}", customerAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerAddress.getId().intValue()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerAddress() throws Exception {
        // Get the customerAddress
        restCustomerAddressMockMvc.perform(get("/api/customer-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerAddress() throws Exception {
        // Initialize the database
        customerAddressRepository.saveAndFlush(customerAddress);
        customerAddressSearchRepository.save(customerAddress);
        int databaseSizeBeforeUpdate = customerAddressRepository.findAll().size();

        // Update the customerAddress
        CustomerAddress updatedCustomerAddress = customerAddressRepository.findOne(customerAddress.getId());
        updatedCustomerAddress
            .streetAddress(UPDATED_STREET_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .stateProvince(UPDATED_STATE_PROVINCE);
        CustomerAddressDTO customerAddressDTO = customerAddressMapper.toDto(updatedCustomerAddress);

        restCustomerAddressMockMvc.perform(put("/api/customer-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAddressDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerAddress in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeUpdate);
        CustomerAddress testCustomerAddress = customerAddressList.get(customerAddressList.size() - 1);
        assertThat(testCustomerAddress.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testCustomerAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomerAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCustomerAddress.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);

        // Validate the CustomerAddress in Elasticsearch
        CustomerAddress customerAddressEs = customerAddressSearchRepository.findOne(testCustomerAddress.getId());
        assertThat(customerAddressEs).isEqualToComparingFieldByField(testCustomerAddress);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerAddress() throws Exception {
        int databaseSizeBeforeUpdate = customerAddressRepository.findAll().size();

        // Create the CustomerAddress
        CustomerAddressDTO customerAddressDTO = customerAddressMapper.toDto(customerAddress);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerAddressMockMvc.perform(put("/api/customer-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerAddress in the database
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerAddress() throws Exception {
        // Initialize the database
        customerAddressRepository.saveAndFlush(customerAddress);
        customerAddressSearchRepository.save(customerAddress);
        int databaseSizeBeforeDelete = customerAddressRepository.findAll().size();

        // Get the customerAddress
        restCustomerAddressMockMvc.perform(delete("/api/customer-addresses/{id}", customerAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean customerAddressExistsInEs = customerAddressSearchRepository.exists(customerAddress.getId());
        assertThat(customerAddressExistsInEs).isFalse();

        // Validate the database is empty
        List<CustomerAddress> customerAddressList = customerAddressRepository.findAll();
        assertThat(customerAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerAddress() throws Exception {
        // Initialize the database
        customerAddressRepository.saveAndFlush(customerAddress);
        customerAddressSearchRepository.save(customerAddress);

        // Search the customerAddress
        restCustomerAddressMockMvc.perform(get("/api/_search/customer-addresses?query=id:" + customerAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerAddress.class);
        CustomerAddress customerAddress1 = new CustomerAddress();
        customerAddress1.setId(1L);
        CustomerAddress customerAddress2 = new CustomerAddress();
        customerAddress2.setId(customerAddress1.getId());
        assertThat(customerAddress1).isEqualTo(customerAddress2);
        customerAddress2.setId(2L);
        assertThat(customerAddress1).isNotEqualTo(customerAddress2);
        customerAddress1.setId(null);
        assertThat(customerAddress1).isNotEqualTo(customerAddress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerAddressDTO.class);
        CustomerAddressDTO customerAddressDTO1 = new CustomerAddressDTO();
        customerAddressDTO1.setId(1L);
        CustomerAddressDTO customerAddressDTO2 = new CustomerAddressDTO();
        assertThat(customerAddressDTO1).isNotEqualTo(customerAddressDTO2);
        customerAddressDTO2.setId(customerAddressDTO1.getId());
        assertThat(customerAddressDTO1).isEqualTo(customerAddressDTO2);
        customerAddressDTO2.setId(2L);
        assertThat(customerAddressDTO1).isNotEqualTo(customerAddressDTO2);
        customerAddressDTO1.setId(null);
        assertThat(customerAddressDTO1).isNotEqualTo(customerAddressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerAddressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerAddressMapper.fromId(null)).isNull();
    }
}

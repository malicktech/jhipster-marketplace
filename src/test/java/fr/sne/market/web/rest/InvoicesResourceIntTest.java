package fr.sne.market.web.rest;

import fr.sne.market.MarketApp;

import fr.sne.market.domain.Invoices;
import fr.sne.market.repository.InvoicesRepository;
import fr.sne.market.repository.search.InvoicesSearchRepository;
import fr.sne.market.service.dto.InvoicesDTO;
import fr.sne.market.service.mapper.InvoicesMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvoicesResource REST controller.
 *
 * @see InvoicesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketApp.class)
public class InvoicesResourceIntTest {

    private static final LocalDate DEFAULT_INVOICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INVOICE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_DETAILS = "BBBBBBBBBB";

    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private InvoicesMapper invoicesMapper;

    @Autowired
    private InvoicesSearchRepository invoicesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvoicesMockMvc;

    private Invoices invoices;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvoicesResource invoicesResource = new InvoicesResource(invoicesRepository, invoicesMapper, invoicesSearchRepository);
        this.restInvoicesMockMvc = MockMvcBuilders.standaloneSetup(invoicesResource)
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
    public static Invoices createEntity(EntityManager em) {
        Invoices invoices = new Invoices()
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .invoiceDetails(DEFAULT_INVOICE_DETAILS);
        return invoices;
    }

    @Before
    public void initTest() {
        invoicesSearchRepository.deleteAll();
        invoices = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoices() throws Exception {
        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);
        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isCreated());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate + 1);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoices.getInvoiceDetails()).isEqualTo(DEFAULT_INVOICE_DETAILS);

        // Validate the Invoices in Elasticsearch
        Invoices invoicesEs = invoicesSearchRepository.findOne(testInvoices.getId());
        assertThat(invoicesEs).isEqualToComparingFieldByField(testInvoices);
    }

    @Test
    @Transactional
    public void createInvoicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();

        // Create the Invoices with an existing ID
        invoices.setId(1L);
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList
        restInvoicesMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoices.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceDetails").value(hasItem(DEFAULT_INVOICE_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get the invoices
        restInvoicesMockMvc.perform(get("/api/invoices/{id}", invoices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoices.getId().intValue()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.invoiceDetails").value(DEFAULT_INVOICE_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoices() throws Exception {
        // Get the invoices
        restInvoicesMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        invoicesSearchRepository.save(invoices);
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Update the invoices
        Invoices updatedInvoices = invoicesRepository.findOne(invoices.getId());
        updatedInvoices
            .invoiceDate(UPDATED_INVOICE_DATE)
            .invoiceDetails(UPDATED_INVOICE_DETAILS);
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(updatedInvoices);

        restInvoicesMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isOk());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoices.getInvoiceDetails()).isEqualTo(UPDATED_INVOICE_DETAILS);

        // Validate the Invoices in Elasticsearch
        Invoices invoicesEs = invoicesSearchRepository.findOne(testInvoices.getId());
        assertThat(invoicesEs).isEqualToComparingFieldByField(testInvoices);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoices() throws Exception {
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInvoicesMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isCreated());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        invoicesSearchRepository.save(invoices);
        int databaseSizeBeforeDelete = invoicesRepository.findAll().size();

        // Get the invoices
        restInvoicesMockMvc.perform(delete("/api/invoices/{id}", invoices.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean invoicesExistsInEs = invoicesSearchRepository.exists(invoices.getId());
        assertThat(invoicesExistsInEs).isFalse();

        // Validate the database is empty
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        invoicesSearchRepository.save(invoices);

        // Search the invoices
        restInvoicesMockMvc.perform(get("/api/_search/invoices?query=id:" + invoices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoices.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceDetails").value(hasItem(DEFAULT_INVOICE_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invoices.class);
        Invoices invoices1 = new Invoices();
        invoices1.setId(1L);
        Invoices invoices2 = new Invoices();
        invoices2.setId(invoices1.getId());
        assertThat(invoices1).isEqualTo(invoices2);
        invoices2.setId(2L);
        assertThat(invoices1).isNotEqualTo(invoices2);
        invoices1.setId(null);
        assertThat(invoices1).isNotEqualTo(invoices2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoicesDTO.class);
        InvoicesDTO invoicesDTO1 = new InvoicesDTO();
        invoicesDTO1.setId(1L);
        InvoicesDTO invoicesDTO2 = new InvoicesDTO();
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
        invoicesDTO2.setId(invoicesDTO1.getId());
        assertThat(invoicesDTO1).isEqualTo(invoicesDTO2);
        invoicesDTO2.setId(2L);
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
        invoicesDTO1.setId(null);
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(invoicesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(invoicesMapper.fromId(null)).isNull();
    }
}

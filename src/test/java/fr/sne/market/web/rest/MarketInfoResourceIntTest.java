package fr.sne.market.web.rest;

import fr.sne.market.MarketApp;

import fr.sne.market.domain.MarketInfo;
import fr.sne.market.repository.MarketInfoRepository;
import fr.sne.market.repository.search.MarketInfoSearchRepository;
import fr.sne.market.service.dto.MarketInfoDTO;
import fr.sne.market.service.mapper.MarketInfoMapper;
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
 * Test class for the MarketInfoResource REST controller.
 *
 * @see MarketInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketApp.class)
public class MarketInfoResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private MarketInfoRepository marketInfoRepository;

    @Autowired
    private MarketInfoMapper marketInfoMapper;

    @Autowired
    private MarketInfoSearchRepository marketInfoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketInfoMockMvc;

    private MarketInfo marketInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketInfoResource marketInfoResource = new MarketInfoResource(marketInfoRepository, marketInfoMapper, marketInfoSearchRepository);
        this.restMarketInfoMockMvc = MockMvcBuilders.standaloneSetup(marketInfoResource)
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
    public static MarketInfo createEntity(EntityManager em) {
        MarketInfo marketInfo = new MarketInfo()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return marketInfo;
    }

    @Before
    public void initTest() {
        marketInfoSearchRepository.deleteAll();
        marketInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketInfo() throws Exception {
        int databaseSizeBeforeCreate = marketInfoRepository.findAll().size();

        // Create the MarketInfo
        MarketInfoDTO marketInfoDTO = marketInfoMapper.toDto(marketInfo);
        restMarketInfoMockMvc.perform(post("/api/market-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketInfo in the database
        List<MarketInfo> marketInfoList = marketInfoRepository.findAll();
        assertThat(marketInfoList).hasSize(databaseSizeBeforeCreate + 1);
        MarketInfo testMarketInfo = marketInfoList.get(marketInfoList.size() - 1);
        assertThat(testMarketInfo.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testMarketInfo.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the MarketInfo in Elasticsearch
        MarketInfo marketInfoEs = marketInfoSearchRepository.findOne(testMarketInfo.getId());
        assertThat(marketInfoEs).isEqualToComparingFieldByField(testMarketInfo);
    }

    @Test
    @Transactional
    public void createMarketInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketInfoRepository.findAll().size();

        // Create the MarketInfo with an existing ID
        marketInfo.setId(1L);
        MarketInfoDTO marketInfoDTO = marketInfoMapper.toDto(marketInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketInfoMockMvc.perform(post("/api/market-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketInfo> marketInfoList = marketInfoRepository.findAll();
        assertThat(marketInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketInfos() throws Exception {
        // Initialize the database
        marketInfoRepository.saveAndFlush(marketInfo);

        // Get all the marketInfoList
        restMarketInfoMockMvc.perform(get("/api/market-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getMarketInfo() throws Exception {
        // Initialize the database
        marketInfoRepository.saveAndFlush(marketInfo);

        // Get the marketInfo
        restMarketInfoMockMvc.perform(get("/api/market-infos/{id}", marketInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketInfo.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketInfo() throws Exception {
        // Get the marketInfo
        restMarketInfoMockMvc.perform(get("/api/market-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketInfo() throws Exception {
        // Initialize the database
        marketInfoRepository.saveAndFlush(marketInfo);
        marketInfoSearchRepository.save(marketInfo);
        int databaseSizeBeforeUpdate = marketInfoRepository.findAll().size();

        // Update the marketInfo
        MarketInfo updatedMarketInfo = marketInfoRepository.findOne(marketInfo.getId());
        updatedMarketInfo
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        MarketInfoDTO marketInfoDTO = marketInfoMapper.toDto(updatedMarketInfo);

        restMarketInfoMockMvc.perform(put("/api/market-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketInfoDTO)))
            .andExpect(status().isOk());

        // Validate the MarketInfo in the database
        List<MarketInfo> marketInfoList = marketInfoRepository.findAll();
        assertThat(marketInfoList).hasSize(databaseSizeBeforeUpdate);
        MarketInfo testMarketInfo = marketInfoList.get(marketInfoList.size() - 1);
        assertThat(testMarketInfo.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testMarketInfo.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the MarketInfo in Elasticsearch
        MarketInfo marketInfoEs = marketInfoSearchRepository.findOne(testMarketInfo.getId());
        assertThat(marketInfoEs).isEqualToComparingFieldByField(testMarketInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketInfo() throws Exception {
        int databaseSizeBeforeUpdate = marketInfoRepository.findAll().size();

        // Create the MarketInfo
        MarketInfoDTO marketInfoDTO = marketInfoMapper.toDto(marketInfo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketInfoMockMvc.perform(put("/api/market-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the MarketInfo in the database
        List<MarketInfo> marketInfoList = marketInfoRepository.findAll();
        assertThat(marketInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketInfo() throws Exception {
        // Initialize the database
        marketInfoRepository.saveAndFlush(marketInfo);
        marketInfoSearchRepository.save(marketInfo);
        int databaseSizeBeforeDelete = marketInfoRepository.findAll().size();

        // Get the marketInfo
        restMarketInfoMockMvc.perform(delete("/api/market-infos/{id}", marketInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean marketInfoExistsInEs = marketInfoSearchRepository.exists(marketInfo.getId());
        assertThat(marketInfoExistsInEs).isFalse();

        // Validate the database is empty
        List<MarketInfo> marketInfoList = marketInfoRepository.findAll();
        assertThat(marketInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMarketInfo() throws Exception {
        // Initialize the database
        marketInfoRepository.saveAndFlush(marketInfo);
        marketInfoSearchRepository.save(marketInfo);

        // Search the marketInfo
        restMarketInfoMockMvc.perform(get("/api/_search/market-infos?query=id:" + marketInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketInfo.class);
        MarketInfo marketInfo1 = new MarketInfo();
        marketInfo1.setId(1L);
        MarketInfo marketInfo2 = new MarketInfo();
        marketInfo2.setId(marketInfo1.getId());
        assertThat(marketInfo1).isEqualTo(marketInfo2);
        marketInfo2.setId(2L);
        assertThat(marketInfo1).isNotEqualTo(marketInfo2);
        marketInfo1.setId(null);
        assertThat(marketInfo1).isNotEqualTo(marketInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketInfoDTO.class);
        MarketInfoDTO marketInfoDTO1 = new MarketInfoDTO();
        marketInfoDTO1.setId(1L);
        MarketInfoDTO marketInfoDTO2 = new MarketInfoDTO();
        assertThat(marketInfoDTO1).isNotEqualTo(marketInfoDTO2);
        marketInfoDTO2.setId(marketInfoDTO1.getId());
        assertThat(marketInfoDTO1).isEqualTo(marketInfoDTO2);
        marketInfoDTO2.setId(2L);
        assertThat(marketInfoDTO1).isNotEqualTo(marketInfoDTO2);
        marketInfoDTO1.setId(null);
        assertThat(marketInfoDTO1).isNotEqualTo(marketInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marketInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marketInfoMapper.fromId(null)).isNull();
    }
}

package fr.sne.market.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.market.domain.Shipments;

import fr.sne.market.repository.ShipmentsRepository;
import fr.sne.market.repository.search.ShipmentsSearchRepository;
import fr.sne.market.web.rest.util.HeaderUtil;
import fr.sne.market.service.dto.ShipmentsDTO;
import fr.sne.market.service.mapper.ShipmentsMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Shipments.
 */
@RestController
@RequestMapping("/api")
public class ShipmentsResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentsResource.class);

    private static final String ENTITY_NAME = "shipments";

    private final ShipmentsRepository shipmentsRepository;

    private final ShipmentsMapper shipmentsMapper;

    private final ShipmentsSearchRepository shipmentsSearchRepository;

    public ShipmentsResource(ShipmentsRepository shipmentsRepository, ShipmentsMapper shipmentsMapper, ShipmentsSearchRepository shipmentsSearchRepository) {
        this.shipmentsRepository = shipmentsRepository;
        this.shipmentsMapper = shipmentsMapper;
        this.shipmentsSearchRepository = shipmentsSearchRepository;
    }

    /**
     * POST  /shipments : Create a new shipments.
     *
     * @param shipmentsDTO the shipmentsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentsDTO, or with status 400 (Bad Request) if the shipments has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipments")
    @Timed
    public ResponseEntity<ShipmentsDTO> createShipments(@RequestBody ShipmentsDTO shipmentsDTO) throws URISyntaxException {
        log.debug("REST request to save Shipments : {}", shipmentsDTO);
        if (shipmentsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shipments cannot already have an ID")).body(null);
        }
        Shipments shipments = shipmentsMapper.toEntity(shipmentsDTO);
        shipments = shipmentsRepository.save(shipments);
        ShipmentsDTO result = shipmentsMapper.toDto(shipments);
        shipmentsSearchRepository.save(shipments);
        return ResponseEntity.created(new URI("/api/shipments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipments : Updates an existing shipments.
     *
     * @param shipmentsDTO the shipmentsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentsDTO,
     * or with status 400 (Bad Request) if the shipmentsDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipmentsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipments")
    @Timed
    public ResponseEntity<ShipmentsDTO> updateShipments(@RequestBody ShipmentsDTO shipmentsDTO) throws URISyntaxException {
        log.debug("REST request to update Shipments : {}", shipmentsDTO);
        if (shipmentsDTO.getId() == null) {
            return createShipments(shipmentsDTO);
        }
        Shipments shipments = shipmentsMapper.toEntity(shipmentsDTO);
        shipments = shipmentsRepository.save(shipments);
        ShipmentsDTO result = shipmentsMapper.toDto(shipments);
        shipmentsSearchRepository.save(shipments);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipments : get all the shipments.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of shipments in body
     */
    @GetMapping("/shipments")
    @Timed
    public List<ShipmentsDTO> getAllShipments(@RequestParam(required = false) String filter) {
        if ("order-is-null".equals(filter)) {
            log.debug("REST request to get all Shipmentss where order is null");
            return StreamSupport
                .stream(shipmentsRepository.findAll().spliterator(), false)
                .filter(shipments -> shipments.getOrder() == null)
                .map(shipmentsMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        log.debug("REST request to get all Shipments");
        List<Shipments> shipments = shipmentsRepository.findAll();
        return shipmentsMapper.toDto(shipments);
    }

    /**
     * GET  /shipments/:id : get the "id" shipments.
     *
     * @param id the id of the shipmentsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipments/{id}")
    @Timed
    public ResponseEntity<ShipmentsDTO> getShipments(@PathVariable Long id) {
        log.debug("REST request to get Shipments : {}", id);
        Shipments shipments = shipmentsRepository.findOne(id);
        ShipmentsDTO shipmentsDTO = shipmentsMapper.toDto(shipments);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shipmentsDTO));
    }

    /**
     * DELETE  /shipments/:id : delete the "id" shipments.
     *
     * @param id the id of the shipmentsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipments/{id}")
    @Timed
    public ResponseEntity<Void> deleteShipments(@PathVariable Long id) {
        log.debug("REST request to delete Shipments : {}", id);
        shipmentsRepository.delete(id);
        shipmentsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shipments?query=:query : search for the shipments corresponding
     * to the query.
     *
     * @param query the query of the shipments search
     * @return the result of the search
     */
    @GetMapping("/_search/shipments")
    @Timed
    public List<ShipmentsDTO> searchShipments(@RequestParam String query) {
        log.debug("REST request to search Shipments for query {}", query);
        return StreamSupport
            .stream(shipmentsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(shipmentsMapper::toDto)
            .collect(Collectors.toList());
    }

}

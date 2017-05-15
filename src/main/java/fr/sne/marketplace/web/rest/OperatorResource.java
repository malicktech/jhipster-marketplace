package fr.sne.marketplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sne.marketplace.domain.Operator;

import fr.sne.marketplace.repository.OperatorRepository;
import fr.sne.marketplace.repository.search.OperatorSearchRepository;
import fr.sne.marketplace.web.rest.util.HeaderUtil;
import fr.sne.marketplace.web.rest.util.PaginationUtil;
import fr.sne.marketplace.service.dto.OperatorDTO;
import fr.sne.marketplace.service.mapper.OperatorMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Operator.
 */
@RestController
@RequestMapping("/api")
public class OperatorResource {

    private final Logger log = LoggerFactory.getLogger(OperatorResource.class);

    private static final String ENTITY_NAME = "operator";
        
    private final OperatorRepository operatorRepository;

    private final OperatorMapper operatorMapper;

    private final OperatorSearchRepository operatorSearchRepository;

    public OperatorResource(OperatorRepository operatorRepository, OperatorMapper operatorMapper, OperatorSearchRepository operatorSearchRepository) {
        this.operatorRepository = operatorRepository;
        this.operatorMapper = operatorMapper;
        this.operatorSearchRepository = operatorSearchRepository;
    }

    /**
     * POST  /operators : Create a new operator.
     *
     * @param operatorDTO the operatorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operatorDTO, or with status 400 (Bad Request) if the operator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operators")
    @Timed
    public ResponseEntity<OperatorDTO> createOperator(@Valid @RequestBody OperatorDTO operatorDTO) throws URISyntaxException {
        log.debug("REST request to save Operator : {}", operatorDTO);
        if (operatorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new operator cannot already have an ID")).body(null);
        }
        Operator operator = operatorMapper.toEntity(operatorDTO);
        operator = operatorRepository.save(operator);
        OperatorDTO result = operatorMapper.toDto(operator);
        operatorSearchRepository.save(operator);
        return ResponseEntity.created(new URI("/api/operators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operators : Updates an existing operator.
     *
     * @param operatorDTO the operatorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operatorDTO,
     * or with status 400 (Bad Request) if the operatorDTO is not valid,
     * or with status 500 (Internal Server Error) if the operatorDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operators")
    @Timed
    public ResponseEntity<OperatorDTO> updateOperator(@Valid @RequestBody OperatorDTO operatorDTO) throws URISyntaxException {
        log.debug("REST request to update Operator : {}", operatorDTO);
        if (operatorDTO.getId() == null) {
            return createOperator(operatorDTO);
        }
        Operator operator = operatorMapper.toEntity(operatorDTO);
        operator = operatorRepository.save(operator);
        OperatorDTO result = operatorMapper.toDto(operator);
        operatorSearchRepository.save(operator);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operatorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operators : get all the operators.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operators in body
     */
    @GetMapping("/operators")
    @Timed
    public ResponseEntity<List<OperatorDTO>> getAllOperators(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Operators");
        Page<Operator> page = operatorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operators");
        return new ResponseEntity<>(operatorMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /operators/:id : get the "id" operator.
     *
     * @param id the id of the operatorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operatorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/operators/{id}")
    @Timed
    public ResponseEntity<OperatorDTO> getOperator(@PathVariable Long id) {
        log.debug("REST request to get Operator : {}", id);
        Operator operator = operatorRepository.findOne(id);
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operatorDTO));
    }

    /**
     * DELETE  /operators/:id : delete the "id" operator.
     *
     * @param id the id of the operatorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operators/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperator(@PathVariable Long id) {
        log.debug("REST request to delete Operator : {}", id);
        operatorRepository.delete(id);
        operatorSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/operators?query=:query : search for the operator corresponding
     * to the query.
     *
     * @param query the query of the operator search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/operators")
    @Timed
    public ResponseEntity<List<OperatorDTO>> searchOperators(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Operators for query {}", query);
        Page<Operator> page = operatorSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/operators");
        return new ResponseEntity<>(operatorMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }


}

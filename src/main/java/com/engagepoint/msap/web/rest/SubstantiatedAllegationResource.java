package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.SubstantiatedAllegation;
import com.engagepoint.msap.repository.SubstantiatedAllegationRepository;
import com.engagepoint.msap.repository.search.SubstantiatedAllegationSearchRepository;
import com.engagepoint.msap.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SubstantiatedAllegation.
 */
@RestController
@RequestMapping("/api")
public class SubstantiatedAllegationResource {

    private final Logger log = LoggerFactory.getLogger(SubstantiatedAllegationResource.class);
        
    @Inject
    private SubstantiatedAllegationRepository substantiatedAllegationRepository;
    
    @Inject
    private SubstantiatedAllegationSearchRepository substantiatedAllegationSearchRepository;
    
    /**
     * POST  /substantiatedAllegations -> Create a new substantiatedAllegation.
     */
    @RequestMapping(value = "/substantiatedAllegations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubstantiatedAllegation> createSubstantiatedAllegation(@RequestBody SubstantiatedAllegation substantiatedAllegation) throws URISyntaxException {
        log.debug("REST request to save SubstantiatedAllegation : {}", substantiatedAllegation);
        if (substantiatedAllegation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("substantiatedAllegation", "idexists", "A new substantiatedAllegation cannot already have an ID")).body(null);
        }
        SubstantiatedAllegation result = substantiatedAllegationRepository.save(substantiatedAllegation);
        substantiatedAllegationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/substantiatedAllegations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("substantiatedAllegation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /substantiatedAllegations -> Updates an existing substantiatedAllegation.
     */
    @RequestMapping(value = "/substantiatedAllegations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubstantiatedAllegation> updateSubstantiatedAllegation(@RequestBody SubstantiatedAllegation substantiatedAllegation) throws URISyntaxException {
        log.debug("REST request to update SubstantiatedAllegation : {}", substantiatedAllegation);
        if (substantiatedAllegation.getId() == null) {
            return createSubstantiatedAllegation(substantiatedAllegation);
        }
        SubstantiatedAllegation result = substantiatedAllegationRepository.save(substantiatedAllegation);
        substantiatedAllegationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("substantiatedAllegation", substantiatedAllegation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /substantiatedAllegations -> get all the substantiatedAllegations.
     */
    @RequestMapping(value = "/substantiatedAllegations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SubstantiatedAllegation> getAllSubstantiatedAllegations() {
        log.debug("REST request to get all SubstantiatedAllegations");
        return substantiatedAllegationRepository.findAll();
            }

    /**
     * GET  /substantiatedAllegations/:id -> get the "id" substantiatedAllegation.
     */
    @RequestMapping(value = "/substantiatedAllegations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubstantiatedAllegation> getSubstantiatedAllegation(@PathVariable Long id) {
        log.debug("REST request to get SubstantiatedAllegation : {}", id);
        SubstantiatedAllegation substantiatedAllegation = substantiatedAllegationRepository.findOne(id);
        return Optional.ofNullable(substantiatedAllegation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /substantiatedAllegations/:id -> delete the "id" substantiatedAllegation.
     */
    @RequestMapping(value = "/substantiatedAllegations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSubstantiatedAllegation(@PathVariable Long id) {
        log.debug("REST request to delete SubstantiatedAllegation : {}", id);
        substantiatedAllegationRepository.delete(id);
        substantiatedAllegationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("substantiatedAllegation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/substantiatedAllegations/:query -> search for the substantiatedAllegation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/substantiatedAllegations/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SubstantiatedAllegation> searchSubstantiatedAllegations(@PathVariable String query) {
        log.debug("REST request to search SubstantiatedAllegations for query {}", query);
        return StreamSupport
            .stream(substantiatedAllegationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

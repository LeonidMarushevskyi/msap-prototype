package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.LookupProviderType;
import com.engagepoint.msap.repository.LookupProviderTypeRepository;
import com.engagepoint.msap.repository.search.LookupProviderTypeSearchRepository;
import com.engagepoint.msap.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing LookupProviderType.
 */
@RestController
@RequestMapping("/api")
public class LookupProviderTypeResource {

    private final Logger log = LoggerFactory.getLogger(LookupProviderTypeResource.class);
        
    @Inject
    private LookupProviderTypeRepository lookupProviderTypeRepository;
    
    @Inject
    private LookupProviderTypeSearchRepository lookupProviderTypeSearchRepository;
    
    /**
     * POST  /lookupProviderTypes -> Create a new lookupProviderType.
     */
    @RequestMapping(value = "/lookupProviderTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupProviderType> createLookupProviderType(@Valid @RequestBody LookupProviderType lookupProviderType) throws URISyntaxException {
        log.debug("REST request to save LookupProviderType : {}", lookupProviderType);
        if (lookupProviderType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lookupProviderType", "idexists", "A new lookupProviderType cannot already have an ID")).body(null);
        }
        LookupProviderType result = lookupProviderTypeRepository.save(lookupProviderType);
        lookupProviderTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/lookupProviderTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lookupProviderType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lookupProviderTypes -> Updates an existing lookupProviderType.
     */
    @RequestMapping(value = "/lookupProviderTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupProviderType> updateLookupProviderType(@Valid @RequestBody LookupProviderType lookupProviderType) throws URISyntaxException {
        log.debug("REST request to update LookupProviderType : {}", lookupProviderType);
        if (lookupProviderType.getId() == null) {
            return createLookupProviderType(lookupProviderType);
        }
        LookupProviderType result = lookupProviderTypeRepository.save(lookupProviderType);
        lookupProviderTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lookupProviderType", lookupProviderType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lookupProviderTypes -> get all the lookupProviderTypes.
     */
    @RequestMapping(value = "/lookupProviderTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupProviderType> getAllLookupProviderTypes() {
        log.debug("REST request to get all LookupProviderTypes");
        return lookupProviderTypeRepository.findAll();
            }

    /**
     * GET  /lookupProviderTypes/:id -> get the "id" lookupProviderType.
     */
    @RequestMapping(value = "/lookupProviderTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupProviderType> getLookupProviderType(@PathVariable Long id) {
        log.debug("REST request to get LookupProviderType : {}", id);
        LookupProviderType lookupProviderType = lookupProviderTypeRepository.findOne(id);
        return Optional.ofNullable(lookupProviderType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lookupProviderTypes/:id -> delete the "id" lookupProviderType.
     */
    @RequestMapping(value = "/lookupProviderTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLookupProviderType(@PathVariable Long id) {
        log.debug("REST request to delete LookupProviderType : {}", id);
        lookupProviderTypeRepository.delete(id);
        lookupProviderTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lookupProviderType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lookupProviderTypes/:query -> search for the lookupProviderType corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/lookupProviderTypes/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupProviderType> searchLookupProviderTypes(@PathVariable String query) {
        log.debug("REST request to search LookupProviderTypes for query {}", query);
        return StreamSupport
            .stream(lookupProviderTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.LookupLicenseType;
import com.engagepoint.msap.repository.LookupLicenseTypeRepository;
import com.engagepoint.msap.repository.search.LookupLicenseTypeSearchRepository;
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
 * REST controller for managing LookupLicenseType.
 */
@RestController
@RequestMapping("/api")
public class LookupLicenseTypeResource {

    private final Logger log = LoggerFactory.getLogger(LookupLicenseTypeResource.class);
        
    @Inject
    private LookupLicenseTypeRepository lookupLicenseTypeRepository;
    
    @Inject
    private LookupLicenseTypeSearchRepository lookupLicenseTypeSearchRepository;
    
    /**
     * POST  /lookupLicenseTypes -> Create a new lookupLicenseType.
     */
    @RequestMapping(value = "/lookupLicenseTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupLicenseType> createLookupLicenseType(@Valid @RequestBody LookupLicenseType lookupLicenseType) throws URISyntaxException {
        log.debug("REST request to save LookupLicenseType : {}", lookupLicenseType);
        if (lookupLicenseType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lookupLicenseType", "idexists", "A new lookupLicenseType cannot already have an ID")).body(null);
        }
        LookupLicenseType result = lookupLicenseTypeRepository.save(lookupLicenseType);
        lookupLicenseTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/lookupLicenseTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lookupLicenseType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lookupLicenseTypes -> Updates an existing lookupLicenseType.
     */
    @RequestMapping(value = "/lookupLicenseTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupLicenseType> updateLookupLicenseType(@Valid @RequestBody LookupLicenseType lookupLicenseType) throws URISyntaxException {
        log.debug("REST request to update LookupLicenseType : {}", lookupLicenseType);
        if (lookupLicenseType.getId() == null) {
            return createLookupLicenseType(lookupLicenseType);
        }
        LookupLicenseType result = lookupLicenseTypeRepository.save(lookupLicenseType);
        lookupLicenseTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lookupLicenseType", lookupLicenseType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lookupLicenseTypes -> get all the lookupLicenseTypes.
     */
    @RequestMapping(value = "/lookupLicenseTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupLicenseType> getAllLookupLicenseTypes() {
        log.debug("REST request to get all LookupLicenseTypes");
        return lookupLicenseTypeRepository.findAll();
            }

    /**
     * GET  /lookupLicenseTypes/:id -> get the "id" lookupLicenseType.
     */
    @RequestMapping(value = "/lookupLicenseTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupLicenseType> getLookupLicenseType(@PathVariable Long id) {
        log.debug("REST request to get LookupLicenseType : {}", id);
        LookupLicenseType lookupLicenseType = lookupLicenseTypeRepository.findOne(id);
        return Optional.ofNullable(lookupLicenseType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lookupLicenseTypes/:id -> delete the "id" lookupLicenseType.
     */
    @RequestMapping(value = "/lookupLicenseTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLookupLicenseType(@PathVariable Long id) {
        log.debug("REST request to delete LookupLicenseType : {}", id);
        lookupLicenseTypeRepository.delete(id);
        lookupLicenseTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lookupLicenseType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lookupLicenseTypes/:query -> search for the lookupLicenseType corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/lookupLicenseTypes/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupLicenseType> searchLookupLicenseTypes(@PathVariable String query) {
        log.debug("REST request to search LookupLicenseTypes for query {}", query);
        return StreamSupport
            .stream(lookupLicenseTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

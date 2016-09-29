package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.LookupSpecialNeedType;
import com.engagepoint.msap.repository.LookupSpecialNeedTypeRepository;
import com.engagepoint.msap.repository.search.LookupSpecialNeedTypeSearchRepository;
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
 * REST controller for managing LookupSpecialNeedType.
 */
@RestController
@RequestMapping("/api")
public class LookupSpecialNeedTypeResource {

    private final Logger log = LoggerFactory.getLogger(LookupSpecialNeedTypeResource.class);
        
    @Inject
    private LookupSpecialNeedTypeRepository lookupSpecialNeedTypeRepository;
    
    @Inject
    private LookupSpecialNeedTypeSearchRepository lookupSpecialNeedTypeSearchRepository;
    
    /**
     * POST  /lookupSpecialNeedTypes -> Create a new lookupSpecialNeedType.
     */
    @RequestMapping(value = "/lookupSpecialNeedTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupSpecialNeedType> createLookupSpecialNeedType(@Valid @RequestBody LookupSpecialNeedType lookupSpecialNeedType) throws URISyntaxException {
        log.debug("REST request to save LookupSpecialNeedType : {}", lookupSpecialNeedType);
        if (lookupSpecialNeedType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lookupSpecialNeedType", "idexists", "A new lookupSpecialNeedType cannot already have an ID")).body(null);
        }
        LookupSpecialNeedType result = lookupSpecialNeedTypeRepository.save(lookupSpecialNeedType);
        lookupSpecialNeedTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/lookupSpecialNeedTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lookupSpecialNeedType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lookupSpecialNeedTypes -> Updates an existing lookupSpecialNeedType.
     */
    @RequestMapping(value = "/lookupSpecialNeedTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupSpecialNeedType> updateLookupSpecialNeedType(@Valid @RequestBody LookupSpecialNeedType lookupSpecialNeedType) throws URISyntaxException {
        log.debug("REST request to update LookupSpecialNeedType : {}", lookupSpecialNeedType);
        if (lookupSpecialNeedType.getId() == null) {
            return createLookupSpecialNeedType(lookupSpecialNeedType);
        }
        LookupSpecialNeedType result = lookupSpecialNeedTypeRepository.save(lookupSpecialNeedType);
        lookupSpecialNeedTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lookupSpecialNeedType", lookupSpecialNeedType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lookupSpecialNeedTypes -> get all the lookupSpecialNeedTypes.
     */
    @RequestMapping(value = "/lookupSpecialNeedTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupSpecialNeedType> getAllLookupSpecialNeedTypes() {
        log.debug("REST request to get all LookupSpecialNeedTypes");
        return lookupSpecialNeedTypeRepository.findAll();
            }

    /**
     * GET  /lookupSpecialNeedTypes/:id -> get the "id" lookupSpecialNeedType.
     */
    @RequestMapping(value = "/lookupSpecialNeedTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupSpecialNeedType> getLookupSpecialNeedType(@PathVariable Long id) {
        log.debug("REST request to get LookupSpecialNeedType : {}", id);
        LookupSpecialNeedType lookupSpecialNeedType = lookupSpecialNeedTypeRepository.findOne(id);
        return Optional.ofNullable(lookupSpecialNeedType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lookupSpecialNeedTypes/:id -> delete the "id" lookupSpecialNeedType.
     */
    @RequestMapping(value = "/lookupSpecialNeedTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLookupSpecialNeedType(@PathVariable Long id) {
        log.debug("REST request to delete LookupSpecialNeedType : {}", id);
        lookupSpecialNeedTypeRepository.delete(id);
        lookupSpecialNeedTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lookupSpecialNeedType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lookupSpecialNeedTypes/:query -> search for the lookupSpecialNeedType corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/lookupSpecialNeedTypes/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupSpecialNeedType> searchLookupSpecialNeedTypes(@PathVariable String query) {
        log.debug("REST request to search LookupSpecialNeedTypes for query {}", query);
        return StreamSupport
            .stream(lookupSpecialNeedTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

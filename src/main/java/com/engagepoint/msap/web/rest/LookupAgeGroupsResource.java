package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.LookupAgeGroups;
import com.engagepoint.msap.repository.LookupAgeGroupsRepository;
import com.engagepoint.msap.repository.search.LookupAgeGroupsSearchRepository;
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
 * REST controller for managing LookupAgeGroups.
 */
@RestController
@RequestMapping("/api")
public class LookupAgeGroupsResource {

    private final Logger log = LoggerFactory.getLogger(LookupAgeGroupsResource.class);
        
    @Inject
    private LookupAgeGroupsRepository lookupAgeGroupsRepository;
    
    @Inject
    private LookupAgeGroupsSearchRepository lookupAgeGroupsSearchRepository;
    
    /**
     * POST  /lookupAgeGroupss -> Create a new lookupAgeGroups.
     */
    @RequestMapping(value = "/lookupAgeGroupss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupAgeGroups> createLookupAgeGroups(@Valid @RequestBody LookupAgeGroups lookupAgeGroups) throws URISyntaxException {
        log.debug("REST request to save LookupAgeGroups : {}", lookupAgeGroups);
        if (lookupAgeGroups.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lookupAgeGroups", "idexists", "A new lookupAgeGroups cannot already have an ID")).body(null);
        }
        LookupAgeGroups result = lookupAgeGroupsRepository.save(lookupAgeGroups);
        lookupAgeGroupsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/lookupAgeGroupss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lookupAgeGroups", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lookupAgeGroupss -> Updates an existing lookupAgeGroups.
     */
    @RequestMapping(value = "/lookupAgeGroupss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupAgeGroups> updateLookupAgeGroups(@Valid @RequestBody LookupAgeGroups lookupAgeGroups) throws URISyntaxException {
        log.debug("REST request to update LookupAgeGroups : {}", lookupAgeGroups);
        if (lookupAgeGroups.getId() == null) {
            return createLookupAgeGroups(lookupAgeGroups);
        }
        LookupAgeGroups result = lookupAgeGroupsRepository.save(lookupAgeGroups);
        lookupAgeGroupsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lookupAgeGroups", lookupAgeGroups.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lookupAgeGroupss -> get all the lookupAgeGroupss.
     */
    @RequestMapping(value = "/lookupAgeGroupss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupAgeGroups> getAllLookupAgeGroupss() {
        log.debug("REST request to get all LookupAgeGroupss");
        return lookupAgeGroupsRepository.findAll();
            }

    /**
     * GET  /lookupAgeGroupss/:id -> get the "id" lookupAgeGroups.
     */
    @RequestMapping(value = "/lookupAgeGroupss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupAgeGroups> getLookupAgeGroups(@PathVariable Long id) {
        log.debug("REST request to get LookupAgeGroups : {}", id);
        LookupAgeGroups lookupAgeGroups = lookupAgeGroupsRepository.findOne(id);
        return Optional.ofNullable(lookupAgeGroups)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lookupAgeGroupss/:id -> delete the "id" lookupAgeGroups.
     */
    @RequestMapping(value = "/lookupAgeGroupss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLookupAgeGroups(@PathVariable Long id) {
        log.debug("REST request to delete LookupAgeGroups : {}", id);
        lookupAgeGroupsRepository.delete(id);
        lookupAgeGroupsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lookupAgeGroups", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lookupAgeGroupss/:query -> search for the lookupAgeGroups corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/lookupAgeGroupss/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupAgeGroups> searchLookupAgeGroupss(@PathVariable String query) {
        log.debug("REST request to search LookupAgeGroupss for query {}", query);
        return StreamSupport
            .stream(lookupAgeGroupsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

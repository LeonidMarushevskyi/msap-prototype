package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.LookupSpecialNeedGroup;
import com.engagepoint.msap.repository.LookupSpecialNeedGroupRepository;
import com.engagepoint.msap.repository.search.LookupSpecialNeedGroupSearchRepository;
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
 * REST controller for managing LookupSpecialNeedGroup.
 */
@RestController
@RequestMapping("/api")
public class LookupSpecialNeedGroupResource {

    private final Logger log = LoggerFactory.getLogger(LookupSpecialNeedGroupResource.class);
        
    @Inject
    private LookupSpecialNeedGroupRepository lookupSpecialNeedGroupRepository;
    
    @Inject
    private LookupSpecialNeedGroupSearchRepository lookupSpecialNeedGroupSearchRepository;
    
    /**
     * POST  /lookupSpecialNeedGroups -> Create a new lookupSpecialNeedGroup.
     */
    @RequestMapping(value = "/lookupSpecialNeedGroups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupSpecialNeedGroup> createLookupSpecialNeedGroup(@Valid @RequestBody LookupSpecialNeedGroup lookupSpecialNeedGroup) throws URISyntaxException {
        log.debug("REST request to save LookupSpecialNeedGroup : {}", lookupSpecialNeedGroup);
        if (lookupSpecialNeedGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lookupSpecialNeedGroup", "idexists", "A new lookupSpecialNeedGroup cannot already have an ID")).body(null);
        }
        LookupSpecialNeedGroup result = lookupSpecialNeedGroupRepository.save(lookupSpecialNeedGroup);
        lookupSpecialNeedGroupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/lookupSpecialNeedGroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lookupSpecialNeedGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lookupSpecialNeedGroups -> Updates an existing lookupSpecialNeedGroup.
     */
    @RequestMapping(value = "/lookupSpecialNeedGroups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupSpecialNeedGroup> updateLookupSpecialNeedGroup(@Valid @RequestBody LookupSpecialNeedGroup lookupSpecialNeedGroup) throws URISyntaxException {
        log.debug("REST request to update LookupSpecialNeedGroup : {}", lookupSpecialNeedGroup);
        if (lookupSpecialNeedGroup.getId() == null) {
            return createLookupSpecialNeedGroup(lookupSpecialNeedGroup);
        }
        LookupSpecialNeedGroup result = lookupSpecialNeedGroupRepository.save(lookupSpecialNeedGroup);
        lookupSpecialNeedGroupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lookupSpecialNeedGroup", lookupSpecialNeedGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lookupSpecialNeedGroups -> get all the lookupSpecialNeedGroups.
     */
    @RequestMapping(value = "/lookupSpecialNeedGroups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupSpecialNeedGroup> getAllLookupSpecialNeedGroups() {
        log.debug("REST request to get all LookupSpecialNeedGroups");
        return lookupSpecialNeedGroupRepository.findAll();
            }

    /**
     * GET  /lookupSpecialNeedGroups/:id -> get the "id" lookupSpecialNeedGroup.
     */
    @RequestMapping(value = "/lookupSpecialNeedGroups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupSpecialNeedGroup> getLookupSpecialNeedGroup(@PathVariable Long id) {
        log.debug("REST request to get LookupSpecialNeedGroup : {}", id);
        LookupSpecialNeedGroup lookupSpecialNeedGroup = lookupSpecialNeedGroupRepository.findOne(id);
        return Optional.ofNullable(lookupSpecialNeedGroup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lookupSpecialNeedGroups/:id -> delete the "id" lookupSpecialNeedGroup.
     */
    @RequestMapping(value = "/lookupSpecialNeedGroups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLookupSpecialNeedGroup(@PathVariable Long id) {
        log.debug("REST request to delete LookupSpecialNeedGroup : {}", id);
        lookupSpecialNeedGroupRepository.delete(id);
        lookupSpecialNeedGroupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lookupSpecialNeedGroup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lookupSpecialNeedGroups/:query -> search for the lookupSpecialNeedGroup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/lookupSpecialNeedGroups/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupSpecialNeedGroup> searchLookupSpecialNeedGroups(@PathVariable String query) {
        log.debug("REST request to search LookupSpecialNeedGroups for query {}", query);
        return StreamSupport
            .stream(lookupSpecialNeedGroupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.SupportedSpecialNeed;
import com.engagepoint.msap.repository.SupportedSpecialNeedRepository;
import com.engagepoint.msap.repository.search.SupportedSpecialNeedSearchRepository;
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
 * REST controller for managing SupportedSpecialNeed.
 */
@RestController
@RequestMapping("/api")
public class SupportedSpecialNeedResource {

    private final Logger log = LoggerFactory.getLogger(SupportedSpecialNeedResource.class);
        
    @Inject
    private SupportedSpecialNeedRepository supportedSpecialNeedRepository;
    
    @Inject
    private SupportedSpecialNeedSearchRepository supportedSpecialNeedSearchRepository;
    
    /**
     * POST  /supportedSpecialNeeds -> Create a new supportedSpecialNeed.
     */
    @RequestMapping(value = "/supportedSpecialNeeds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupportedSpecialNeed> createSupportedSpecialNeed(@RequestBody SupportedSpecialNeed supportedSpecialNeed) throws URISyntaxException {
        log.debug("REST request to save SupportedSpecialNeed : {}", supportedSpecialNeed);
        if (supportedSpecialNeed.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("supportedSpecialNeed", "idexists", "A new supportedSpecialNeed cannot already have an ID")).body(null);
        }
        SupportedSpecialNeed result = supportedSpecialNeedRepository.save(supportedSpecialNeed);
        supportedSpecialNeedSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/supportedSpecialNeeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("supportedSpecialNeed", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supportedSpecialNeeds -> Updates an existing supportedSpecialNeed.
     */
    @RequestMapping(value = "/supportedSpecialNeeds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupportedSpecialNeed> updateSupportedSpecialNeed(@RequestBody SupportedSpecialNeed supportedSpecialNeed) throws URISyntaxException {
        log.debug("REST request to update SupportedSpecialNeed : {}", supportedSpecialNeed);
        if (supportedSpecialNeed.getId() == null) {
            return createSupportedSpecialNeed(supportedSpecialNeed);
        }
        SupportedSpecialNeed result = supportedSpecialNeedRepository.save(supportedSpecialNeed);
        supportedSpecialNeedSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("supportedSpecialNeed", supportedSpecialNeed.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supportedSpecialNeeds -> get all the supportedSpecialNeeds.
     */
    @RequestMapping(value = "/supportedSpecialNeeds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SupportedSpecialNeed> getAllSupportedSpecialNeeds() {
        log.debug("REST request to get all SupportedSpecialNeeds");
        return supportedSpecialNeedRepository.findAll();
            }

    /**
     * GET  /supportedSpecialNeeds/:id -> get the "id" supportedSpecialNeed.
     */
    @RequestMapping(value = "/supportedSpecialNeeds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupportedSpecialNeed> getSupportedSpecialNeed(@PathVariable Long id) {
        log.debug("REST request to get SupportedSpecialNeed : {}", id);
        SupportedSpecialNeed supportedSpecialNeed = supportedSpecialNeedRepository.findOne(id);
        return Optional.ofNullable(supportedSpecialNeed)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /supportedSpecialNeeds/:id -> delete the "id" supportedSpecialNeed.
     */
    @RequestMapping(value = "/supportedSpecialNeeds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSupportedSpecialNeed(@PathVariable Long id) {
        log.debug("REST request to delete SupportedSpecialNeed : {}", id);
        supportedSpecialNeedRepository.delete(id);
        supportedSpecialNeedSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("supportedSpecialNeed", id.toString())).build();
    }

    /**
     * SEARCH  /_search/supportedSpecialNeeds/:query -> search for the supportedSpecialNeed corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/supportedSpecialNeeds/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SupportedSpecialNeed> searchSupportedSpecialNeeds(@PathVariable String query) {
        log.debug("REST request to search SupportedSpecialNeeds for query {}", query);
        return StreamSupport
            .stream(supportedSpecialNeedSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

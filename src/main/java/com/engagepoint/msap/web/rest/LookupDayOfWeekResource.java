package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.LookupDayOfWeek;
import com.engagepoint.msap.repository.LookupDayOfWeekRepository;
import com.engagepoint.msap.repository.search.LookupDayOfWeekSearchRepository;
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
 * REST controller for managing LookupDayOfWeek.
 */
@RestController
@RequestMapping("/api")
public class LookupDayOfWeekResource {

    private final Logger log = LoggerFactory.getLogger(LookupDayOfWeekResource.class);
        
    @Inject
    private LookupDayOfWeekRepository lookupDayOfWeekRepository;
    
    @Inject
    private LookupDayOfWeekSearchRepository lookupDayOfWeekSearchRepository;
    
    /**
     * POST  /lookupDayOfWeeks -> Create a new lookupDayOfWeek.
     */
    @RequestMapping(value = "/lookupDayOfWeeks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupDayOfWeek> createLookupDayOfWeek(@Valid @RequestBody LookupDayOfWeek lookupDayOfWeek) throws URISyntaxException {
        log.debug("REST request to save LookupDayOfWeek : {}", lookupDayOfWeek);
        if (lookupDayOfWeek.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lookupDayOfWeek", "idexists", "A new lookupDayOfWeek cannot already have an ID")).body(null);
        }
        LookupDayOfWeek result = lookupDayOfWeekRepository.save(lookupDayOfWeek);
        lookupDayOfWeekSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/lookupDayOfWeeks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lookupDayOfWeek", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lookupDayOfWeeks -> Updates an existing lookupDayOfWeek.
     */
    @RequestMapping(value = "/lookupDayOfWeeks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupDayOfWeek> updateLookupDayOfWeek(@Valid @RequestBody LookupDayOfWeek lookupDayOfWeek) throws URISyntaxException {
        log.debug("REST request to update LookupDayOfWeek : {}", lookupDayOfWeek);
        if (lookupDayOfWeek.getId() == null) {
            return createLookupDayOfWeek(lookupDayOfWeek);
        }
        LookupDayOfWeek result = lookupDayOfWeekRepository.save(lookupDayOfWeek);
        lookupDayOfWeekSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lookupDayOfWeek", lookupDayOfWeek.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lookupDayOfWeeks -> get all the lookupDayOfWeeks.
     */
    @RequestMapping(value = "/lookupDayOfWeeks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupDayOfWeek> getAllLookupDayOfWeeks() {
        log.debug("REST request to get all LookupDayOfWeeks");
        return lookupDayOfWeekRepository.findAll();
            }

    /**
     * GET  /lookupDayOfWeeks/:id -> get the "id" lookupDayOfWeek.
     */
    @RequestMapping(value = "/lookupDayOfWeeks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupDayOfWeek> getLookupDayOfWeek(@PathVariable Long id) {
        log.debug("REST request to get LookupDayOfWeek : {}", id);
        LookupDayOfWeek lookupDayOfWeek = lookupDayOfWeekRepository.findOne(id);
        return Optional.ofNullable(lookupDayOfWeek)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lookupDayOfWeeks/:id -> delete the "id" lookupDayOfWeek.
     */
    @RequestMapping(value = "/lookupDayOfWeeks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLookupDayOfWeek(@PathVariable Long id) {
        log.debug("REST request to delete LookupDayOfWeek : {}", id);
        lookupDayOfWeekRepository.delete(id);
        lookupDayOfWeekSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lookupDayOfWeek", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lookupDayOfWeeks/:query -> search for the lookupDayOfWeek corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/lookupDayOfWeeks/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupDayOfWeek> searchLookupDayOfWeeks(@PathVariable String query) {
        log.debug("REST request to search LookupDayOfWeeks for query {}", query);
        return StreamSupport
            .stream(lookupDayOfWeekSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

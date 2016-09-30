package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.LookupLanguage;
import com.engagepoint.msap.repository.LookupLanguageRepository;
import com.engagepoint.msap.repository.search.LookupLanguageSearchRepository;
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
 * REST controller for managing LookupLanguage.
 */
@RestController
@RequestMapping("/api")
public class LookupLanguageResource {

    private final Logger log = LoggerFactory.getLogger(LookupLanguageResource.class);
        
    @Inject
    private LookupLanguageRepository lookupLanguageRepository;
    
    @Inject
    private LookupLanguageSearchRepository lookupLanguageSearchRepository;
    
    /**
     * POST  /lookupLanguages -> Create a new lookupLanguage.
     */
    @RequestMapping(value = "/lookupLanguages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupLanguage> createLookupLanguage(@Valid @RequestBody LookupLanguage lookupLanguage) throws URISyntaxException {
        log.debug("REST request to save LookupLanguage : {}", lookupLanguage);
        if (lookupLanguage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lookupLanguage", "idexists", "A new lookupLanguage cannot already have an ID")).body(null);
        }
        LookupLanguage result = lookupLanguageRepository.save(lookupLanguage);
        lookupLanguageSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/lookupLanguages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lookupLanguage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lookupLanguages -> Updates an existing lookupLanguage.
     */
    @RequestMapping(value = "/lookupLanguages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupLanguage> updateLookupLanguage(@Valid @RequestBody LookupLanguage lookupLanguage) throws URISyntaxException {
        log.debug("REST request to update LookupLanguage : {}", lookupLanguage);
        if (lookupLanguage.getId() == null) {
            return createLookupLanguage(lookupLanguage);
        }
        LookupLanguage result = lookupLanguageRepository.save(lookupLanguage);
        lookupLanguageSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lookupLanguage", lookupLanguage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lookupLanguages -> get all the lookupLanguages.
     */
    @RequestMapping(value = "/lookupLanguages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupLanguage> getAllLookupLanguages() {
        log.debug("REST request to get all LookupLanguages");
        return lookupLanguageRepository.findAll();
            }

    /**
     * GET  /lookupLanguages/:id -> get the "id" lookupLanguage.
     */
    @RequestMapping(value = "/lookupLanguages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupLanguage> getLookupLanguage(@PathVariable Long id) {
        log.debug("REST request to get LookupLanguage : {}", id);
        LookupLanguage lookupLanguage = lookupLanguageRepository.findOne(id);
        return Optional.ofNullable(lookupLanguage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lookupLanguages/:id -> delete the "id" lookupLanguage.
     */
    @RequestMapping(value = "/lookupLanguages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLookupLanguage(@PathVariable Long id) {
        log.debug("REST request to delete LookupLanguage : {}", id);
        lookupLanguageRepository.delete(id);
        lookupLanguageSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lookupLanguage", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lookupLanguages/:query -> search for the lookupLanguage corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/lookupLanguages/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupLanguage> searchLookupLanguages(@PathVariable String query) {
        log.debug("REST request to search LookupLanguages for query {}", query);
        return StreamSupport
            .stream(lookupLanguageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

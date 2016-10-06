package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.SupportedLanguage;
import com.engagepoint.msap.repository.SupportedLanguageRepository;
import com.engagepoint.msap.repository.search.SupportedLanguageSearchRepository;
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
 * REST controller for managing SupportedLanguage.
 */
@RestController
@RequestMapping("/api")
public class SupportedLanguageResource {

    private final Logger log = LoggerFactory.getLogger(SupportedLanguageResource.class);
        
    @Inject
    private SupportedLanguageRepository supportedLanguageRepository;
    
    @Inject
    private SupportedLanguageSearchRepository supportedLanguageSearchRepository;
    
    /**
     * POST  /supportedLanguages -> Create a new supportedLanguage.
     */
    @RequestMapping(value = "/supportedLanguages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupportedLanguage> createSupportedLanguage(@RequestBody SupportedLanguage supportedLanguage) throws URISyntaxException {
        log.debug("REST request to save SupportedLanguage : {}", supportedLanguage);
        if (supportedLanguage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("supportedLanguage", "idexists", "A new supportedLanguage cannot already have an ID")).body(null);
        }
        SupportedLanguage result = supportedLanguageRepository.save(supportedLanguage);
        supportedLanguageSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/supportedLanguages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("supportedLanguage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supportedLanguages -> Updates an existing supportedLanguage.
     */
    @RequestMapping(value = "/supportedLanguages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupportedLanguage> updateSupportedLanguage(@RequestBody SupportedLanguage supportedLanguage) throws URISyntaxException {
        log.debug("REST request to update SupportedLanguage : {}", supportedLanguage);
        if (supportedLanguage.getId() == null) {
            return createSupportedLanguage(supportedLanguage);
        }
        SupportedLanguage result = supportedLanguageRepository.save(supportedLanguage);
        supportedLanguageSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("supportedLanguage", supportedLanguage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supportedLanguages -> get all the supportedLanguages.
     */
    @RequestMapping(value = "/supportedLanguages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SupportedLanguage> getAllSupportedLanguages() {
        log.debug("REST request to get all SupportedLanguages");
        return supportedLanguageRepository.findAll();
            }

    /**
     * GET  /supportedLanguages/:id -> get the "id" supportedLanguage.
     */
    @RequestMapping(value = "/supportedLanguages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SupportedLanguage> getSupportedLanguage(@PathVariable Long id) {
        log.debug("REST request to get SupportedLanguage : {}", id);
        SupportedLanguage supportedLanguage = supportedLanguageRepository.findOne(id);
        return Optional.ofNullable(supportedLanguage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /supportedLanguages/:id -> delete the "id" supportedLanguage.
     */
    @RequestMapping(value = "/supportedLanguages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSupportedLanguage(@PathVariable Long id) {
        log.debug("REST request to delete SupportedLanguage : {}", id);
        supportedLanguageRepository.delete(id);
        supportedLanguageSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("supportedLanguage", id.toString())).build();
    }

    /**
     * SEARCH  /_search/supportedLanguages/:query -> search for the supportedLanguage corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/supportedLanguages/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SupportedLanguage> searchSupportedLanguages(@PathVariable String query) {
        log.debug("REST request to search SupportedLanguages for query {}", query);
        return StreamSupport
            .stream(supportedLanguageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

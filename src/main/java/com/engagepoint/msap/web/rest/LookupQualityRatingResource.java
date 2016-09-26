package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.LookupQualityRating;
import com.engagepoint.msap.repository.LookupQualityRatingRepository;
import com.engagepoint.msap.repository.search.LookupQualityRatingSearchRepository;
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
 * REST controller for managing LookupQualityRating.
 */
@RestController
@RequestMapping("/api")
public class LookupQualityRatingResource {

    private final Logger log = LoggerFactory.getLogger(LookupQualityRatingResource.class);
        
    @Inject
    private LookupQualityRatingRepository lookupQualityRatingRepository;
    
    @Inject
    private LookupQualityRatingSearchRepository lookupQualityRatingSearchRepository;
    
    /**
     * POST  /lookupQualityRatings -> Create a new lookupQualityRating.
     */
    @RequestMapping(value = "/lookupQualityRatings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupQualityRating> createLookupQualityRating(@Valid @RequestBody LookupQualityRating lookupQualityRating) throws URISyntaxException {
        log.debug("REST request to save LookupQualityRating : {}", lookupQualityRating);
        if (lookupQualityRating.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lookupQualityRating", "idexists", "A new lookupQualityRating cannot already have an ID")).body(null);
        }
        LookupQualityRating result = lookupQualityRatingRepository.save(lookupQualityRating);
        lookupQualityRatingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/lookupQualityRatings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lookupQualityRating", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lookupQualityRatings -> Updates an existing lookupQualityRating.
     */
    @RequestMapping(value = "/lookupQualityRatings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupQualityRating> updateLookupQualityRating(@Valid @RequestBody LookupQualityRating lookupQualityRating) throws URISyntaxException {
        log.debug("REST request to update LookupQualityRating : {}", lookupQualityRating);
        if (lookupQualityRating.getId() == null) {
            return createLookupQualityRating(lookupQualityRating);
        }
        LookupQualityRating result = lookupQualityRatingRepository.save(lookupQualityRating);
        lookupQualityRatingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lookupQualityRating", lookupQualityRating.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lookupQualityRatings -> get all the lookupQualityRatings.
     */
    @RequestMapping(value = "/lookupQualityRatings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupQualityRating> getAllLookupQualityRatings() {
        log.debug("REST request to get all LookupQualityRatings");
        return lookupQualityRatingRepository.findAll();
            }

    /**
     * GET  /lookupQualityRatings/:id -> get the "id" lookupQualityRating.
     */
    @RequestMapping(value = "/lookupQualityRatings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookupQualityRating> getLookupQualityRating(@PathVariable Long id) {
        log.debug("REST request to get LookupQualityRating : {}", id);
        LookupQualityRating lookupQualityRating = lookupQualityRatingRepository.findOne(id);
        return Optional.ofNullable(lookupQualityRating)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lookupQualityRatings/:id -> delete the "id" lookupQualityRating.
     */
    @RequestMapping(value = "/lookupQualityRatings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLookupQualityRating(@PathVariable Long id) {
        log.debug("REST request to delete LookupQualityRating : {}", id);
        lookupQualityRatingRepository.delete(id);
        lookupQualityRatingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lookupQualityRating", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lookupQualityRatings/:query -> search for the lookupQualityRating corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/lookupQualityRatings/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookupQualityRating> searchLookupQualityRatings(@PathVariable String query) {
        log.debug("REST request to search LookupQualityRatings for query {}", query);
        return StreamSupport
            .stream(lookupQualityRatingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

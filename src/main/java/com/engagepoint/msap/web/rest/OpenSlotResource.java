package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.OpenSlot;
import com.engagepoint.msap.repository.OpenSlotRepository;
import com.engagepoint.msap.repository.search.OpenSlotSearchRepository;
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
 * REST controller for managing OpenSlot.
 */
@RestController
@RequestMapping("/api")
public class OpenSlotResource {

    private final Logger log = LoggerFactory.getLogger(OpenSlotResource.class);
        
    @Inject
    private OpenSlotRepository openSlotRepository;
    
    @Inject
    private OpenSlotSearchRepository openSlotSearchRepository;
    
    /**
     * POST  /openSlots -> Create a new openSlot.
     */
    @RequestMapping(value = "/openSlots",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OpenSlot> createOpenSlot(@RequestBody OpenSlot openSlot) throws URISyntaxException {
        log.debug("REST request to save OpenSlot : {}", openSlot);
        if (openSlot.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("openSlot", "idexists", "A new openSlot cannot already have an ID")).body(null);
        }
        OpenSlot result = openSlotRepository.save(openSlot);
        openSlotSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/openSlots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("openSlot", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /openSlots -> Updates an existing openSlot.
     */
    @RequestMapping(value = "/openSlots",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OpenSlot> updateOpenSlot(@RequestBody OpenSlot openSlot) throws URISyntaxException {
        log.debug("REST request to update OpenSlot : {}", openSlot);
        if (openSlot.getId() == null) {
            return createOpenSlot(openSlot);
        }
        OpenSlot result = openSlotRepository.save(openSlot);
        openSlotSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("openSlot", openSlot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /openSlots -> get all the openSlots.
     */
    @RequestMapping(value = "/openSlots",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OpenSlot> getAllOpenSlots() {
        log.debug("REST request to get all OpenSlots");
        return openSlotRepository.findAll();
            }

    /**
     * GET  /openSlots/:id -> get the "id" openSlot.
     */
    @RequestMapping(value = "/openSlots/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OpenSlot> getOpenSlot(@PathVariable Long id) {
        log.debug("REST request to get OpenSlot : {}", id);
        OpenSlot openSlot = openSlotRepository.findOne(id);
        return Optional.ofNullable(openSlot)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /openSlots/:id -> delete the "id" openSlot.
     */
    @RequestMapping(value = "/openSlots/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOpenSlot(@PathVariable Long id) {
        log.debug("REST request to delete OpenSlot : {}", id);
        openSlotRepository.delete(id);
        openSlotSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("openSlot", id.toString())).build();
    }

    /**
     * SEARCH  /_search/openSlots/:query -> search for the openSlot corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/openSlots/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OpenSlot> searchOpenSlots(@PathVariable String query) {
        log.debug("REST request to search OpenSlots for query {}", query);
        return StreamSupport
            .stream(openSlotSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

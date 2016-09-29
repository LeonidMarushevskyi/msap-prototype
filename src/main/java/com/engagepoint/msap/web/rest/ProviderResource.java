package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.Provider;
import com.engagepoint.msap.repository.ProviderRepository;
import com.engagepoint.msap.repository.search.ProviderSearchRepository;
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
 * REST controller for managing Provider.
 */
@RestController
@RequestMapping("/api")
public class ProviderResource {

    private final Logger log = LoggerFactory.getLogger(ProviderResource.class);
        
    @Inject
    private ProviderRepository providerRepository;
    
    @Inject
    private ProviderSearchRepository providerSearchRepository;
    
    /**
     * POST  /providers -> Create a new provider.
     */
    @RequestMapping(value = "/providers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Provider> createProvider(@RequestBody Provider provider) throws URISyntaxException {
        log.debug("REST request to save Provider : {}", provider);
        if (provider.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("provider", "idexists", "A new provider cannot already have an ID")).body(null);
        }
        Provider result = providerRepository.save(provider);
        providerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("provider", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /providers -> Updates an existing provider.
     */
    @RequestMapping(value = "/providers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Provider> updateProvider(@RequestBody Provider provider) throws URISyntaxException {
        log.debug("REST request to update Provider : {}", provider);
        if (provider.getId() == null) {
            return createProvider(provider);
        }
        Provider result = providerRepository.save(provider);
        providerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("provider", provider.getId().toString()))
            .body(result);
    }

    /**
     * GET  /providers -> get all the providers.
     */
    @RequestMapping(value = "/providers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Provider> getAllProviders() {
        log.debug("REST request to get all Providers");
        return providerRepository.findAll();
            }

    /**
     * GET  /providers/:id -> get the "id" provider.
     */
    @RequestMapping(value = "/providers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Provider> getProvider(@PathVariable Long id) {
        log.debug("REST request to get Provider : {}", id);
        Provider provider = providerRepository.findOne(id);
        return Optional.ofNullable(provider)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /providers/:id -> delete the "id" provider.
     */
    @RequestMapping(value = "/providers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        log.debug("REST request to delete Provider : {}", id);
        providerRepository.delete(id);
        providerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("provider", id.toString())).build();
    }

    /**
     * SEARCH  /_search/providers/:query -> search for the provider corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/providers/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Provider> searchProviders(@PathVariable String query) {
        log.debug("REST request to search Providers for query {}", query);
        return StreamSupport
            .stream(providerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

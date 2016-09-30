package com.engagepoint.msap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.Schedule;
import com.engagepoint.msap.repository.ScheduleRepository;
import com.engagepoint.msap.repository.search.ScheduleSearchRepository;
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
 * REST controller for managing Schedule.
 */
@RestController
@RequestMapping("/api")
public class ScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleResource.class);
        
    @Inject
    private ScheduleRepository scheduleRepository;
    
    @Inject
    private ScheduleSearchRepository scheduleSearchRepository;
    
    /**
     * POST  /schedules -> Create a new schedule.
     */
    @RequestMapping(value = "/schedules",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) throws URISyntaxException {
        log.debug("REST request to save Schedule : {}", schedule);
        if (schedule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("schedule", "idexists", "A new schedule cannot already have an ID")).body(null);
        }
        Schedule result = scheduleRepository.save(schedule);
        scheduleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("schedule", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schedules -> Updates an existing schedule.
     */
    @RequestMapping(value = "/schedules",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule) throws URISyntaxException {
        log.debug("REST request to update Schedule : {}", schedule);
        if (schedule.getId() == null) {
            return createSchedule(schedule);
        }
        Schedule result = scheduleRepository.save(schedule);
        scheduleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("schedule", schedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schedules -> get all the schedules.
     */
    @RequestMapping(value = "/schedules",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Schedule> getAllSchedules() {
        log.debug("REST request to get all Schedules");
        return scheduleRepository.findAll();
            }

    /**
     * GET  /schedules/:id -> get the "id" schedule.
     */
    @RequestMapping(value = "/schedules/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Schedule> getSchedule(@PathVariable Long id) {
        log.debug("REST request to get Schedule : {}", id);
        Schedule schedule = scheduleRepository.findOne(id);
        return Optional.ofNullable(schedule)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /schedules/:id -> delete the "id" schedule.
     */
    @RequestMapping(value = "/schedules/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        log.debug("REST request to delete Schedule : {}", id);
        scheduleRepository.delete(id);
        scheduleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("schedule", id.toString())).build();
    }

    /**
     * SEARCH  /_search/schedules/:query -> search for the schedule corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/schedules/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Schedule> searchSchedules(@PathVariable String query) {
        log.debug("REST request to search Schedules for query {}", query);
        return StreamSupport
            .stream(scheduleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

package com.skt.test.web.rest;

import com.skt.test.repository.ProjectHistoryRepository;
import com.skt.test.service.ProjectHistoryService;
import com.skt.test.service.dto.ProjectHistoryDTO;
import com.skt.test.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.skt.test.domain.ProjectHistory}.
 */
@RestController
@RequestMapping("/api")
public class ProjectHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ProjectHistoryResource.class);

    private static final String ENTITY_NAME = "projectHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectHistoryService projectHistoryService;

    private final ProjectHistoryRepository projectHistoryRepository;

    public ProjectHistoryResource(ProjectHistoryService projectHistoryService, ProjectHistoryRepository projectHistoryRepository) {
        this.projectHistoryService = projectHistoryService;
        this.projectHistoryRepository = projectHistoryRepository;
    }

    /**
     * {@code POST  /project-histories} : Create a new projectHistory.
     *
     * @param projectHistoryDTO the projectHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectHistoryDTO, or with status {@code 400 (Bad Request)} if the projectHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-histories")
    public ResponseEntity<ProjectHistoryDTO> createProjectHistory(@Valid @RequestBody ProjectHistoryDTO projectHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectHistory : {}", projectHistoryDTO);
        if (projectHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectHistoryDTO result = projectHistoryService.save(projectHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/project-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-histories/:id} : Updates an existing projectHistory.
     *
     * @param id the id of the projectHistoryDTO to save.
     * @param projectHistoryDTO the projectHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the projectHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-histories/{id}")
    public ResponseEntity<ProjectHistoryDTO> updateProjectHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectHistoryDTO projectHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectHistory : {}, {}", id, projectHistoryDTO);
        if (projectHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectHistoryDTO result = projectHistoryService.save(projectHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-histories/:id} : Partial updates given fields of an existing projectHistory, field will ignore if it is null
     *
     * @param id the id of the projectHistoryDTO to save.
     * @param projectHistoryDTO the projectHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the projectHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-histories/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProjectHistoryDTO> partialUpdateProjectHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectHistoryDTO projectHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectHistory partially : {}, {}", id, projectHistoryDTO);
        if (projectHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectHistoryDTO> result = projectHistoryService.partialUpdate(projectHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-histories} : get all the projectHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectHistories in body.
     */
    @GetMapping("/project-histories")
    public ResponseEntity<List<ProjectHistoryDTO>> getAllProjectHistories(Pageable pageable) {
        log.debug("REST request to get a page of ProjectHistories");
        Page<ProjectHistoryDTO> page = projectHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-histories/:id} : get the "id" projectHistory.
     *
     * @param id the id of the projectHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-histories/{id}")
    public ResponseEntity<ProjectHistoryDTO> getProjectHistory(@PathVariable Long id) {
        log.debug("REST request to get ProjectHistory : {}", id);
        Optional<ProjectHistoryDTO> projectHistoryDTO = projectHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectHistoryDTO);
    }

    /**
     * {@code DELETE  /project-histories/:id} : delete the "id" projectHistory.
     *
     * @param id the id of the projectHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-histories/{id}")
    public ResponseEntity<Void> deleteProjectHistory(@PathVariable Long id) {
        log.debug("REST request to delete ProjectHistory : {}", id);
        projectHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

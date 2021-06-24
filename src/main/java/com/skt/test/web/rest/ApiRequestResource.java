package com.skt.test.web.rest;

import com.skt.test.repository.ApiRequestRepository;
import com.skt.test.service.ApiRequestService;
import com.skt.test.service.dto.ApiRequestDTO;
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
 * REST controller for managing {@link com.skt.test.domain.ApiRequest}.
 */
@RestController
@RequestMapping("/api")
public class ApiRequestResource {

    private final Logger log = LoggerFactory.getLogger(ApiRequestResource.class);

    private static final String ENTITY_NAME = "apiRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiRequestService apiRequestService;

    private final ApiRequestRepository apiRequestRepository;

    public ApiRequestResource(ApiRequestService apiRequestService, ApiRequestRepository apiRequestRepository) {
        this.apiRequestService = apiRequestService;
        this.apiRequestRepository = apiRequestRepository;
    }

    /**
     * {@code POST  /api-requests} : Create a new apiRequest.
     *
     * @param apiRequestDTO the apiRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiRequestDTO, or with status {@code 400 (Bad Request)} if the apiRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-requests")
    public ResponseEntity<ApiRequestDTO> createApiRequest(@Valid @RequestBody ApiRequestDTO apiRequestDTO) throws URISyntaxException {
        log.debug("REST request to save ApiRequest : {}", apiRequestDTO);
        if (apiRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiRequestDTO result = apiRequestService.save(apiRequestDTO);
        return ResponseEntity
            .created(new URI("/api/api-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-requests/:id} : Updates an existing apiRequest.
     *
     * @param id the id of the apiRequestDTO to save.
     * @param apiRequestDTO the apiRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiRequestDTO,
     * or with status {@code 400 (Bad Request)} if the apiRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-requests/{id}")
    public ResponseEntity<ApiRequestDTO> updateApiRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApiRequestDTO apiRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApiRequest : {}, {}", id, apiRequestDTO);
        if (apiRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApiRequestDTO result = apiRequestService.save(apiRequestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /api-requests/:id} : Partial updates given fields of an existing apiRequest, field will ignore if it is null
     *
     * @param id the id of the apiRequestDTO to save.
     * @param apiRequestDTO the apiRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiRequestDTO,
     * or with status {@code 400 (Bad Request)} if the apiRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the apiRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the apiRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/api-requests/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ApiRequestDTO> partialUpdateApiRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApiRequestDTO apiRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApiRequest partially : {}, {}", id, apiRequestDTO);
        if (apiRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApiRequestDTO> result = apiRequestService.partialUpdate(apiRequestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiRequestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /api-requests} : get all the apiRequests.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiRequests in body.
     */
    @GetMapping("/api-requests")
    public ResponseEntity<List<ApiRequestDTO>> getAllApiRequests(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of ApiRequests");
        Page<ApiRequestDTO> page;
        if (eagerload) {
            page = apiRequestService.findAllWithEagerRelationships(pageable);
        } else {
            page = apiRequestService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /api-requests/:id} : get the "id" apiRequest.
     *
     * @param id the id of the apiRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-requests/{id}")
    public ResponseEntity<ApiRequestDTO> getApiRequest(@PathVariable Long id) {
        log.debug("REST request to get ApiRequest : {}", id);
        Optional<ApiRequestDTO> apiRequestDTO = apiRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiRequestDTO);
    }

    /**
     * {@code DELETE  /api-requests/:id} : delete the "id" apiRequest.
     *
     * @param id the id of the apiRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-requests/{id}")
    public ResponseEntity<Void> deleteApiRequest(@PathVariable Long id) {
        log.debug("REST request to delete ApiRequest : {}", id);
        apiRequestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

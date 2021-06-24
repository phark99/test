package com.skt.test.web.rest;

import com.skt.test.repository.ApiMetaRepository;
import com.skt.test.service.ApiMetaService;
import com.skt.test.service.dto.ApiMetaDTO;
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
 * REST controller for managing {@link com.skt.test.domain.ApiMeta}.
 */
@RestController
@RequestMapping("/api")
public class ApiMetaResource {

    private final Logger log = LoggerFactory.getLogger(ApiMetaResource.class);

    private static final String ENTITY_NAME = "apiMeta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiMetaService apiMetaService;

    private final ApiMetaRepository apiMetaRepository;

    public ApiMetaResource(ApiMetaService apiMetaService, ApiMetaRepository apiMetaRepository) {
        this.apiMetaService = apiMetaService;
        this.apiMetaRepository = apiMetaRepository;
    }

    /**
     * {@code POST  /api-metas} : Create a new apiMeta.
     *
     * @param apiMetaDTO the apiMetaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiMetaDTO, or with status {@code 400 (Bad Request)} if the apiMeta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-metas")
    public ResponseEntity<ApiMetaDTO> createApiMeta(@Valid @RequestBody ApiMetaDTO apiMetaDTO) throws URISyntaxException {
        log.debug("REST request to save ApiMeta : {}", apiMetaDTO);
        if (apiMetaDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiMeta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiMetaDTO result = apiMetaService.save(apiMetaDTO);
        return ResponseEntity
            .created(new URI("/api/api-metas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-metas/:id} : Updates an existing apiMeta.
     *
     * @param id the id of the apiMetaDTO to save.
     * @param apiMetaDTO the apiMetaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiMetaDTO,
     * or with status {@code 400 (Bad Request)} if the apiMetaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiMetaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-metas/{id}")
    public ResponseEntity<ApiMetaDTO> updateApiMeta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApiMetaDTO apiMetaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApiMeta : {}, {}", id, apiMetaDTO);
        if (apiMetaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiMetaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiMetaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApiMetaDTO result = apiMetaService.save(apiMetaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiMetaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /api-metas/:id} : Partial updates given fields of an existing apiMeta, field will ignore if it is null
     *
     * @param id the id of the apiMetaDTO to save.
     * @param apiMetaDTO the apiMetaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiMetaDTO,
     * or with status {@code 400 (Bad Request)} if the apiMetaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the apiMetaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the apiMetaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/api-metas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ApiMetaDTO> partialUpdateApiMeta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApiMetaDTO apiMetaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApiMeta partially : {}, {}", id, apiMetaDTO);
        if (apiMetaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiMetaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiMetaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApiMetaDTO> result = apiMetaService.partialUpdate(apiMetaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiMetaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /api-metas} : get all the apiMetas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiMetas in body.
     */
    @GetMapping("/api-metas")
    public ResponseEntity<List<ApiMetaDTO>> getAllApiMetas(Pageable pageable) {
        log.debug("REST request to get a page of ApiMetas");
        Page<ApiMetaDTO> page = apiMetaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /api-metas/:id} : get the "id" apiMeta.
     *
     * @param id the id of the apiMetaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiMetaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-metas/{id}")
    public ResponseEntity<ApiMetaDTO> getApiMeta(@PathVariable Long id) {
        log.debug("REST request to get ApiMeta : {}", id);
        Optional<ApiMetaDTO> apiMetaDTO = apiMetaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiMetaDTO);
    }

    /**
     * {@code DELETE  /api-metas/:id} : delete the "id" apiMeta.
     *
     * @param id the id of the apiMetaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-metas/{id}")
    public ResponseEntity<Void> deleteApiMeta(@PathVariable Long id) {
        log.debug("REST request to delete ApiMeta : {}", id);
        apiMetaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

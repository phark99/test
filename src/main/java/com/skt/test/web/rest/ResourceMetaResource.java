package com.skt.test.web.rest;

import com.skt.test.repository.ResourceMetaRepository;
import com.skt.test.service.ResourceMetaService;
import com.skt.test.service.dto.ResourceMetaDTO;
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
 * REST controller for managing {@link com.skt.test.domain.ResourceMeta}.
 */
@RestController
@RequestMapping("/api")
public class ResourceMetaResource {

    private final Logger log = LoggerFactory.getLogger(ResourceMetaResource.class);

    private static final String ENTITY_NAME = "resourceMeta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResourceMetaService resourceMetaService;

    private final ResourceMetaRepository resourceMetaRepository;

    public ResourceMetaResource(ResourceMetaService resourceMetaService, ResourceMetaRepository resourceMetaRepository) {
        this.resourceMetaService = resourceMetaService;
        this.resourceMetaRepository = resourceMetaRepository;
    }

    /**
     * {@code POST  /resource-metas} : Create a new resourceMeta.
     *
     * @param resourceMetaDTO the resourceMetaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resourceMetaDTO, or with status {@code 400 (Bad Request)} if the resourceMeta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resource-metas")
    public ResponseEntity<ResourceMetaDTO> createResourceMeta(@Valid @RequestBody ResourceMetaDTO resourceMetaDTO)
        throws URISyntaxException {
        log.debug("REST request to save ResourceMeta : {}", resourceMetaDTO);
        if (resourceMetaDTO.getId() != null) {
            throw new BadRequestAlertException("A new resourceMeta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResourceMetaDTO result = resourceMetaService.save(resourceMetaDTO);
        return ResponseEntity
            .created(new URI("/api/resource-metas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resource-metas/:id} : Updates an existing resourceMeta.
     *
     * @param id the id of the resourceMetaDTO to save.
     * @param resourceMetaDTO the resourceMetaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resourceMetaDTO,
     * or with status {@code 400 (Bad Request)} if the resourceMetaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resourceMetaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resource-metas/{id}")
    public ResponseEntity<ResourceMetaDTO> updateResourceMeta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResourceMetaDTO resourceMetaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResourceMeta : {}, {}", id, resourceMetaDTO);
        if (resourceMetaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resourceMetaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resourceMetaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResourceMetaDTO result = resourceMetaService.save(resourceMetaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resourceMetaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resource-metas/:id} : Partial updates given fields of an existing resourceMeta, field will ignore if it is null
     *
     * @param id the id of the resourceMetaDTO to save.
     * @param resourceMetaDTO the resourceMetaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resourceMetaDTO,
     * or with status {@code 400 (Bad Request)} if the resourceMetaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the resourceMetaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the resourceMetaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resource-metas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ResourceMetaDTO> partialUpdateResourceMeta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResourceMetaDTO resourceMetaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResourceMeta partially : {}, {}", id, resourceMetaDTO);
        if (resourceMetaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resourceMetaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resourceMetaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResourceMetaDTO> result = resourceMetaService.partialUpdate(resourceMetaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resourceMetaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /resource-metas} : get all the resourceMetas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resourceMetas in body.
     */
    @GetMapping("/resource-metas")
    public ResponseEntity<List<ResourceMetaDTO>> getAllResourceMetas(Pageable pageable) {
        log.debug("REST request to get a page of ResourceMetas");
        Page<ResourceMetaDTO> page = resourceMetaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resource-metas/:id} : get the "id" resourceMeta.
     *
     * @param id the id of the resourceMetaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resourceMetaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resource-metas/{id}")
    public ResponseEntity<ResourceMetaDTO> getResourceMeta(@PathVariable Long id) {
        log.debug("REST request to get ResourceMeta : {}", id);
        Optional<ResourceMetaDTO> resourceMetaDTO = resourceMetaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resourceMetaDTO);
    }

    /**
     * {@code DELETE  /resource-metas/:id} : delete the "id" resourceMeta.
     *
     * @param id the id of the resourceMetaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resource-metas/{id}")
    public ResponseEntity<Void> deleteResourceMeta(@PathVariable Long id) {
        log.debug("REST request to delete ResourceMeta : {}", id);
        resourceMetaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.skt.test.web.rest;

import com.skt.test.repository.LoginHistoryRepository;
import com.skt.test.service.LoginHistoryService;
import com.skt.test.service.dto.LoginHistoryDTO;
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
 * REST controller for managing {@link com.skt.test.domain.LoginHistory}.
 */
@RestController
@RequestMapping("/api")
public class LoginHistoryResource {

    private final Logger log = LoggerFactory.getLogger(LoginHistoryResource.class);

    private static final String ENTITY_NAME = "loginHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoginHistoryService loginHistoryService;

    private final LoginHistoryRepository loginHistoryRepository;

    public LoginHistoryResource(LoginHistoryService loginHistoryService, LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryService = loginHistoryService;
        this.loginHistoryRepository = loginHistoryRepository;
    }

    /**
     * {@code POST  /login-histories} : Create a new loginHistory.
     *
     * @param loginHistoryDTO the loginHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loginHistoryDTO, or with status {@code 400 (Bad Request)} if the loginHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/login-histories")
    public ResponseEntity<LoginHistoryDTO> createLoginHistory(@Valid @RequestBody LoginHistoryDTO loginHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save LoginHistory : {}", loginHistoryDTO);
        if (loginHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new loginHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoginHistoryDTO result = loginHistoryService.save(loginHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/login-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /login-histories/:id} : Updates an existing loginHistory.
     *
     * @param id the id of the loginHistoryDTO to save.
     * @param loginHistoryDTO the loginHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loginHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the loginHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loginHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/login-histories/{id}")
    public ResponseEntity<LoginHistoryDTO> updateLoginHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LoginHistoryDTO loginHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LoginHistory : {}, {}", id, loginHistoryDTO);
        if (loginHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loginHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loginHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoginHistoryDTO result = loginHistoryService.save(loginHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loginHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /login-histories/:id} : Partial updates given fields of an existing loginHistory, field will ignore if it is null
     *
     * @param id the id of the loginHistoryDTO to save.
     * @param loginHistoryDTO the loginHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loginHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the loginHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the loginHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the loginHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/login-histories/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LoginHistoryDTO> partialUpdateLoginHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LoginHistoryDTO loginHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoginHistory partially : {}, {}", id, loginHistoryDTO);
        if (loginHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loginHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loginHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoginHistoryDTO> result = loginHistoryService.partialUpdate(loginHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loginHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /login-histories} : get all the loginHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loginHistories in body.
     */
    @GetMapping("/login-histories")
    public ResponseEntity<List<LoginHistoryDTO>> getAllLoginHistories(Pageable pageable) {
        log.debug("REST request to get a page of LoginHistories");
        Page<LoginHistoryDTO> page = loginHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /login-histories/:id} : get the "id" loginHistory.
     *
     * @param id the id of the loginHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loginHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/login-histories/{id}")
    public ResponseEntity<LoginHistoryDTO> getLoginHistory(@PathVariable Long id) {
        log.debug("REST request to get LoginHistory : {}", id);
        Optional<LoginHistoryDTO> loginHistoryDTO = loginHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loginHistoryDTO);
    }

    /**
     * {@code DELETE  /login-histories/:id} : delete the "id" loginHistory.
     *
     * @param id the id of the loginHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/login-histories/{id}")
    public ResponseEntity<Void> deleteLoginHistory(@PathVariable Long id) {
        log.debug("REST request to delete LoginHistory : {}", id);
        loginHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.skt.test.web.rest;

import com.skt.test.repository.UserTokenRepository;
import com.skt.test.service.UserTokenService;
import com.skt.test.service.dto.UserTokenDTO;
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
 * REST controller for managing {@link com.skt.test.domain.UserToken}.
 */
@RestController
@RequestMapping("/api")
public class UserTokenResource {

    private final Logger log = LoggerFactory.getLogger(UserTokenResource.class);

    private static final String ENTITY_NAME = "userToken";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserTokenService userTokenService;

    private final UserTokenRepository userTokenRepository;

    public UserTokenResource(UserTokenService userTokenService, UserTokenRepository userTokenRepository) {
        this.userTokenService = userTokenService;
        this.userTokenRepository = userTokenRepository;
    }

    /**
     * {@code POST  /user-tokens} : Create a new userToken.
     *
     * @param userTokenDTO the userTokenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userTokenDTO, or with status {@code 400 (Bad Request)} if the userToken has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-tokens")
    public ResponseEntity<UserTokenDTO> createUserToken(@Valid @RequestBody UserTokenDTO userTokenDTO) throws URISyntaxException {
        log.debug("REST request to save UserToken : {}", userTokenDTO);
        if (userTokenDTO.getId() != null) {
            throw new BadRequestAlertException("A new userToken cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserTokenDTO result = userTokenService.save(userTokenDTO);
        return ResponseEntity
            .created(new URI("/api/user-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-tokens/:id} : Updates an existing userToken.
     *
     * @param id the id of the userTokenDTO to save.
     * @param userTokenDTO the userTokenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTokenDTO,
     * or with status {@code 400 (Bad Request)} if the userTokenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userTokenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-tokens/{id}")
    public ResponseEntity<UserTokenDTO> updateUserToken(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserTokenDTO userTokenDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserToken : {}, {}", id, userTokenDTO);
        if (userTokenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTokenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTokenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserTokenDTO result = userTokenService.save(userTokenDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userTokenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-tokens/:id} : Partial updates given fields of an existing userToken, field will ignore if it is null
     *
     * @param id the id of the userTokenDTO to save.
     * @param userTokenDTO the userTokenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTokenDTO,
     * or with status {@code 400 (Bad Request)} if the userTokenDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userTokenDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userTokenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-tokens/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<UserTokenDTO> partialUpdateUserToken(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserTokenDTO userTokenDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserToken partially : {}, {}", id, userTokenDTO);
        if (userTokenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTokenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTokenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserTokenDTO> result = userTokenService.partialUpdate(userTokenDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userTokenDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-tokens} : get all the userTokens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userTokens in body.
     */
    @GetMapping("/user-tokens")
    public ResponseEntity<List<UserTokenDTO>> getAllUserTokens(Pageable pageable) {
        log.debug("REST request to get a page of UserTokens");
        Page<UserTokenDTO> page = userTokenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-tokens/:id} : get the "id" userToken.
     *
     * @param id the id of the userTokenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userTokenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-tokens/{id}")
    public ResponseEntity<UserTokenDTO> getUserToken(@PathVariable Long id) {
        log.debug("REST request to get UserToken : {}", id);
        Optional<UserTokenDTO> userTokenDTO = userTokenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userTokenDTO);
    }

    /**
     * {@code DELETE  /user-tokens/:id} : delete the "id" userToken.
     *
     * @param id the id of the userTokenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-tokens/{id}")
    public ResponseEntity<Void> deleteUserToken(@PathVariable Long id) {
        log.debug("REST request to delete UserToken : {}", id);
        userTokenService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

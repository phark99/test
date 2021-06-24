package com.skt.test.service;

import com.skt.test.service.dto.UserTokenDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.skt.test.domain.UserToken}.
 */
public interface UserTokenService {
    /**
     * Save a userToken.
     *
     * @param userTokenDTO the entity to save.
     * @return the persisted entity.
     */
    UserTokenDTO save(UserTokenDTO userTokenDTO);

    /**
     * Partially updates a userToken.
     *
     * @param userTokenDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserTokenDTO> partialUpdate(UserTokenDTO userTokenDTO);

    /**
     * Get all the userTokens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserTokenDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userToken.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserTokenDTO> findOne(Long id);

    /**
     * Delete the "id" userToken.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

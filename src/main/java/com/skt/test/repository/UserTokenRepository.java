package com.skt.test.repository;

import com.skt.test.domain.UserToken;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserToken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {}

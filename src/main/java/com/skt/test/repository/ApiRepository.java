package com.skt.test.repository;

import com.skt.test.domain.Api;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Api entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiRepository extends JpaRepository<Api, Long> {}

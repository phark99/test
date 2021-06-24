package com.skt.test.repository;

import com.skt.test.domain.ApiMeta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApiMeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiMetaRepository extends JpaRepository<ApiMeta, Long> {}

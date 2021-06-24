package com.skt.test.repository;

import com.skt.test.domain.ResourceMeta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResourceMeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceMetaRepository extends JpaRepository<ResourceMeta, Long> {}

package com.skt.test.repository;

import com.skt.test.domain.ApiRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApiRequest entity.
 */
@Repository
public interface ApiRequestRepository extends JpaRepository<ApiRequest, Long> {
    @Query(
        value = "select distinct apiRequest from ApiRequest apiRequest left join fetch apiRequest.apis",
        countQuery = "select count(distinct apiRequest) from ApiRequest apiRequest"
    )
    Page<ApiRequest> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct apiRequest from ApiRequest apiRequest left join fetch apiRequest.apis")
    List<ApiRequest> findAllWithEagerRelationships();

    @Query("select apiRequest from ApiRequest apiRequest left join fetch apiRequest.apis where apiRequest.id =:id")
    Optional<ApiRequest> findOneWithEagerRelationships(@Param("id") Long id);
}

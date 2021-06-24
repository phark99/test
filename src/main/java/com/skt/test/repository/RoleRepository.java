package com.skt.test.repository;

import com.skt.test.domain.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Role entity.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(
        value = "select distinct role from Role role left join fetch role.users left join fetch role.resources",
        countQuery = "select count(distinct role) from Role role"
    )
    Page<Role> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct role from Role role left join fetch role.users left join fetch role.resources")
    List<Role> findAllWithEagerRelationships();

    @Query("select role from Role role left join fetch role.users left join fetch role.resources where role.id =:id")
    Optional<Role> findOneWithEagerRelationships(@Param("id") Long id);
}

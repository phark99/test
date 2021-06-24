package com.skt.test.repository;

import com.skt.test.domain.Menu;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Menu entity.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query(
        value = "select distinct menu from Menu menu left join fetch menu.roles",
        countQuery = "select count(distinct menu) from Menu menu"
    )
    Page<Menu> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct menu from Menu menu left join fetch menu.roles")
    List<Menu> findAllWithEagerRelationships();

    @Query("select menu from Menu menu left join fetch menu.roles where menu.id =:id")
    Optional<Menu> findOneWithEagerRelationships(@Param("id") Long id);
}

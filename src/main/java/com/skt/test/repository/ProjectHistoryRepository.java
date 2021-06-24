package com.skt.test.repository;

import com.skt.test.domain.ProjectHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectHistoryRepository extends JpaRepository<ProjectHistory, Long> {}

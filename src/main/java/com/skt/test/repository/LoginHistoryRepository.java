package com.skt.test.repository;

import com.skt.test.domain.LoginHistory;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LoginHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    @Query("select loginHistory from LoginHistory loginHistory where loginHistory.user.login = ?#{principal.username}")
    List<LoginHistory> findByUserIsCurrentUser();
}

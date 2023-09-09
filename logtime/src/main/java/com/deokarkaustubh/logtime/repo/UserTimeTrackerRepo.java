package com.deokarkaustubh.logtime.repo;

import com.deokarkaustubh.logtime.model.User;
import com.deokarkaustubh.logtime.model.UserTimeTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserTimeTrackerRepo extends JpaRepository<UserTimeTracker, Long> {

    Optional<UserTimeTracker> findByUserIdAndLocalDate(User user, LocalDate localDate);
}

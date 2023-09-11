package com.deokarkaustubh.database.repo;

import com.deokarkaustubh.database.model.User;
import com.deokarkaustubh.database.model.UserTimeTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserTimeTrackerRepo extends JpaRepository<UserTimeTracker, Long> {

    Optional<UserTimeTracker> findByUserIdAndLocalDate(User user, LocalDate localDate);
}

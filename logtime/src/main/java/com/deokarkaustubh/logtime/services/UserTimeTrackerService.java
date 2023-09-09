package com.deokarkaustubh.logtime.services;

import com.deokarkaustubh.logtime.exception.CustomException;
import com.deokarkaustubh.logtime.model.User;
import com.deokarkaustubh.logtime.model.UserTimeTracker;
import com.deokarkaustubh.logtime.repo.UserTimeTrackerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserTimeTrackerService {

    @Autowired
    UserTimeTrackerRepo userTimeTrackerRepo;

    public void addTimeTracking(String description, User user) {

        UserTimeTracker timeTracker = new UserTimeTracker(
                Instant.now(), Instant.now().plusSeconds(1), LocalDate.now(),
                description, user);

        userTimeTrackerRepo.save(timeTracker);

    }

    public void updateTimeTracking(String description, User user) throws CustomException {
        Optional<UserTimeTracker> byUserIdAndLocalDate = userTimeTrackerRepo.findByUserIdAndLocalDate(user, LocalDate.now());
        if (byUserIdAndLocalDate.isEmpty()) {
            throw new CustomException("Could not find time tracking");
        }
        UserTimeTracker timeTracker = byUserIdAndLocalDate.get();
        timeTracker.setEndTime(Instant.now());
        timeTracker.setWorkDescription(description);
        userTimeTrackerRepo.save(timeTracker);
    }
}

package com.example.performance_management.repo;

import com.example.performance_management.entity.timesheet.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends MongoRepository<Attendance, Long> {
    Optional<Attendance> findByUsernameAndDate(String username, String date);


}

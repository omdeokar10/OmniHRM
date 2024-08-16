package com.example.performance_management.repo;

import com.example.performance_management.entity.timesheet.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends MongoRepository<Task, Long> {

    Optional<List<Task>> getTaskByUsernameAndCreatedDate(String username, String createdDate);

}

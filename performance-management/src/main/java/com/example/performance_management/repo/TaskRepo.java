package com.example.performance_management.repo;

import com.example.performance_management.entity.timesheet.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepo extends MongoRepository<Task, Long> {


}

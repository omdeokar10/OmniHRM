package com.example.performance_management.service.timesheet;

import com.example.performance_management.dto.timesheet.TaskEntryDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.timesheet.Task;
import com.example.performance_management.mapper.TaskMapper;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.TaskRepo;
import org.springframework.stereotype.Service;

@Service
public class TimesheetService {

    final private EmployeeSequenceGeneratorService employeeSequenceGeneratorService;
    private final TaskMapper taskMapper = new TaskMapper();
    private final TaskRepo taskRepo;

    public TimesheetService(EmployeeSequenceGeneratorService employeeSequenceGeneratorService, TaskRepo taskRepo) {
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
        this.taskRepo = taskRepo;
    }

    public void addTaskForUser(TaskEntryDto taskEntryDto) {
        Task task = taskMapper.convertToTask(taskEntryDto);
        task.setId(generateTaskId());
        taskRepo.save(task);
    }

    private Long generateTaskId() {
        return employeeSequenceGeneratorService.getTaskSequenceNumber
                (Employee.ID_KEY, Employee.ID_VAL, Task.GENERATED_ID);
    }
}

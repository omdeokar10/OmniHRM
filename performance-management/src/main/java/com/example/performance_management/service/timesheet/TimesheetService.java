package com.example.performance_management.service.timesheet;

import com.example.performance_management.dto.timesheet.TaskEntryDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.timesheet.Task;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.TaskMapper;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.TaskRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Task task = taskMapper.convertToTask(taskEntryDto, true);
        task.setId(generateTaskId());
        taskRepo.save(task);
    }

    private Long generateTaskId() {
        return employeeSequenceGeneratorService.getTaskSequenceNumber
                (Employee.ID_KEY, Employee.ID_VAL, Task.GENERATED_ID);
    }

    public void deleteTask(Long taskId) {
        taskRepo.deleteById(taskId);
    }

    public void updateTaskForUser(TaskEntryDto taskEntryDto) {
        Task task = taskMapper.convertToTask(taskEntryDto, false);
        task.setId(taskEntryDto.getId());
        taskRepo.save(task);
    }

    public List<TaskEntryDto> getAllTaskForUserForDate(String username, String dateString) {
        List<Task> task = getTask(taskRepo.getTaskByUsernameAndCreatedDate(username, dateString));
        List<TaskEntryDto> taskEntryDtos = task.stream().map(taskMapper::convertToDto).toList();
        return taskEntryDtos;
    }

    public List<TaskEntryDto> getAllTaskForUserForDate(String username, String startDateStr, String endDateStr)
    {
        List<TaskEntryDto> tasks = new ArrayList<>();
        for (LocalDate date = parse(startDateStr); date.isBefore(parse(endDateStr)); date = date.plusDays(1)) {
            tasks.addAll(getAllTaskForUserForDate(username, date.toString()));
        }

        LocalDate endDate = parse(endDateStr);
        tasks.addAll(getAllTaskForUserForDate(username, endDate.toString()));
        return tasks;
    }

    private LocalDate parse(String startDate) {
        return LocalDate.parse(startDate);
    }

    public <T> T getTask(Optional<T> optionalTask) {
        if (optionalTask.isEmpty()) {
            throw new CustomException("Task is not present");
        }
        return optionalTask.get();
    }
}

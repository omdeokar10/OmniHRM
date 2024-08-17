package com.example.performance_management.mapper;

import com.example.performance_management.dto.timesheet.TaskEntryDto;
import com.example.performance_management.entity.timesheet.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskMapper {
    public TaskMapper() {
    }

    public Task convertToTask(TaskEntryDto taskEntryDto, boolean bCreating) {
        Task task = new Task();
        task.setDescription(taskEntryDto.getDescription());
        task.setUsername(taskEntryDto.getUsername());
        task.setDurationLogged(taskEntryDto.getDurationLogged());

        if (!taskEntryDto.getCreatedDate().isBlank()) {
            task.setCreatedDate(taskEntryDto.getCreatedDate());
        } else {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDateTime = now.format(formatter);

            if (bCreating) {
                task.setCreatedDate(formattedDateTime);
                task.setLastUpdatedAt(formattedDateTime);
            } else {
                task.setLastUpdatedAt(formattedDateTime);
            }

        }


        return task;
    }

    public TaskEntryDto convertToDto(Task task) {
        TaskEntryDto taskEntryDto = new TaskEntryDto();
        taskEntryDto.setId(task.getId());
        taskEntryDto.setDescription(task.getDescription());
        taskEntryDto.setUsername(task.getUsername());
        taskEntryDto.setDurationLogged(task.getDurationLogged());
        taskEntryDto.setCreatedDate(task.getCreatedDate());
        return taskEntryDto;
    }

}

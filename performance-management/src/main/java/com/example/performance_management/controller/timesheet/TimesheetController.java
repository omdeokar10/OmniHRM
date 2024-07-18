package com.example.performance_management.controller.timesheet;


import com.example.performance_management.dto.timesheet.TaskEntryDto;
import com.example.performance_management.service.timesheet.TimesheetService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timesheet")
@CrossOrigin("*")
public class TimesheetController {

    private final TimesheetService timesheetService;

    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @PostMapping("/add")
    public void addTaskForUser(@RequestBody TaskEntryDto taskEntryDto){ // task description, user, time logged, start time, end time, date & logged at (now)
        timesheetService.addTaskForUser(taskEntryDto);
    }

    public void startTaskForUser(){ // task description, user, start time(now)

    }

    public void endTaskForUser(){ // task description, user, end time

    }

    public void calculateOvertime(){ // calculate if above x hours.

    }

    public void deleteTaskForUser(){ //task id.

    }

    public void updateTaskForUser(){ // task description, user, time logged, start time, end time, date & logged at (now)

    }




}

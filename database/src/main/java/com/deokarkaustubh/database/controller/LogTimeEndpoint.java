package com.deokarkaustubh.database.controller;

import com.deokarkaustubh.database.config.JacksonConfig;
import com.deokarkaustubh.database.exception.CustomException;
import com.deokarkaustubh.database.model.User;
import com.deokarkaustubh.database.model.UserTimeTracker;
import com.deokarkaustubh.database.repo.UserRepo;
import com.deokarkaustubh.database.repo.UserTimeTrackerRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/microservice/database/logtime")
public class LogTimeEndpoint {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserTimeTrackerRepo userTimeTrackerRepo;
    @Autowired
    JacksonConfig objectMapper;
    @PostMapping("/startime")
    public String saveStartTrackingTime(@RequestBody String string) throws JsonProcessingException {
        System.out.println("Start tracking time");
        UserTimeTracker userTimeTracker = saveTimeTracking(string);
        return "start time tracked" + userTimeTracker.toString();
    }

    private UserTimeTracker saveTimeTracking(String string) throws JsonProcessingException {
        UserTimeTracker userTimeTracker = objectMapper.objectMapper().readValue(string, UserTimeTracker.class);
        userTimeTrackerRepo.save(userTimeTracker);
        return userTimeTracker;
    }

    @PostMapping("/endtime")
    public String saveEndTrackingTime(@RequestBody String string) throws JsonProcessingException {
        System.out.println("Tracking time for today completion started.");
        UserTimeTracker userTimeTracker = saveTimeTracking(string);
        return "end time tracked" + userTimeTracker.toString();
    }

    @GetMapping("/fetchbydate/{date}/anduserid/{userid}")
    public UserTimeTracker getTrackTime(@PathVariable String date, @PathVariable String userid) throws CustomException {
        System.out.println("fetch tracked time.");
        Optional<User> userId = userRepo.findById(Long.parseLong(userid));
        if (userId.isEmpty()) {
            throw new CustomException("user not present");
        }
        LocalDate localDate = LocalDate.parse(date);

        Optional<UserTimeTracker> optionalUserTimeTracker = userTimeTrackerRepo.findByUserIdAndLocalDate(userId.get(), localDate);
        if (optionalUserTimeTracker.isEmpty()) {
            throw new CustomException("time tracker not present");
        }
        return optionalUserTimeTracker.get();
    }

}

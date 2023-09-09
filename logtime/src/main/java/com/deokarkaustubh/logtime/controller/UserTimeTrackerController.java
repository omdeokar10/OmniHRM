package com.deokarkaustubh.logtime.controller;

import com.deokarkaustubh.logtime.dto.UserTimeTrackerDto;
import com.deokarkaustubh.logtime.exception.CustomException;
import com.deokarkaustubh.logtime.model.User;
import com.deokarkaustubh.logtime.repo.UserRepo;
import com.deokarkaustubh.logtime.services.UserTimeTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tracktime")
public class UserTimeTrackerController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserTimeTrackerService service;

    @PostMapping("/startime")
    public ResponseEntity<String> trackStartTime(@RequestBody UserTimeTrackerDto userTimeTrackerDto) throws CustomException {
        User user = fetchUserFromRequest(userTimeTrackerDto);
        service.addTimeTracking(userTimeTrackerDto.getDescription(), user);
        return new ResponseEntity<>("Start time tracking logged.", HttpStatus.OK);
    }

    @PutMapping("/endtime")
    public void updateEndTime(@RequestBody UserTimeTrackerDto userTimeTrackerDto) throws CustomException {

        User user = fetchUserFromRequest(userTimeTrackerDto);
        service.updateTimeTracking(userTimeTrackerDto.getDescription(), user);
    }

    private User fetchUserFromRequest(UserTimeTrackerDto userTimeTrackerDto) throws CustomException {
        String username = userTimeTrackerDto.getUsername();
        Optional<User> optionalUser = userRepo.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new CustomException("User not found");
        }
        User user = optionalUser.get();
        return user;
    }

}

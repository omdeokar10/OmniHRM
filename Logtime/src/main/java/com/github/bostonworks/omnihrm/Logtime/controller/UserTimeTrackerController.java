package com.github.bostonworks.omnihrm.Logtime.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.bostonworks.omnihrm.Logtime.JacksonConfig;
import com.github.bostonworks.omnihrm.Logtime.dto.UserTimeTrackerDto;
import com.github.bostonworks.omnihrm.Logtime.exception.CustomException;
import com.github.bostonworks.omnihrm.Logtime.exception.UserNotPresent;
import com.github.bostonworks.omnihrm.Logtime.model.User;
import com.github.bostonworks.omnihrm.Logtime.model.UserTimeTracker;
import com.github.bostonworks.omnihrm.Logtime.services.UserTimeTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@RequestMapping("/tracktime")
public class UserTimeTrackerController {

    public final String DATABASE_SERVICE = "http://localhost:8081/microservice/database/logtime";
    @Autowired
    UserTimeTrackerService service;

    @Autowired
    JacksonConfig objectMapper;

    @Autowired
    AuthController authController;

    @PostMapping("/startime")
    public ResponseEntity<String> trackStartTimeForUser(@RequestBody UserTimeTrackerDto userTimeTrackerDto) throws JsonProcessingException {

        User user;
        try {
            user = fetchUserFromRequest(userTimeTrackerDto);
        } catch (UserNotPresent e) {
            return ResponseEntity.status(404).body("User not found");
        }

        return trackStartTimeForUser(userTimeTrackerDto, user);
    }

    private ResponseEntity<String> trackStartTimeForUser(UserTimeTrackerDto userTimeTrackerDto, User user) throws JsonProcessingException {
        UserTimeTracker timeTracker = new UserTimeTracker(Instant.now(), Instant.now(), LocalDate.now(),
                userTimeTrackerDto.getDescription(), user);
        String url = DATABASE_SERVICE + "/startime";
        String userTimeTrackerString = objectMapper.objectMapper().writeValueAsString(timeTracker);
        //improvement: response dto containing only the essential information.
        return new RestTemplate().postForEntity(url, userTimeTrackerString, String.class);
    }

    @PostMapping("/endtime")
    public ResponseEntity<String> updateEndTime(@RequestBody UserTimeTrackerDto userTimeTrackerDto) throws CustomException, JsonProcessingException {

        User user;
        try {
            user = fetchUserFromRequest(userTimeTrackerDto);
        } catch (UserNotPresent e) {
            return ResponseEntity.status(404).body("User not found");
        }
        ResponseEntity<UserTimeTracker> userTimeTrackerResponseEntity = fetchUserHavingTimeTracking(user);
        String url;

        UserTimeTracker userTimeTracker = userTimeTrackerResponseEntity.getBody();
        userTimeTracker.setEndTime(Instant.now());
        userTimeTracker.setWorkDescription(userTimeTrackerDto.getDescription());

        url = DATABASE_SERVICE + "/endtime";
        String userTimeTrackerString = objectMapper.objectMapper().writeValueAsString(userTimeTracker);
        ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(url, userTimeTrackerString, String.class);
        return responseEntity;
    }

    private ResponseEntity<UserTimeTracker> fetchUserHavingTimeTracking(User user) {
        String url = DATABASE_SERVICE + "/fetchbydate/{date}/anduserid/{userid}";
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("date", LocalDate.now());
        uriVariables.put("userid", user.getUserId());
        ResponseEntity<UserTimeTracker> userTimeTrackerResponseEntity = new RestTemplate().getForEntity(url,
                UserTimeTracker.class, uriVariables);
        return userTimeTrackerResponseEntity;
    }

    private User fetchUserFromRequest(UserTimeTrackerDto userTimeTrackerDto) throws UserNotPresent {
        try {
            String username = userTimeTrackerDto.getUsername();
            ResponseEntity<User> userResponseEntity = authController.fetchUserFromService(username);
            return userResponseEntity.getBody();
        } catch (Exception e) {
            throw new UserNotPresent(e.getMessage());
        }
    }

}

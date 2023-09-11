package com.deokarkaustubh.logtime.controller;

import com.deokarkaustubh.logtime.JacksonConfig;
import com.deokarkaustubh.logtime.dto.UserTimeTrackerDto;
import com.deokarkaustubh.logtime.exception.CustomException;
import com.deokarkaustubh.logtime.model.User;
import com.deokarkaustubh.logtime.model.UserTimeTracker;
import com.deokarkaustubh.logtime.services.UserTimeTrackerService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    UserTimeTrackerService service;

    @Autowired
    JacksonConfig objectMapper;

    @Autowired
    AuthController authController;

    @PostMapping("/startime")
    public ResponseEntity<String> trackStartTime(@RequestBody UserTimeTrackerDto userTimeTrackerDto) throws CustomException, JsonProcessingException {

        User user = fetchUserFromRequest(userTimeTrackerDto);
        UserTimeTracker timeTracker = new UserTimeTracker(Instant.now(), Instant.now(), LocalDate.now(),
                userTimeTrackerDto.getDescription(), user);
        String url = "http://localhost:8081/microservice/db/tracktime/startime";
        String userTimeTrackerString = objectMapper.objectMapper().writeValueAsString(timeTracker);
        ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(url, userTimeTrackerString, String.class);
        return responseEntity;
    }

    @PutMapping("/endtime")
    public ResponseEntity<String> updateEndTime(@RequestBody UserTimeTrackerDto userTimeTrackerDto) throws CustomException, JsonProcessingException {

        User user = fetchUserFromRequest(userTimeTrackerDto);

        String url = "http://localhost:8081/microservice/db/tracktime/fetchbydate/{date}/anduserid/{userid}";
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("date", LocalDate.now());
        uriVariables.put("userid", user.getUserId());
        ResponseEntity<UserTimeTracker> userTimeTrackerResponseEntity = new RestTemplate().getForEntity(url,
                UserTimeTracker.class, uriVariables);

        UserTimeTracker userTimeTracker = userTimeTrackerResponseEntity.getBody();
        userTimeTracker.setEndTime(Instant.now());
        userTimeTracker.setWorkDescription(userTimeTrackerDto.getDescription());

        url = "http://localhost:8081/microservice/db/tracktime/startime";
        String userTimeTrackerString = objectMapper.objectMapper().writeValueAsString(userTimeTracker);
        ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(url, userTimeTrackerString, String.class);
        return responseEntity;
    }

    private User fetchUserFromRequest(UserTimeTrackerDto userTimeTrackerDto) throws CustomException {
        String username = userTimeTrackerDto.getUsername();
        ResponseEntity<User> userResponseEntity = authController.fetchUserFromService(username);
        return userResponseEntity.getBody();
    }

}

package com.deokarkaustubh.logtime.controller;

import com.deokarkaustubh.logtime.JacksonConfig;
import com.deokarkaustubh.logtime.dto.UserDto;
import com.deokarkaustubh.logtime.exception.CustomException;
import com.deokarkaustubh.logtime.model.User;
import com.deokarkaustubh.logtime.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class AuthController {

    public static final String USER_SERVICE = "http://localhost:8081/microservice/db/user";
    @Autowired
    UserService userService;

    @Autowired
    JacksonConfig objectMapper;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) throws JsonProcessingException {
        User user = new User(userDto.getUsername(), userDto.getPassword());
        String userString = objectMapper.objectMapper().writeValueAsString(user);
        String url = USER_SERVICE;
        ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(url, userString, String.class);
        return responseEntity;
    }

    @GetMapping("/{username}") //todo: remove.
    public ResponseEntity<User> fetchUser(@PathVariable String username) throws CustomException {

        return fetchUserFromService(username);
    }

    public ResponseEntity<User> fetchUserFromService(String username) {
        String url = "http://localhost:8081/microservice/db/user/{username}";
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("username", username);
        ResponseEntity<User> responseEntity = new RestTemplate().getForEntity(url, User.class, uriVariables);
        return responseEntity;
    }


}

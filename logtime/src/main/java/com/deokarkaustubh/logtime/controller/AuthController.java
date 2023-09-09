package com.deokarkaustubh.logtime.controller;

import com.deokarkaustubh.logtime.dto.UserDto;
import com.deokarkaustubh.logtime.exception.CustomException;
import com.deokarkaustubh.logtime.model.User;
import com.deokarkaustubh.logtime.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return new ResponseEntity<>("user added", HttpStatus.OK);
    }

    @GetMapping("/{username}") //todo: remove.
    public ResponseEntity<User> fetchUser(@PathVariable String username) throws CustomException {
        User user = userService.findUser(username);
        return status(HttpStatus.OK).body(user);
    }


}

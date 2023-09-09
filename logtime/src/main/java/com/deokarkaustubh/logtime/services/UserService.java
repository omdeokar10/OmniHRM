package com.deokarkaustubh.logtime.services;

import com.deokarkaustubh.logtime.dto.UserDto;
import com.deokarkaustubh.logtime.exception.CustomException;
import com.deokarkaustubh.logtime.model.User;
import com.deokarkaustubh.logtime.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public void addUser(UserDto userDto) {
        User user = new User(userDto.getUsername(), userDto.getPassword());
        userRepo.save(user);
    }

    public User findUser(String username) throws CustomException {
        Optional<User> byUsername = userRepo.findByUsername(username);
        if (byUsername.isEmpty()) {
            throw new CustomException("User not present");
        }
        return byUsername.get();
    }
}

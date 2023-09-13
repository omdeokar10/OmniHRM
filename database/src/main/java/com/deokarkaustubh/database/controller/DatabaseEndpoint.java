package com.deokarkaustubh.database.controller;

import com.deokarkaustubh.database.config.JacksonConfig;
import com.deokarkaustubh.database.dao.UserAttendanceDao;
import com.deokarkaustubh.database.exception.CustomException;
import com.deokarkaustubh.database.model.User;
import com.deokarkaustubh.database.model.UserAttendance;
import com.deokarkaustubh.database.model.UserTimeTracker;
import com.deokarkaustubh.database.repo.UserAttendanceRepo;
import com.deokarkaustubh.database.repo.UserRepo;
import com.deokarkaustubh.database.repo.UserTimeTrackerRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/microservice/db")
public class DatabaseEndpoint {

    @Autowired
    JacksonConfig objectMapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserTimeTrackerRepo userTimeTrackerRepo;

    @Autowired
    UserAttendanceRepo userAttendanceRepo;

    @PostMapping("/user")
    public String saveUser(@RequestBody String string) throws JsonProcessingException {
        System.out.println("User received:" + string);
        User user = objectMapper.objectMapper().readValue(string, User.class);
        userRepo.save(user);
        return "User created" + user.toString();
    }

    @GetMapping("/user/{username}")
    public User getUser(@PathVariable String username) throws CustomException {
        System.out.println("get user:" + username);
        Optional<User> optionalUser = userRepo.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new CustomException("User not present");
        }
        return optionalUser.get();
    }


    @PostMapping("/tracktime/startime")
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

    @PostMapping("/tracktime/endtime")
    public String saveEndTrackingTime(@RequestBody String string) throws JsonProcessingException {
        System.out.println("Tracking time for today completion started.");
        UserTimeTracker userTimeTracker = saveTimeTracking(string);
        return "start time tracked" + userTimeTracker.toString();
    }

    @GetMapping("/tracktime/fetchbydate/{date}/anduserid/{userid}")
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

    @PostMapping("/UserAttendance/signin")
    public void userAttendanceEntry(@RequestBody String string) throws JsonProcessingException {
        UserAttendanceDao userAttendanceDao = objectMapper.objectMapper().readValue(string, UserAttendanceDao.class);

        LocalDateTime localDateTime = getLocalDateTimeFrom(userAttendanceDao.getDay());
        LocalDate parse = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth());


        Instant startTime = getLocalDateTimeFrom(userAttendanceDao.getStartTime()).toInstant(ZoneOffset.UTC);
        Instant endTime = getLocalDateTimeFrom(userAttendanceDao.getEndTime()).toInstant(ZoneOffset.UTC);

        UserAttendance userAttendance = new UserAttendance(parse, userAttendanceDao.getUserid(), userAttendanceDao.isPresent(), startTime, endTime);
        userAttendanceRepo.save(userAttendance);

        System.out.println("In user attendance handler." + string);
    }

    private LocalDateTime getLocalDateTimeFrom(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(str, formatter);
    }

    //todo: endtime occurs in incorrect format laawanya remove
    private LocalDateTime getLocalDateTimeFrom2(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(str, formatter);
    }

    @GetMapping("/UserAttendance/user/{userid}")
    public User getUserById(@PathVariable String userid) {
        Optional<User> userId = userRepo.findById(Long.parseLong(userid));
        return userId.orElse(null);
    }

    @PostMapping("/UserAttendance/signout")
    public void getAttendanceHandler(@RequestBody String string) throws JsonProcessingException, CustomException {

        UserAttendanceDao userAttendanceDao = objectMapper.objectMapper().readValue(string, UserAttendanceDao.class);
        Integer userid = userAttendanceDao.getUserid();
        Instant endTime = getLocalDateTimeFrom2(userAttendanceDao.getEndTime()).toInstant(ZoneOffset.UTC);

        Optional<UserAttendance> byUserid = userAttendanceRepo.findByUserid(userid);
        if (byUserid.isEmpty()) {
            throw new CustomException("not user present with user id;");
        }

        UserAttendance userAttendanceRecord = byUserid.get();
        userAttendanceRecord.setEndTime(endTime);
        userAttendanceRepo.save(userAttendanceRecord);

        System.out.println("In user attendance handler." + string);
    }


}

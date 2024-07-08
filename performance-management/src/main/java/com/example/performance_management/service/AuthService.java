package com.example.performance_management.service;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.dto.LoginResponseDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.repo.EmployeeRepo;
import com.example.performance_management.repo.RoleRepo;
import com.example.performance_management.security.JwtTokenProvider;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Getter
public class AuthService {

    @Autowired
    private JwtTokenProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final EmployeeService employeeService;

    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RoleService roleService, EmployeeService employeeService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleService = roleService;
        this.employeeService = employeeService;
    }

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private RoleRepo roleRepo;
    Logger logger = LoggerFactory.getLogger(this.getClass());

//    private static int attempts = 0;
//    private boolean bPasswordResetDone = true;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public EmployeeDto signup(EmployeeDto employeeDto) throws DataIntegrityViolationException {
        return employeeService.createEmployee(employeeDto, passwordEncoder);
    }

    private boolean ifUserExists(EmployeeDto employeeDto) {
        Optional<Employee> userByName = employeeRepo.findByUserNameStartsWith(employeeDto.getUserName());
        if (userByName.isEmpty()) {
            Optional<Employee> userByEmail = employeeRepo.findByEmailStartsWith(employeeDto.getEmail());
            return !userByEmail.isEmpty();
        }
        return true;
    }

    private String formMailBody(String token) {
        return "Thank you for signing up, please click on the url to activate your account:\n" +
                "http://localhost:8080/api/auth/accountVerification/" + token;
    }

    private String formResetPasswordBody(String token) {
        return "Use following link to reset the password:\n" +
                "http://localhost:8080/api/auth/completeresetpassword/" + token;
    }



    public LoginResponseDto loginUser(String username, String password) {

        Employee employee = employeeRepo.findByUserNameStartsWith(username).orElseThrow(() -> new CustomException("Invalid username"));
        if (employee.isEnabled()) {
            Authentication authenticate = authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(
                    username, password));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String jwtToken = jwtProvider.generateToken(authenticate);
            return new LoginResponseDto(username, jwtToken);
        }
        throw new CustomException("User is disabled");
    }

    private void handleIncorrectCredentials(String username, Employee principalUser) {
//        attempts += 1;
//        if (attempts > 2) {
//            bPasswordResetDone = false;
//            principalUser.setEnabled(false);
//            throw new CustomException(HttpStatus.BAD_REQUEST, "Account is locked, please reset the password for user : " + username);
//        }
//        throw new CustomException(HttpStatus.BAD_REQUEST,
//                "Incorrect credentials for username: " + username + ". You have " + (3 - attempts) + " attempts remaining.");
    }

//    public LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
//        refreshTokenService.validateRefreshToken(refreshTokenRequestDto.getRefreshToken());
//        String token = "jwtProvider.generateToken(refreshTokenRequest.getUsername())";
//        String refreshToken = refreshTokenRequestDto.getRefreshToken();
//        Instant expiry = Instant.now().plusMillis(JwtTokenProvider.JWT_EXPIRATION);
//        return new LoginResponseDto(refreshTokenRequestDto.getUsername(), token, refreshToken, expiry);
//    }

    public Employee getUserByUserName(String username) {
        return employeeRepo.findByUserNameStartsWith(username).orElseThrow(() -> new CustomException("User not found with id -" + username));
    }

    public Employee getUserByEmail(String email) {
        return employeeRepo.findByUserNameStartsWith(email).orElseThrow(() -> new CustomException("User not found with id -" + email));
    }


    public void resetPasswordForEmail(String email) {
//        Optional<User> byEmail = userRepo.findByEmail(email);
//        if (byEmail.isPresent()) {
//            User user = byEmail.get();
//            VerificationToken verificationToken = tokenRepo.findByUser(user).orElseThrow(() -> new CustomException("Not token found for user"));
//            mailService.sendEmail(new NotificationEmail("Account activation", user.getEmail(), formResetPasswordBody(verificationToken.getToken())));
//        }
        //todo: to implement.
    }

//    public void setPasswordForUser(User userByToken, String newpassword) {
//        userByToken.setPassword(passwordEncoder.encode(newpassword));
//        userByToken.setEnabled(true);
//        employeeRepo.save(userByToken);
//    }
}

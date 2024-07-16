package com.example.performance_management.service;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.dto.EmployeeLoginResponseDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.Role;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.repo.EmployeeRepo;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Getter
public class AuthService {

    @Autowired
    private JwtTokenProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;
    private final PermissionService permissionService;
    private final EmployeeRepo employeeRepo;


    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EmployeeService employeeService, PermissionService permissionService, EmployeeRepo employeeRepo) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
        this.permissionService = permissionService;
        this.employeeRepo = employeeRepo;
    }
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public EmployeeDto employeeSignup(EmployeeDto employeeDto) throws DataIntegrityViolationException {
        return employeeService.createViewableEmployee(employeeDto, passwordEncoder);
    }

    public EmployeeLoginResponseDto employeeLogin(String username, String password) {

        Employee employee = employeeRepo.findByUserNameStartsWith(username).orElseThrow(() -> new CustomException("Invalid username"));
        if (employee.isEnabled()) {
            Authentication authenticate = authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(
                    username, password));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String jwtToken = jwtProvider.generateToken(authenticate);
            List<String> roles = employee.getRoles().stream().map(Role::getRoleName).toList();
            return new EmployeeLoginResponseDto(username, jwtToken, roles.toArray(new String[0]));
        }
        throw new CustomException("User is disabled");
    }

    public UserDetails getCurrentUserDetails(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

    public String getNameForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return principal.getUsername();
    }



    public Employee getEmployeeByEmail(String email) {
        return employeeRepo.findByUserNameStartsWith(email).orElseThrow(() -> new CustomException("User not found with id -" + email));
    }

    public Employee getEmployeeByUserName(String username) {
        return employeeRepo.findByUserNameStartsWith(username).orElseThrow(() -> new CustomException("User not found with id -" + username));
    }

        /*

    public void setPasswordForUser(User userByToken, String newpassword) {
        userByToken.setPassword(passwordEncoder.encode(newpassword));
        userByToken.setEnabled(true);
        employeeRepo.save(userByToken);
    }
    private void handleIncorrectCredentials(String username, Employee principalUser) {
        attempts += 1;
        if (attempts > 2) {
            bPasswordResetDone = false;
            principalUser.setEnabled(false);
            throw new CustomException(HttpStatus.BAD_REQUEST, "Account is locked, please reset the password for user : " + username);
        }
        throw new CustomException(HttpStatus.BAD_REQUEST,

                "Incorrect credentials for username: " + username + ". You have " + (3 - attempts) + " attempts remaining.");


        public LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        refreshTokenService.validateRefreshToken(refreshTokenRequestDto.getRefreshToken());
        String token = "jwtProvider.generateToken(refreshTokenRequest.getUsername())";
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        Instant expiry = Instant.now().plusMillis(JwtTokenProvider.JWT_EXPIRATION);
        return new LoginResponseDto(refreshTokenRequestDto.getUsername(), token, refreshToken, expiry);
    }

    public void resetPasswordForEmail(String email) {
        Optional<User> byEmail = userRepo.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            VerificationToken verificationToken = tokenRepo.findByUser(user).orElseThrow(() -> new CustomException("Not token found for user"));
            mailService.sendEmail(new NotificationEmail("Account activation", user.getEmail(), formResetPasswordBody(verificationToken.getToken())));
        }
        todo: to implement.
    }
*/
}

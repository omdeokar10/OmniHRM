package com.example.performance_management.service;

import com.example.performance_management.dto.AuthenticationResponse;
import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.dto.RefreshTokenRequest;
import com.example.performance_management.dto.performance.EmployeeLoginResponseDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.role.Role;
import com.example.performance_management.entity.role.RoleEnum;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.repo.EmployeeRepo;
import com.example.performance_management.security.JwtTokenProvider;
import com.example.performance_management.utils.HelperUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
@Transactional
@Getter
public class AuthService {

    @Autowired
    private JwtTokenProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;
    private final EmployeeRepo employeeRepo;
    private final RefreshTokenService refreshTokenService;
    private final HelperUtil helperUtil;

    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EmployeeService employeeService, EmployeeRepo employeeRepo, RefreshTokenService refreshTokenService, HelperUtil helperUtil) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
        this.employeeRepo = employeeRepo;
        this.refreshTokenService = refreshTokenService;
        this.helperUtil = helperUtil;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public void employeeSignup(EmployeeDto employeeDto) throws DataIntegrityViolationException {
        employeeService.signup(employeeDto);
    }


    public EmployeeLoginResponseDto employeeLogin(String username, String password, boolean adminLogin) {

        Employee employee = employeeRepo.findByUserNameStartsWith(username).orElseThrow(() -> new CustomException("Invalid username"));

        if (employee.isEnabled() && isValid(employee, adminLogin)) {
            List<? extends GrantedAuthority> roles = getGrantedAuthorities(employee);
            Authentication authenticate = authenticationManager.authenticate(UsernamePasswordAuthenticationToken.authenticated(username, password, roles));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String accessToken = jwtProvider.generateToken(authenticate);
            String refreshToken = refreshTokenService.generateRefreshToken().getToken();
            List<String> rolesString = employee.getRoles().stream().map(Role::getRoleName).toList();
            return new EmployeeLoginResponseDto(username, accessToken, refreshToken, employee.getCompanyName(), rolesString.toArray(new String[0]));
        }
        throw new CustomException("User is disabled");
    }

    private boolean isValid(Employee employee, boolean isAdmin) {
        if (isAdmin) {
            List<Role> roles = employee.getRoles();
            for (Role role : roles) {
                if (role.getRoleName().equals(RoleEnum.COMPANYADMIN.getRoleName())) {
                    return true;
                }
            }
            throw new CustomException("Invalid login, company employee has a non-admin login.");
        } else {
            List<Role> roles = employee.getRoles();
            for (Role role : roles) {
                if (role.getRoleName().equals(RoleEnum.COMPANYADMIN.getRoleName())) {
                    throw new CustomException("Invalid login, admin user should use a different admin login.");
                }
            }
            return true;
        }

    }

    private List<GrantedAuthority> getGrantedAuthorities(Employee employee) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = employee.getRoles();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));
        return authorities;
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

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateToken(refreshTokenRequest.getUsername());
        String refreshToken = refreshTokenRequest.getRefreshToken();
        Instant expiry = Instant.now().plusMillis(JwtTokenProvider.REFRESH_EXPIRATION);
        return new AuthenticationResponse(refreshTokenRequest.getUsername(), token, refreshToken, expiry);
    }

    public void deleteRefreshToken(String refreshToken) {
        refreshTokenService.deleteRefreshToken(refreshToken);
    }

    public void resetPassword(String username, String password) {
        employeeService.resetPassword(username, password);
    }

    public Future<String> forgotPassword(String email) {
        Employee emp = employeeService.getEmployeeByEmail(email);
        emp.setPassword(passwordEncoder.encode("password"));
        employeeRepo.save(emp);
        return helperUtil.sendMail(email, "password");
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

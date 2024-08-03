package com.example.performance_management.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals("/api/auth/login") || request.getServletPath().equals("/api/auth/refresh/token")) {
            filterChain.doFilter(request, response);
        } else {
            String token = getTokenFromRequest(request);
            System.out.println("Token:" + token + ", request" + request.getRequestURI());
            if (StringUtils.hasText(token) && jwtTokenProvider.validateJwtToken(response, token)) {
                String usernameFromJWTToken = jwtTokenProvider.getUsernameFromJWTToken(token);
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(usernameFromJWTToken);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);
                authToken.setDetails(webAuthenticationDetails);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = "Bearer ";
        String bearerToken = request.getHeader("Authorization");
        System.out.println("gettoken from request: Bearer Token " + bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(bearer)) {
            return bearerToken.substring(bearer.length(), bearerToken.length());
        }
        return null;
    }

}

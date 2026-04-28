package com.rga.gradesubmission.security.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import com.rga.gradesubmission.domain.exception.EntityNotFoundException;
import com.rga.gradesubmission.security.handler.HandleServletResponse;
import com.rga.gradesubmission.security.manager.CustomAuthenticationManager;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (EntityNotFoundException ex) {
            HandleServletResponse.handleException(response, HttpStatus.BAD_REQUEST.value(), CustomAuthenticationManager.BAD_CREDENTIALS);
        } catch (RuntimeException ex) {
            HandleServletResponse.handleException(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex);
        }
    }
}

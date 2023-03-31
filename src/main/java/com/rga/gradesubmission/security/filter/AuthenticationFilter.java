package com.rga.gradesubmission.security.filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.rga.gradesubmission.domain.documents.User;
import com.rga.gradesubmission.security.SecurityConstants;
import com.rga.gradesubmission.security.handler.HandleServletResponse;
import com.rga.gradesubmission.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private CustomAuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, BadCredentialsException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (UnrecognizedPropertyException ex) {
            throw new RuntimeException( "you have entered an invalid key (" + ex.getPropertyName() + ") for email or password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 2);

        String userId = authResult.getName();

        Builder tokenBuilder = JWT.create();
        tokenBuilder.withSubject(userId);
        tokenBuilder.withExpiresAt(calendar.getTime());
        String token = tokenBuilder.sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
        HandleServletResponse.handleTokenResponse(response, HttpStatus.OK.value(), token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        HandleServletResponse.handleException(response, HttpStatus.BAD_REQUEST.value(), failed.getMessage());
    }

}

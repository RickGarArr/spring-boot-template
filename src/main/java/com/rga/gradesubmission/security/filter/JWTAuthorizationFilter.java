package com.rga.gradesubmission.security.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rga.gradesubmission.controllers.UserController;
import com.rga.gradesubmission.security.SecurityConstants;
import com.rga.gradesubmission.security.handler.HandleServletResponse;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private static final String TOKEN_NOT_FOUND = "Security Violation: the header Authorization can not be blank";
    private static final String INVALID_TOKEN = "Security Violation: the provided token is not correct";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String requestRegisterPath = UserController.class.getAnnotation(RequestMapping.class).value()[0] + UserController.REGISTER_PATH;
            String requestUri = request.getRequestURI();
            if (!(requestUri.equals(requestRegisterPath) && request.getMethod().equals("POST"))) {

                String authorizationHeader = request.getHeader(SecurityConstants.AUTHORIZATION);

                if (authorizationHeader == null || authorizationHeader.equals(""))
                    throw new NullPointerException(TOKEN_NOT_FOUND);

                String[] splitAuthorizationHeader = authorizationHeader.split("\\s+");

                if (splitAuthorizationHeader.length != 2)
                    throw new RuntimeException(INVALID_TOKEN);

                if (!splitAuthorizationHeader[0].equals(SecurityConstants.BEARER.trim()))
                    throw new RuntimeException(INVALID_TOKEN);

                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY)).build()
                        .verify(splitAuthorizationHeader[1]);
                String userId = decodedJWT.getSubject();
                Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, Arrays.asList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (ArrayIndexOutOfBoundsException | JWTDecodeException ex) {
            HandleServletResponse.handleException(response, HttpServletResponse.SC_BAD_REQUEST, INVALID_TOKEN);
        } catch (Exception e) {
            HandleServletResponse.handleException(response, HttpServletResponse.SC_BAD_REQUEST, e);
        }
    }

}

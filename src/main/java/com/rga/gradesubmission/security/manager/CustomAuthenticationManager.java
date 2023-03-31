package com.rga.gradesubmission.security.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.rga.gradesubmission.domain.documents.User;
import com.rga.gradesubmission.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    public static final String BAD_CREDENTIALS = "you have entered an invalid username or password";

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException, BadCredentialsException {
        User user = this.userService.getUserByEmail(authentication.getName());
        if (!bCryptPasswordEncoder.matches((CharSequence) authentication.getCredentials(), user.getPassword())) {
            throw new BadCredentialsException(BAD_CREDENTIALS);
        }
        return new UsernamePasswordAuthenticationToken(user.getId().toString(), user.getPassword());
    }
    
}

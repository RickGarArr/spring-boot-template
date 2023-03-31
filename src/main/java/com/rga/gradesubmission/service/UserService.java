package com.rga.gradesubmission.service;

import java.util.List;

import com.rga.gradesubmission.domain.documents.User;

public interface UserService {
    User getUserById(String id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User saveUser(User user);
}
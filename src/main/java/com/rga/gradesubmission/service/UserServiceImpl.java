package com.rga.gradesubmission.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rga.gradesubmission.domain.documents.User;
import com.rga.gradesubmission.domain.exception.EntityNotFoundException;
import com.rga.gradesubmission.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUserById(String id) {
        ObjectId objectId = new ObjectId(id);
        Optional<User> user = userRepository.findById(objectId);
        return unwrapUser(user, id);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return unwrapUser(user, email);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    static User unwrapUser(Optional<User> entity, ObjectId id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(id, User.class);
    }

    static User unwrapUser(Optional<User> entity, String email) {
        if (entity.isPresent()) {
            return entity.get();
        } else
            throw new EntityNotFoundException(email, User.class);
    }

}

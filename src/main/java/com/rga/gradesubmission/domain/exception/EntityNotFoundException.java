package com.rga.gradesubmission.domain.exception;

import org.bson.types.ObjectId;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(ObjectId id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
    }

    public EntityNotFoundException(Long id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
    }

    public EntityNotFoundException(String email, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with email '" + email + "' does not exist in our records");
    }

}
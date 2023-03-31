package com.rga.gradesubmission.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.rga.gradesubmission.domain.documents.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
	@Query("{ email: ?0 }")
	Optional<User> findByEmail(String email);

	@Query("{ _id: ?0 }")
	Optional<User> findById(ObjectId email);
}
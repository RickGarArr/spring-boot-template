package com.rga.gradesubmission.repository;

import org.springframework.data.repository.CrudRepository;

import com.rga.gradesubmission.domain.entities.Course;


public interface CourseRepository extends CrudRepository<Course, Long> {

}
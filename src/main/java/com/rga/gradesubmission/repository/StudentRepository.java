package com.rga.gradesubmission.repository;

import org.springframework.data.repository.CrudRepository;

import com.rga.gradesubmission.domain.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

}
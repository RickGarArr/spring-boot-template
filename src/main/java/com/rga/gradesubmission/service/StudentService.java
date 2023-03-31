package com.rga.gradesubmission.service;

import java.util.List;
import java.util.Set;

import com.rga.gradesubmission.domain.entities.Course;
import com.rga.gradesubmission.domain.entities.Student;

public interface StudentService {
    Student getStudent(Long id);
    Student saveStudent(Student student);
    void deleteStudent(Long id);
    List<Student> getStudents();
    Set<Course> getEnrolledCourses(Long id);
}
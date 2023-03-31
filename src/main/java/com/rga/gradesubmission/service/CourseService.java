package com.rga.gradesubmission.service;

import java.util.List;
import java.util.Set;

import com.rga.gradesubmission.domain.entities.Course;
import com.rga.gradesubmission.domain.entities.Student;

public interface CourseService {
    Course getCourse(Long id);
    Course saveCourse(Course course);
    void deleteCourse(Long id);    
    Course addStudentToCourse(Long studentId, Long courseId);
    List<Course> getCourses();
    Set<Student> getEnrolledStudents(Long id);
}
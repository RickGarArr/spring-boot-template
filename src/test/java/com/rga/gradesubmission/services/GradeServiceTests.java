package com.rga.gradesubmission.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rga.gradesubmission.domain.entities.Course;
import com.rga.gradesubmission.domain.entities.Grade;
import com.rga.gradesubmission.domain.entities.Student;
import com.rga.gradesubmission.domain.exception.GradeNotFoundException;
import com.rga.gradesubmission.repository.CourseRepository;
import com.rga.gradesubmission.repository.GradeRepository;
import com.rga.gradesubmission.repository.StudentRepository;
import com.rga.gradesubmission.service.GradeServiceImpl;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class GradeServiceTests {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private GradeServiceImpl gradeService;

    private LocalDate harryMockBirthday;

    @Before
    public void setup() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1997, Calendar.FEBRUARY, 7, 0, 0, 0);
        this.harryMockBirthday = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        MockitoAnnotations.openMocks(this);
    }

    @Test()
    public void getAllGradesTest() {
        List<Grade> grades = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1997, Calendar.FEBRUARY, 7, 0, 0, 0);

        Student harryStyStudent = new Student("Harry Potter", harryMockBirthday);
        Course potionCourse = new Course("Pottions", "HP-P1", "this is a mock description");
        Grade grade = new Grade(1L, "C-", harryStyStudent, potionCourse);
        grades.add(grade);

        when(gradeRepository.findAll()).thenReturn(grades);

        this.gradeService.getAllGrades();

        assertEquals("Harry Potter", grades.get(0).getStudent().getName());
    }

    @Test
    public void getGradeTest() {
        Long harryId = 451L;
        Long potionCourseId = 4111L;
        Long gradeId = 12L;

        Student harryStyStudent = new Student("Harry Potter", harryMockBirthday);
        harryStyStudent.setId(harryId);
        Course potionCourse = new Course("Pottions", "HP-P1", "this is a mock description");
        potionCourse.setId(potionCourseId);

        Grade grade = new Grade(gradeId, "C-", harryStyStudent, potionCourse);

        Optional<Grade> optionalGrade = Optional.of(grade);

        when(this.gradeRepository.findByStudentIdAndCourseId(harryId, potionCourseId)).thenReturn(optionalGrade);

        assertEquals(harryId, optionalGrade.get().getStudent().getId());
        assertEquals(potionCourseId, optionalGrade.get().getCourse().getId());

        this.gradeService.getGrade(harryId, potionCourseId);
    }

    @Test()
    public void getGrade_GradeNotFoundExceptionTest() {
        Long harryId = 451L;
        Long potionCourseId = 4111L;

        Student harryStyStudent = new Student("Harry Potter", harryMockBirthday);
        harryStyStudent.setId(harryId);
        Course potionCourse = new Course("Pottions", "HP-P1", "this is a mock description");
        potionCourse.setId(potionCourseId);

        Optional<Grade> optionalGrade = Optional.empty();

        when(this.gradeRepository.findByStudentIdAndCourseId(harryId, potionCourseId)).thenReturn(optionalGrade);

        try {
            this.gradeService.getGrade(harryId, potionCourseId);
        } catch (GradeNotFoundException e) {
            assertEquals("The grade with student id: '451' and course id: '4111' does not exist in our records", e.getMessage());
        }
    }
}

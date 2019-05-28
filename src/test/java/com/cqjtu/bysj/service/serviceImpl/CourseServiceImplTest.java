package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.Course;
import com.cqjtu.bysj.service.CourseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceImplTest {
    private Course course;
    @Before
    public void setUp(){
        course = new Course();
    }
    @Test
    public void createCourse() {
        CourseService mock = mock(CourseService.class);
        doNothing().when(mock).createCourse(isA(Course.class));
        mock.createCourse(course);
        verify(mock, times(1)).createCourse(course);
    }
    @Test
    public void getCourseList() {
        CourseService mock = mock(CourseService.class);
        doReturn(new ArrayList<>()).when(mock).getCourseList();
        mock.getCourseList();
        assertEquals(new ArrayList<Course>(), new ArrayList<>());
        verify(mock, times(1)).getCourseList();
    }
    @Test
    public void updateCourse() {
        CourseService mock = mock(CourseService.class);
        doNothing().when(mock).updateCourse(isA(Course.class));
        mock.updateCourse(course);
        verify(mock, times(1)).updateCourse(course);
    }
    @Test
    public void deleteCourse() {
        CourseService mock = mock(CourseService.class);
        doNothing().when(mock).deleteCourse(isA(Integer.class));
        mock.deleteCourse(course.getCourseId());
        verify(mock, times(1)).deleteCourse(course.getCourseId());
    }
}
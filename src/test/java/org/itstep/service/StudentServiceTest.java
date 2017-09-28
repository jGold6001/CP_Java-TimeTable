package org.itstep.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito. *;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.itstep.App;
import org.itstep.dao.StudentDAO;
import org.itstep.dao.pojo.Student;
import org.itstep.service.impl.StudentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class StudentServiceTest {
	
	@MockBean
	private StudentDAO dao;
	
	@InjectMocks
	private StudentServiceImpl service;
	
	@Spy
	List<Student> students;
	
	Student student;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		students = new ArrayList<Student>(Arrays.asList(
				new Student("jayGold", "jQwerty", "Vyacheslav", "Zlatov", "15pv5"),
				new Student("vasPup", "pupk1234", "Vasiliy", "Pupkin", "15pv5"),
				new Student("stepPup", "step1234", "Stepan", "Pupkin", "19pv5")
		));
		
		student = students.get(0);
	}
	
	@Test
	public void testCreateAndUpdate() {
		when(dao.saveAndFlush(student)).thenReturn(student);
		Student studInDb = service.createAndUpdateStudent(student);
		assertEquals(studInDb.getLogin(), student.getLogin());
	}
	
	@Test
	public void testGetOne() {
		when(dao.findOne(student.getLogin())).thenReturn(student);
		assertEquals(student, service.getStudent(student.getLogin()));
	}
	
	@Test
	public void testFindAllByGroup() {
		List<Student> expectedStudents = new ArrayList(Arrays.asList(students.get(0), students.get(1)));
		when(dao.findStudentsByGroup("15pv5")).thenReturn(expectedStudents);
		List<Student> actualStudents = service.findStudentsByGroup("15pv5");
		assertArrayEquals(expectedStudents.toArray(), actualStudents.toArray());
	}
	
	@Test
	public void testDelete() {
		doNothing().when(dao).delete(student.getLogin());
		service.deleteStudent(student.getLogin());
		verify(dao).delete(student.getLogin());
	}

}

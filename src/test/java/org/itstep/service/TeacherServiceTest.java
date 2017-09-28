package org.itstep.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.itstep.App;
import org.itstep.dao.TeacherDAO;
import org.itstep.dao.pojo.Teacher;
import org.itstep.service.impl.TeacherServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TeacherServiceTest {
	@MockBean
	private TeacherDAO dao;
	
	@InjectMocks
	private TeacherServiceImpl service;
	
	@Spy
	private List<Teacher> teachers;
	
	Teacher teacher;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		
		teachers = new ArrayList<Teacher>(Arrays.asList(
				new Teacher("les", "olles)9", "Les", "Poderevyansky", "Art writting"),
				new Teacher("bobik", "bob1234", "Bob", "Barker", "Java EE"),
				new Teacher("george01", "gora&8", "George", "Washington", "Java EE")
		));
		teacher = teachers.get(0);
	}
	
	@Test
	public void testCreateAndUpdate() {
		when(dao.saveAndFlush(teacher)).thenReturn(teacher);
		Teacher teacherInDb = service.createAndUpdateTeacher(teacher);
		assertEquals(teacher.getLogin(), teacherInDb.getLogin());
	}
	
	@Test
	public void findTeacherBySubjectTest() {
		List<Teacher> expecteds = new ArrayList<Teacher>(Arrays.asList(teachers.get(1), teachers.get(2)));
		when(dao.findTeachersBySubject("Java EE")).thenReturn(expecteds);
		List<Teacher> actuals = service.findTeacherBySubject("Java EE");
		assertArrayEquals(expecteds.toArray(), actuals.toArray());
	}
	
}

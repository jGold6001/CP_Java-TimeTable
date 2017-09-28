package org.itstep.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.itstep.App;
import org.itstep.dao.pojo.Teacher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TeacherDAOTest {

	@Autowired
	private TeacherDAO dao;
	
	@Spy
	private List<Teacher> teachers;
	
	@Before
	public void init(){
		teachers = new ArrayList<Teacher>(Arrays.asList(
				new Teacher("les", "olles)9", "Les", "Poderevyansky", "Art writting"),
				new Teacher("bobik", "bob1234", "Bob", "Barker", "Java EE"),
				new Teacher("george01", "gora&8", "George", "Washington", "Java EE")
		));
	}
	
	@Test
	public void CreateAndDeleteOperationsTest() {
		Teacher teacher = teachers.get(0);
		Teacher teacherInDb = dao.saveAndFlush(teacher);
		assertEquals(teacher.getLogin(), teacherInDb.getLogin());
		
		dao.delete(teacherInDb);
		assertFalse(dao.exists(teacherInDb.getLogin()));
	}
}

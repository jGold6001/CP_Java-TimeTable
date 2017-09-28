package org.itstep.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.itstep.App;
import org.itstep.dao.LessonDAO;
import org.itstep.dao.pojo.Lesson;
import org.itstep.service.impl.LessonServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class LessonServiceTest {
	@MockBean
	private LessonDAO dao;
	
	@InjectMocks
	private LessonServiceImpl service;
	
	@Spy
	List<Lesson> lessons;
	
	Lesson lesson;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		lessons = new ArrayList<Lesson>(Arrays.asList(
				new Lesson(new Time(10,12,00).getTime(), (long) 2400000, "JayGold", "14pv5", "12", "Java"),
				new Lesson(new Time(11,12,00).getTime(), (long) 2400000, "Kipelov", "15pv5", "14", "Metal"),
				new Lesson(new Time(12,12,00).getTime(), (long) 2400000, "StrausTrup", "16pv5", "16", "C++"),
				new Lesson(new Time(12,12,00).getTime(), (long) 2400000, "JayGold", "16pv5", "16", "C#")
		));
		lesson = lessons.get(0);
	}
	
	@Test
	public void testCreateAndUpdate() {
		when(dao.saveAndFlush(lesson)).thenReturn(lesson);
		Lesson lessonInDb = service.saveAndUpdate(lesson);
		assertEquals(lesson.getLessonId(), lessonInDb.getLessonId());
	}
	
	@Test
	public void testGetOne() {
		when(dao.getOne(lesson.getLessonId())).thenReturn(lesson);	
		assertEquals(lesson, service.getLesson(lesson.getLessonId()));
	}
	
	@Test
	public void testDelete() {
		doNothing().when(dao).delete(lesson.getLessonId());
		service.delete(lesson.getLessonId());
		verify(dao).delete(lesson.getLessonId());
	}
	
}

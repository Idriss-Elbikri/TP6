package com.example.student_management;

import com.example.student_management.controllers.StudentController;
import com.example.student_management.entity.Student;
import com.example.student_management.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // Import this
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension; // Import this
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Use MockitoExtension instead of @SpringBootTest
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

	// @Mock creates a fake instance of the service
	@Mock
	private StudentService studentService; // Removed @Autowired

	// @InjectMocks creates an instance of StudentController
	// and injects the @Mock fields (studentService) into it.
	@InjectMocks
	private StudentController studentController;

	@Test
	void testSaveStudent() {
		Student student = new Student();
		student.setId(1);
		student.setNom("Mido");

		// Define the mock behavior
		when(studentService.save(any(Student.class))).thenReturn(student);

		// Call the real controller method
		ResponseEntity<Student> response = studentController.save(student);

		// Assert the results
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Mido", response.getBody().getNom());
	}

	@Test
	void testDeleteStudent() {
		when(studentService.delete(1)).thenReturn(true);
		ResponseEntity<Void> response = studentController.delete(1);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void testFindAllStudents() {
		Student student1 = new Student();
		Student student2 = new Student();
		when(studentService.findAll()).thenReturn(Arrays.asList(student1, student2));

		ResponseEntity<List<Student>> response = studentController.findAll();

		assertEquals(2, response.getBody().size());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testCountStudents() {
		when(studentService.countStudents()).thenReturn(10L);
		// Make sure your controller method is named 'countStudent()'
		ResponseEntity<Long> response = studentController.countStudent();
		assertEquals(10L, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testFindByYear() {
		when(studentService.findNbrStudentByYear()).thenReturn(Arrays.asList());
		ResponseEntity<Collection<?>> response = studentController.findByYear();
		assertEquals(0, response.getBody().size());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
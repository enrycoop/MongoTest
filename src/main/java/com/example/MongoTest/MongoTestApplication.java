package com.example.MongoTest;

import com.example.MongoTest.models.Address;
import com.example.MongoTest.models.Student;
import com.example.MongoTest.repositories.StudentRepository;
import com.example.MongoTest.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;

import java.util.List;

@SpringBootApplication
public class MongoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoTestApplication.class, args);
	}

	@Bean
	public CommandLineRunner startup(StudentRepository stRepo, MongoTemplate mongoTemplate,
									 StudentService studentService){
		return args -> {
			Student s = Student.builder()
					.firstName("Anne")
					.lastName("Bonny")
					.email("pirate.life@calico.com").studentId(1L)
					.address(Address.builder()
							.city("Old Head of Kinsale")
							.country("Ireland")
							.street("no street")
							.zipCode("Country Cork")
							.build())
					.note("This is just an example note about this " +
							"Student to tst the capacity of indexed " +
							"text in Spring Data MongoDB ")
					.build();
			//stRepo.save(s);


			Student ss = Student.builder()
					.firstName("Anne")
					.lastName("Bonny")
					.email("pirate.life2@calico.com").studentId(2L)
					.address(Address.builder()
							.city("Old Head of Kinsale")
							.country("Ireland")
							.street("no street")
							.zipCode("Country Cork")
							.build())
					.note("Just a different Note for " +
							"testing unique text indexing")
					.build();

			//stRepo.insert(ss);

			// Query without repository
			List<String> testEmails = List.of("pirate.life@calico.com","pirate.life2@calico.com");
			Query query = new Query();
			query.addCriteria(Criteria.where("email").in(testEmails));

			List<Student> found = mongoTemplate.find(query,Student.class);

//			if (found.isEmpty()){
//				stRepo.insert(s);
//				found = List.of(s);
//			}
			if (!found.contains(s)) stRepo.insert(s);
			if (!found.contains(ss)) stRepo.insert(ss);

			System.out.println("[*] Found : ");
			found.forEach(System.out::println );

			// Query using Repository

			String testName = "Anne";
			stRepo.findStudentsByFirstName(testName).forEach(System.out::println);

			stRepo.findEmailFromName("Anne").forEach(System.out::println);

			TextCriteria tc = TextCriteria.forDefaultLanguage().matchingAny("MongoDB");
			List<Student> tc_found = stRepo.findByFirstName("Anne", tc);
			System.out.println("[*] Found with text criteria: ");
			tc_found.forEach(System.out::println);

			Student newStudent = Student.builder()
					.firstName("Jack")
					.lastName("Calico")
					.email("calico.jack@pirate.world")
					.build();
			Query findByLastName = new Query();
			findByLastName.addCriteria(Criteria.where("lastName").is("Calico"));
			UpdateDefinition newStudentUpdate = new Update()
					.set("firstName",newStudent.getFirstName())
					.set("lastName", newStudent.getLastName())
					.set("email", newStudent.getEmail())
					.set("_id",99L);

			mongoTemplate.upsert(findByLastName,newStudentUpdate, Student.class);
			studentService.updateStudentFirstName("Jack","Jackie");

		};
	}

}

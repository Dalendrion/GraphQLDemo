package nl.avermaas.graphqldemo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(AuthorRepository authorRepository, BookRepository bookRepository) {
		return args -> {
			var josh = authorRepository.save(new Author(null, "Josh Long"));
			var mark = authorRepository.save(new Author(null, "Mark Heckler"));
			bookRepository.saveAll(List.of(
					new Book("Reactive Spring", "Josh Long", josh),
					new Book("Cloud Native Java", "O'Reilly", josh),
					new Book("Spring Boot Up & Running", "O'Reilly", mark)
			));
		};
	}

}

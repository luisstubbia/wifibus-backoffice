package com.vates.wifibus.backoffice;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vates.wifibus.backoffice.model.User;
import com.vates.wifibus.backoffice.repository.UserRepository;

/**
 * 
 * @author Gaston Napoli
 *
 */
@SpringBootApplication
public class BackofficeAdminApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(BackofficeAdminApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

		return builder.sources(BackofficeAdminApplication.class);

	}

	@Bean
	public CommandLineRunner loadData(UserRepository repository) {
		return (args) -> {

			repository.save(new User("tgonzalez", "Tito", "Gonzalez"));

			logger.info("Customers found with findAll():");
			logger.info("-------------------------------");
			for (User user : repository.findAll()) {
				logger.info(user.toString());
			}
			logger.info("----------------------------------------------------------------------------------------");

			User user = repository.findOne(1L);
			logger.info("user found with findOne(1L):");
			logger.info("--------------------------------");
			logger.info(user.toString());
			logger.info("----------------------------------------------------------------------------------------");

			logger.info("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
			logger.info("--------------------------------------------");
			Optional<User> gonzalez = repository.findOneByUsername("Gonzalez");
			logger.info(gonzalez.orElse(new User()).toString());
		};
	}

	public static void main(String[] args) {

		ApplicationContext app = SpringApplication.run(BackofficeAdminApplication.class, args);
		
		PasswordEncoder passwordEncoder = app.getBean(PasswordEncoder.class);
		System.out.println("Admin password = [" + passwordEncoder.encode("admin") + "]");
		System.out.println("User password = [" + passwordEncoder.encode("demo") + "]");
		
	}

}

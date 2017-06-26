package com.vates.wifibus.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 
 * @author Gaston Napoli
 *
 */
@SpringBootApplication
@EntityScan(basePackages = "com.vates.wifibus")
public class BackofficeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackofficeApiApplication.class, args);
	}

}
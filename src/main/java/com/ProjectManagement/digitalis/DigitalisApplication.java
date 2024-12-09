package com.ProjectManagement.digitalis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigitalisApplication {
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_RESET = "\u001B[0m";
	public static void main(String[] args) {

		SpringApplication.run(DigitalisApplication.class, args);

	}

}

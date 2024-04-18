package com.careerit.cbook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CbookAppApplication {

	@Value("${greetings}")
	private String greetings;

	public static void main(String[] args) {
		SpringApplication.run(CbookAppApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
			System.out.println("Hello, "+greetings);
		};
	}

}

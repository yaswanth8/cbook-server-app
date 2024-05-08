package com.careerit.cbook;

import com.careerit.cbook.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class CbookAppApplication {

	@Value("${greetings}")
	private String greetings;

	@Autowired
	private EmailService emailService;



	public static void main(String[] args) {
		SpringApplication.run(CbookAppApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			log.info("Greetings : " + greetings);
			emailService.sendEmail("yaswanthk722@gmail.com", "Greetings", greetings);
		};

	}

}

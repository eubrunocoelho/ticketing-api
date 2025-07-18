package com.eubrunocoelho.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TicketingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketingApiApplication.class, args);
	}

}

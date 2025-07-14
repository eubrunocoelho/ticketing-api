package com.eubrunocoelho.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
		exclude = {
				// API em fase de testes sem configuração de banco de dados
				DataSourceAutoConfiguration.class
		})
public class TicketingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketingApiApplication.class, args);
	}

}

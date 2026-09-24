package com.TP2.PJB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PjbApplication {
	public static void main(String[] args) {
		SpringApplication.run(PjbApplication.class, args);
	}

}

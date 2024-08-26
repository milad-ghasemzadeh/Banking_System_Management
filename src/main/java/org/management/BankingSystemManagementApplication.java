package org.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankingSystemManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemManagementApplication.class, args);
	}

}

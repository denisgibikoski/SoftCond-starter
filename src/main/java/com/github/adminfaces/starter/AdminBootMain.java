package com.github.adminfaces.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
@EnableAutoConfiguration(exclude = MailSenderValidatorAutoConfiguration.class)
public class AdminBootMain {

	public static void main(String[] args) {
		SpringApplication.run(AdminBootMain.class, args);
	}

}

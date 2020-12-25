package com.carrepairshop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// https://github.com/thombergs/buckpal
// https://www.javacodegeeks.com/2019/10/deploy-spring-boot-application-aws-ec2-instance.html
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CarRepairShopApi {

	public static void main(String[] args) {
		SpringApplication.run(CarRepairShopApi.class, args);
	}
}

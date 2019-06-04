package com.ada.learning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DemoApplication {

	public static void main(String[] args) {
		log.warn("DemoApplication-loader-{}", DemoApplication.class.getClassLoader());
		SpringApplication.run(DemoApplication.class, args);
	}

}

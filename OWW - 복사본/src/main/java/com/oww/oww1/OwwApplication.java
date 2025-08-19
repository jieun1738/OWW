package com.oww.oww1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.oww.oww1.mapper")
public class OwwApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwwApplication.class, args);
	}

}

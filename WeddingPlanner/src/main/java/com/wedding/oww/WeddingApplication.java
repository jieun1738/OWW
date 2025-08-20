package com.wedding.oww;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wedding.oww.mapper") // MyBatis 매퍼 스캔
public class WeddingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeddingApplication.class, args);
	}

}

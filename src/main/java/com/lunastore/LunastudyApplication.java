package com.lunastore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lunastore.mapper")
public class LunastudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LunastudyApplication.class, args);
	}
}
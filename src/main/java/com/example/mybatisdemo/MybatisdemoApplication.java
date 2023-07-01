package com.example.mybatisdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mybatisdemo.mapper")
public class MybatisdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisdemoApplication.class, args);
	}

}

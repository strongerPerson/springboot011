package com.wt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("com.wt")
@SpringBootConfiguration
@MapperScan("com.wt.mapper")
public class Demo0101Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo0101Application.class, args);
	}

}

package com.cspi.notionboard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cspi.notionboard.module.post.dao")
public class NotionboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotionboardApplication.class, args);
	}

}

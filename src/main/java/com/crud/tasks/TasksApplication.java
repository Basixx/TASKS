package com.crud.tasks;

import com.crud.tasks.domain.TaskDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
//public class TasksApplication extends SpringBootServletInitializer {

public class TasksApplication{
	public static void main(String[] args) {
		SpringApplication.run(TasksApplication.class, args);


		/*List<String> userNames = new ArrayList<>();
		userNames.add("jan");
		userNames.add("jan2");
		userNames.add("jan3");
		userNames.add("jan4");

		userNames.stream()
				.filter(name -> name.length() ==3)
				.findAny()
				.ifPresent(name -> System.out.println(name));*/


	}

//	@Override
//	protected SpringApplicationBuilder configure (SpringApplicationBuilder application){
//		return application.sources(TasksApplication.class);
//	}
}

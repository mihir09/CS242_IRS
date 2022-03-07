package com.example.dem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.dem.model.Demo;
import com.example.dem.repository.Repo;



@SpringBootApplication
public class DemoApp {
	
	@Autowired
	private Repo repository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApp.class, args);
	}
	
//	public void run(String... args) throws Exception {
//	    for (Demo demo : repository.findAll()) {
//	      System.out.println(demo);
//	    }
//	    
//	    System.out.println(repository.findByWord("a"));
//
//	  }
	
}

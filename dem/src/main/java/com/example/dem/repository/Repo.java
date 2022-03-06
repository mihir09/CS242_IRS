package com.example.dem.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.dem.model.Demo;

@Repository
@Transactional

public interface Repo extends MongoRepository<Demo, String> {
	public Demo findByWord(String word);
	
//	public List<Demo> findByLastName(String lastName);

}

package com.example.dem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.dem.model.Demo;
import com.example.dem.repository.Repo;

import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.*;
import org.bson.Document;
import org.bson.types.ObjectId;

@Controller


public class Controllers {
	
	@Autowired
	private Repo repository;
	
//	@PostMapping("/add")
//	public String save(@RequestBody Demo demo) {
//		repository.save(demo);
//		return " Added";
//	}
//	
//	@GetMapping("/findDatas")
//	public List<Demo> getDatas(){
//		return repository.findAll();
//	}
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String getData(@RequestParam String text,@RequestParam String index){
//		MongoClient client = MongoClients.create("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
//	    MongoDatabase database = client.getDatabase("wordindex");
//	    MongoCollection<Document> file = database.getCollection("Demo");
		System.out.println(repository.findByWord(text).Indexing);
//		Document name = file.find(new Document("word",text)).first();
//		System.out.println(name);
		return "redirect:result";
		
	}
	
	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public String showData(){
		
		return "result.html";
	}
//	@RequestMapping(value = "/findData/{word}", method = RequestMethod.GET)
//	public Demo getData(@PathVariable String word){
//		System.out.println(word);
//		return repository.findByWord(word);
//	}

}

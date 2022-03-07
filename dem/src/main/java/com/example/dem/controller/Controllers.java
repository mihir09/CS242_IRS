package com.example.dem.controller;

import com.nimbusds.jose.Algorithm;
import org.apache.lucene.queryparser.classic.ParseException;
import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


//import com.example.dem.Tfidf.Tfidf;

import static com.example.dem.Lucene.Lucene.lucene;
import static com.example.dem.Tfidf.Tfidf.maintfidf;

@RestController


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
			MongoClient client = MongoClients.create("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
			MongoDatabase database = client.getDatabase("wordindex");
			MongoCollection<Document> file = database.getCollection("hadoop");
			MongoCollection<Document> data = database.getCollection("data");
			//System.out.println(repository.findByWord(text).Indexing);
//		Document name = file.find(new Document("word",text)).first();
//		System.out.println(name);
//			System.out.println(text);
			System.out.println(index);
//			if (index == "hadoop") {
//				JSONArray array = maintfidf(text, file, data);
//				System.out.println(array);
//				return array.toString();
//			} else if (index == "lucene") {
//				JSONArray array = lucene(text);
//				System.out.println(array);
//				return array.toString();
//			}
			JSONArray array = ((index == "hadoop") ? maintfidf(text, file, data) : lucene(text));


		return array.toString();
	}
//
//	@RequestMapping(value = "/result", method = RequestMethod.GET)
//	public String showData(){
//
//		return "result.html";
//	}
//	@RequestMapping(value = "/findData/{word}", method = RequestMethod.GET)
//	public Demo getData(@PathVariable String word){
//		System.out.println(word);
//		return repository.findByWord(word);
//	}

}

package com.example.dem.Lucene;
/**
 * Hello world!
 *
 */
import java.io.File;
import java.io.IOException;

import com.mongodb.client.MongoCollection;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.FSDirectory;

import java.io.FileReader;
import java.sql.Array;
import java.util.Iterator;
import java.util.*;
import org.apache.lucene.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;

public class Lucene {

    public static JSONArray lucene(String q){

        JSONArray array = new JSONArray();
        try {
            //String file = args[0];
            String queryinput = q;

//        List<String> title_pgs = new ArrayList<String>();
//        List<String> text_pgs = new ArrayList<String>();
//        List<String> url_pgs = new ArrayList<String>();

            Analyzer analyzer = new StandardAnalyzer();

            // Store the index in memory:
            //Directory directory = new RAMDirectory();
            // To store an index on disk, use this instead:
            Directory directory = FSDirectory.open(Paths.get("D:/tmp/test"));
        /*
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        Object obj = null;
        try {
            // parsing file "sample.json"
            obj = new JSONParser().parse(new FileReader(file));
        }
        catch(Exception exception) {
        }
        if (obj == null) return;
        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        //getting data
        JSONArray ja = (JSONArray) jo.get("data");

        // iterating data
        Iterator itr = ja.iterator();

        while (itr.hasNext())
        {
            JSONObject jobject = (JSONObject)itr.next();
            String title_page = jobject.get("Title").toString();
            String text_page = jobject.get("Text").toString();
            String url = jobject.get("Url").toString();
            title_pgs.add(title_page);
            text_pgs.add(text_page);
            url_pgs.add(url);
        }

        Object[] title_pages = title_pgs.toArray();
        Object[] text_pages = text_pgs.toArray();
        Object[] url_pages = url_pgs.toArray();

        long start = System.currentTimeMillis();
        for (int i=0;i<title_pages.length;i++) {
            Document doc = new Document();
            doc.add(new TextField("Title", title_pages[i].toString(), Field.Store.YES));
            doc.add(new TextField("Text", text_pages[i].toString(), Field.Store.YES));
            doc.add(new StoredField("Url", url_pages[i].toString()));
            indexWriter.addDocument(doc);
            //System.out.println(doc.get("Url"));
        }
        //System.out.println(doc.get("Url"));
        long end = System.currentTimeMillis();
        System.out.println("Index creation time :"+ (end-start));

        indexWriter.close();*/
            // Now search the index:
            DirectoryReader indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            //QueryParser parser = new QueryParser("Text", analyzer);
            MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"Title", "Text"}, analyzer);

            Query query = parser.parse(queryinput);

            //System.out.println(query.toString());
            int topHitCount = 10;
            ScoreDoc[] hits = indexSearcher.search(query, topHitCount).scoreDocs;

            // Iterate through the results:

            for (int rank = 0; rank < hits.length; ++rank) {
                Document hitDoc = indexSearcher.doc(hits[rank].doc);
                //System.out.println(hitDoc);
                //Document each_doc = data.find(new Document("Url",hitDoc.get("Url"))).first();
                JSONObject jojo = new JSONObject();
                jojo.put("Url", hitDoc.get("Url"));
                jojo.put("Title", hitDoc.get("Title"));
                jojo.put("Text", hitDoc.get("Text"));
                array.add(jojo);
                //System.out.println((rank + 1) + " (score:" + hits[rank].score + ")--> " + hitDoc.get("Title") + " " + hitDoc.get("Url"));
                //System.out.println(indexSearcher.explain(query, hits[rank].doc));
            }
            indexReader.close();
            directory.close();

        }
        catch (Exception e){

        }
        return array;
    }
}


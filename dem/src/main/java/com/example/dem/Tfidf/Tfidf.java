package com.example.dem.Tfidf;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.apache.hadoop.io.Text;
import org.bson.Document;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import static java.lang.Integer.valueOf;

import java.lang.Math;


public class Tfidf {

    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list =
                new LinkedList<Map.Entry<String, Double> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, Collections.reverseOrder(new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        }));

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static Map<String, Double> wordranking(Document yoyo){
        JSONObject jo = new JSONObject(yoyo);
        String in = jo.get("Indexing").toString();
        String[] docl = in.trim().split(" ");
        //System.out.println(splitStr[0]);
        //String[] targetdoc = docl[0].split("~");
        //System.out.println(a[1]);
//            for (String z: docl
//                 ) {
//                System.out.println(z);
//            }
        double TotalDoc =3480;
        double doc_word=docl.length;

        //System.out.println(doc_word);
        double idf = Math.log(TotalDoc/(doc_word));
        //System.out.println(idf);

        //ArrayList<Double> scores = new ArrayList<Double>();
        HashMap<String,Double> scores = new HashMap<String,Double>();
        for (String s: docl
        ) {
            String[] targetdoc = s.split("~");
//                System.out.println(targetdoc[0]);
//                System.out.println("-----");
//                System.out.println(targetdoc[1]);
//                System.out.println(targetdoc[2]);
            int wordfreq_doc = valueOf(targetdoc[1]);
            double word_docl = valueOf(targetdoc[2]);
            double tf = wordfreq_doc / word_docl;

            //System.out.println(tf*idf);
            scores.put(targetdoc[0],(tf*idf));
        }
//            for (String name: scores.keySet()) {
//                String key = name;
//                String value = scores.get(name).toString();
//                System.out.println(key + " " + value);
//            }

        //ArrayList<Double> sortedScores = new ArrayList<>(scores.values());
        //Collections.sort(sortedScores, Collections.reverseOrder());

        HashMap<String, Double> sort_score = sortByValue(scores);
        return sort_score;
    }

    public static ArrayList<Document> dbquery(String query,MongoCollection<Document> file){
        ArrayList<Document> docs = new ArrayList<Document>();
        String[] query_word = query.trim().split(" ");
        for (String s: query_word
        ) {
            Document yoyo = file.find(new Document("word",s)).first();
            docs.add(yoyo);
        }
        return docs;
    }



    public static JSONObject jsonout(String finalurl, MongoCollection<Document> data){
        Document each_doc = data.find(new Document("Url",finalurl)).first();
        JSONObject jojo = new JSONObject(each_doc);

        jojo.put("Url", each_doc.getString("Url"));
        jojo.put("Title", each_doc.getString("Title"));
        jojo.put("Text", each_doc.getString("Text"));;
        jojo.remove("_id");
        return jojo;
    }


    public static JSONArray maintfidf(String query, MongoCollection<Document> file,MongoCollection<Document> data) {
        Text word = new Text();

        JSONArray array = new JSONArray();
        try {
            //MongoClient client = MongoClients.create("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
            //MongoDatabase database = client.getDatabase("wordindex");
//            MongoCollection<Document> file = database.getCollection("hadoop");
//            MongoCollection<Document> data = database.getCollection("data");
            //String query="Football";

            StringTokenizer itr = new StringTokenizer(query, " '-");

            //Iterating through all the words available in that line and forming the key/value pair.
            String abc="";
            while (itr.hasMoreTokens()) {
                // Remove special characters
                word.set(itr.nextToken().replaceAll("[^a-zA-Z]", "").toLowerCase());
                abc+=word+" ";
            }
            //System.out.println(abc);
//            Document yoyo = file.find(new Document("word","a")).first();
//            Document yoyo2 = file.find(new Document("word","a")).first();
            ArrayList<Document> doc=dbquery(abc.toLowerCase(),file);

            HashMap<String, Double> final_out = new HashMap<String,Double>();
            for (Document d: doc
            ) {
                //System.out.println(d);
                int i=0;
                //Map<String, Double> en = wordranking(d);
                //final_out.put(String.valueOf(en.keySet()),en.getValue());

                for (Map.Entry<String, Double> en : wordranking(d).entrySet()) {
                    //final_out.put(String.valueOf(en.getKey()),en.getValue());
//                    System.out.println("Key = " + en.getKey() +
//                            ", Value = " + en.getValue());
                    i+=1;
//                    if (i==10){
//                        break;
//                    }
                    //System.out.println(en.getKey());

                    if (final_out.containsKey(en.getKey())) {
                        double x=0.0;
                        for (Map.Entry<String, Double> p : final_out.entrySet()){
                            x = p.getValue() + en.getValue();
                            final_out.put(p.getKey(),x);
                        }
                        //System.out.println(final_out.get(final_out.values()));

                        //System.out.println("yes");
                    } else {
                        final_out.put(String.valueOf(en.getKey()),en.getValue());
                        //System.out.println("no");
                        //System.out.println(final_out.values());
                    }
                }
//                System.out.println(i);
//                System.out.println(final_out.size());
//                j+=1;
//                if (j==2){
//                    break;
//                }




                //System.out.println(final_out.values());
                //System.out.println(en.keySet());
//                for (Map.Entry<String, Double> en : wordranking(d).entrySet()) {
//                    System.out.println("Key = " + en.getKey() +
//                            ", Value = " + en.getValue());
//                    i+=1;
//                    if (i==10){
//                        break;
//                    }
//                }
            }
            int top=0;

            ArrayList<String>  final_url= new ArrayList<String>();
            for (Map.Entry<String, Double> p : sortByValue(final_out).entrySet()){
//                System.out.println("Key = " + p.getKey() +
//                        ", Value = " + p.getValue());
                final_url.add(p.getKey());
                top+=1;
                if (top==10){
                    break;
                }
            }


            //System.out.println(final_url);
            ArrayList<Document> docs = new ArrayList<Document>();
            for (String z: final_url
            ) {
                array.add(jsonout(z,data));
            }
            //System.out.println(docs);
            //System.out.println(docs);

            //JSONObject jsonObject = new JSONObject();
            //jsonObject.put("Players_data", array);

            //System.out.println(array);
            //System.out.println(array.size());



/*
            System.out.println("System connected");
            //System.out.println(yoyo.toJson());

            int i=0;
            for (Map.Entry<String, Double> en : wordranking().entrySet()) {
                System.out.println("Key = " + en.getKey() +
                        ", Value = " + en.getValue());
                i+=1;
                if (i==10){
                    break;
                }

            }*/
            //0.035633825440507305


            //Multi word query





        }
        catch (Exception e){
            e.printStackTrace();
        }

        return array;
    }
}



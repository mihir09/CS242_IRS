import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.TwoDArrayWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
//import org.apache.hadoop.mapreduce.MapContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.file.Files;
import java.nio.file.Paths;
//import net.minidev.json.JSONArray;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//import org.json.JSONArray;
//import org.json.JSONObject;

public class InvertedIndex {

    /*
    This is the Mapper class. It extends the hadoop's Mapper class.
    This maps input key/value pairs to a set of intermediate(output) key/value pairs.
    Here our input key is a Object and input value is a Text.
    And the output key is a Text and value is an Text. [word<Text> DocID<Text>]<->[aspect 5722018411]
    */
    static List<String> stopwords;



    public static class TokenizerMapper
            extends Mapper<Object, Text, Text, Text>{

        /*
        Hadoop supported datatypes. This is a hadoop specific datatype that is used to handle
        numbers and Strings in a hadoop environment. IntWritable and Text are used instead of
        Java's Integer and String datatypes.
        Here 'one' is the number of occurance of the 'word' and is set to value 1 during the
        Map process.
        */
        //private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {

            HashMap<String,Integer> map1 = new HashMap<String,Integer>();


//            File myObj = new File("\"C:\\Users\\Vishv\\Downloads\\stopwords.txt\"");
//            Scanner myReader = new Scanner(myObj);
//            while (myReader.hasNextLine()) {
//                String data = myReader.nextLine();
//                System.out.println(data);
//            }
            stopwords = Files.readAllLines(Paths.get("src/main/resources/stopwords.txt"));
            //System.out.println(stopwords);

            try {
                //JSONParser parser =new JSONParser();
                //JSONObject json = (JSONObject) parser.parse(value.toString());
                //String str = value.toString();
                //str = str.replaceAll("[^a-zA-Z0-9:;,.?!{}]"," ");
                //str = str.replaceAll("\n"," ");
//        System.out.println(str);
                JSONArray ja = (JSONArray) new JSONParser().parse(value.toString());
                //JSONArray ja = new JSONArray(value.toString());
                //int Docid=0;
                //System.out.println(ja.size());
                for (int i=0; i<ja.size();i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    String url = jo.get("Url").toString();
                    String title = jo.get("Title").toString();
                    String text = jo.get("Text").toString();


                    //System.out.println(text);
                    ArrayList<String> allWords = Stream.of((title + " " +text).toLowerCase()
                                    .split(" "))
                            .collect(Collectors.toCollection(ArrayList<String>::new));
                    allWords.removeAll(stopwords);

                    String result = allWords.stream().collect(Collectors.joining(" "));
                    //System.out.println(result);


                    StringTokenizer itr
                            = new StringTokenizer(
                            result);

                    // Counting the tokens
                    int count = itr.countTokens();
                    //System.out.println(count);

                    //StringTokenizer itr = new StringTokenizer(text, " '-");

                    //Iterating through all the words available in that line and forming the key/value pair.
                    while (itr.hasMoreTokens()) {
                        // Remove special characters
                        word.set(itr.nextToken().replaceAll("[^a-zA-Z]", "").toLowerCase());



                        if (word.toString() != "" && !word.toString().isEmpty()) {
//              List<String> myList = new ArrayList<String>();
//              String stopwordsRegex = stopwords.stream()
//                      .collect(Collectors.joining("|", "\\b(", ")\\b\\s?"));
//
//              String result = original.toLowerCase().replaceAll(stopwordsRegex, "");
//              assertEquals(result, target)
        /*
        Sending to output collector(Context) which in-turn passed the output to Reducer.
        The output is as follows:
          'word1' 5722018411
          'word1' 6722018415
          'word2' 6722018415
        */    StringBuilder docValue = new StringBuilder();
                            docValue.append(url+" "+count);
                            context.write(word, new Text(docValue.toString()));
                        }

                    }

                    //Docid+=1;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            //Iterator itr = ja.iterator();
            //Iterator itr = ja.iterator();
            //System.out.println(json);
//        while (itr.hasNext())
//        {
//          JSONObject jo = (JSONObject)itr.next();
//          String url = jo.get("Url").toString();
//          String title = jo.get("Title").toString();
//          String text = jo.get("Text").toString();
//
//          System.out.println(url);
//          System.out.println(title);
//          System.out.println(text);
////        title_pgs.add(title_page);
////        text_pgs.add(text_page);
//
//          word.set(text.replaceAll("[^a-zA-Z]", "").toLowerCase());
//          if(word.toString() != "" && !word.toString().isEmpty()){
//          /*
//          Sending to output collector(Context) which in-turn passed the output to Reducer.
//          The output is as follows:
//            'word1' 5722018411
//            'word1' 6722018415
//            'word2' 6722018415
//          */
//            context.write(word, new Text(url));
//          }
//        }






            // Split DocID and the actual text
//      String DocId = value.toString().substring(0, value.toString().indexOf("\t"));
//      String value_raw =  value.toString().substring(value.toString().indexOf("\t") + 1);

            // Reading input one line at a time and tokenizing by using space, "'", and "-" characters as tokenizers.
//      StringTokenizer itr = new StringTokenizer(value_raw, " '-");

            // Iterating through all the words available in that line and forming the key/value pair.
//      while (itr.hasMoreTokens()) {
//        // Remove special characters
//        word.set(itr.nextToken().replaceAll("[^a-zA-Z]", "").toLowerCase());
//        if(word.toString() != "" && !word.toString().isEmpty()){
//        /*
//        Sending to output collector(Context) which in-turn passed the output to Reducer.
//        The output is as follows:
//          'word1' 5722018411
//          'word1' 6722018415
//          'word2' 6722018415
//        */
//          context.write(word, new Text(DocId));
//        }
//      }
        }
    }

    public static class IntSumReducer
            extends Reducer<Text,Text,Text,Text> {
        /*
        Reduce method collects the output of the Mapper calculate and aggregate the word's count.
        */
        public void reduce(Text key, Iterable<Text> values,
                           Context context
        ) throws IOException, InterruptedException {

            HashMap<String,Integer> map = new HashMap<String,Integer>();
      /*
      Iterable through all the values available with a key [word] and add them together and give the
      final result as the key and sum of its values along with the DocID.
      */

            for (Text val : values) {
                //String[] strArr = val.toString().split(" ");
                if (map.containsKey(val.toString())) {
                    map.put(val.toString(), map.get(val.toString()) + 1);
                } else {
                    map.put(val.toString(), 1);
                }
            }
            StringBuilder docValueList = new StringBuilder();
            for(String docID : map.keySet()){
                String[] doc = docID.toString().split(" ");
                docValueList.append(doc[0]+ "~" + map.get(docID) +"~"+doc[1]+" ");
            }
            context.write(key, new Text(docValueList.toString()));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "inverted index");
        job.setJarByClass(InvertedIndex.class);
        job.setMapperClass(TokenizerMapper.class);
        // Commend out this part if you want to use combiner. Mapper and Reducer input and outputs type matching might be needed in this case.
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

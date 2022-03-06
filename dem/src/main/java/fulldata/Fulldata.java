//package fulldata;
//
//import org.bson.types.ObjectId;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
////import org.springframework.data.annotation.Id;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//@Getter
//@Setter
//@ToString
//@Document(collection = "Fulldata")
//
//public class Fulldata {
//	
////		@Id
////		public int id;
//		public String word;
//		public String Indexing;
//
//		public Fulldata() {}
//
//		public Fulldata(String word, String Indexing) {
//			this.word = word;
//			this.Indexing = Indexing;
//		}
//
//		@Override
//		public String toString() {
//			return String.format(
//				"%s",Indexing);
//		}
////		MongoClient client = MongoClients.create("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
////	    MongoDatabase database = client.getDatabase("wordindex");
////	    MongoCollection<Document> file = database.getCollection("Demo");
//	
//	
//
//}

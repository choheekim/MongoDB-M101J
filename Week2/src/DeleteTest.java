import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Created by chohee on 3/24/16.
 */
public class DeleteTest {
    public static void main(String[] args) {
/**
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("people");

        for(Document cur : collection.find().into(new ArrayList<Document>())) {
            PrintJson.printJson(cur);
        }
    }
 **/

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("deleteTest");

        collection.drop();

        for(int i = 0; i < 8; i++) {
            collection.insertOne(new Document().append("_id", i+1));
        }

        collection.deleteOne(Filters.eq("_id", 3));
        //collection.deleteMany(Filters.gt("_id", 5));

        for(Document cur : collection.find().into(new ArrayList<Document>())) {
            PrintJson.printJson(cur);
        }
    }
}


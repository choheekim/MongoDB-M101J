import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Created by chohee on 3/24/16.
 */
public class UpdateTest {
    public static void main(String[] args) {

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("UpdateTest");

        collection.drop();

        //insert 8 documents, with both _id and x set to the value of the loop variable
        //and y set to be true
        for(int i = 0; i < 8; i++) {
            collection.insertOne(new Document()
                                        .append("_id", i)
                                        .append("x", i)
                                        .append("y", true));
        }

        collection.updateOne(Filters.eq("_id", 9), Updates.combine(Updates.set("x", 20)
                                                                ,Updates.set("updated", true))
                            ,new UpdateOptions().upsert(true));

        collection.updateMany(Filters.gte("x", 5), Updates.inc("x", 100));

        for(Document cur : collection.find().into(new ArrayList<Document>())){
            PrintJson.printJson(cur);
        }
    }
}

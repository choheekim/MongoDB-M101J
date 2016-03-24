import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;


/**
 * Created by chohee on 3/23/16.
 */
public class FindWithFilterTest {

    public static void main(String[] args) {

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("findWithFilterTest");

        collection.drop();

        for(int i = 0; i < 10; i++) {
            collection.insertOne(new Document()
                        .append("x", new Random().nextInt(2))
                        .append("y", new Random().nextInt(100)));
        }
/*
        Bson filter = new Document("x", 0)
                        .append("y", new Document("$gt", 10)
                                           .append("$lt", 90));
*/

        Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));
      //  Bson projection = new Document("y", 1).append("_id", 0);

        Bson projection = fields(
                include("y", "x"),
                exclude("_id"));

        List<Document> all = collection.find(filter)
                .projection(projection)
                .into(new ArrayList<Document>());
        for(Document cur : all) {
            PrintJson.printJson(cur);
        }

        long count = collection.count();

    }
}

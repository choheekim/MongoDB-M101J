import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chohee on 3/24/16.
 */
public class FindWithSortSkipLimitTest {

    public static void main(String[] args) {

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("FindWithSortTest");

        collection.drop();

        // insert 100 documents with two random integers
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 10; j++) {
                collection.insertOne(new Document().append("i", i).append("j", j));
            }
        }

        Bson projection = Projections.fields(Projections.include("i", "j"), Projections.excludeId());
       //sort i
        //Bson sort = new Document("i", 1).append("j", 1);

        Bson sort = Sorts.orderBy(Sorts.ascending("i"), Sorts.descending("j")) ;


        List<Document> all = collection.find()
                .projection(projection)
                .sort(sort)
                .limit(50)
                .skip(8)
                .into(new ArrayList<Document>());

        for(Document cur : all) {
            PrintJson.printJson(cur);
        }
    }
}

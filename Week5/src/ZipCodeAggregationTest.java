import com.mongodb.AggregationOptions;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by chohee on 4/24/16.
 */
public class ZipCodeAggregationTest {
    public static void main(String[] args) {

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("study");
        MongoCollection<Document> collection = db.getCollection("zips");

        //pipeline
        //using Document
        /*
        List<Document> pipeline = asList(new Document("$group",
                                    new Document("_id", "$state")
                                    .append("totalPop", new Document("$sum", "$pop"))),
                new Document("$match", new Document("totalPop", new Document("$gte", 10000000))) );
        */

        //using parse
        List<Document> pipeline = asList(Document.parse("{$group : { _id : \"$state\", totalPop: { $sum : \"$pop\"}}}")
                                        ,Document.parse("{ $match : { totalPop : {$gte : 10000000}}}"));

        //using Builder to aggregate
        /*
        List<Bson> pipeline = asList(Aggregates.group("$state", Accumulators.sum("totalPop", "$pop")),
                                        Aggregates.match(Filters.gte("totalPop", 10000000)));

        */
        List<Document> all = collection.aggregate(pipeline)
                            .into(new ArrayList<Document>());

        for(Document cur : all) {
            System.out.println(cur.toJson());
        }

    }
}


import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;

/**
 * Created by chohee on 4/1/16.
 *
 * import json file
 * =====> mongoimport -d school -c students < students.json
 * Write a program in the language of your choice that will remove the lowest homework score for each student.
 * Since there is a single document for each student containing an array of scores,
 * you will need to update the scores array and remove the homework.
 */
public class HW3_1 {

    public static void main(String[] args) {

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("school");
        MongoCollection<Document> collection = db.getCollection("students");

        //sorting
        Bson sort = Sorts.orderBy(Sorts.ascending("_id"));

        List<Document> all = collection.find()
                            .sort(sort)
                            .into(new ArrayList<Document>());



        for(Document cur: all) {
            List<Document> score = (ArrayList<Document>) cur.get("scores");

            double lowScore = Double.MIN_VALUE;
            Document hw1 = score.get(2);
            double hw1Score = hw1.getDouble("score");

            Document hw2 = score.get(3);
            double hw2Score = hw2.getDouble("score");

            if(hw1Score < hw2Score) {
                score.remove(2);
            }else {
                score.remove(3);
            }

            UpdateResult result = collection.updateOne(new Document("_id", cur.get("_id")), new Document("$set", new Document("scores", score)));
            System.out.println(result.toString());
        }

    }
}

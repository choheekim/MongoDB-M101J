import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chohee on 3/24/16.
 */
public class HW2_3 {

    public static void main(String[] args) {

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("students");
        MongoCollection<Document> collection = db.getCollection("grades");

        Bson sort = Sorts.orderBy(Sorts.ascending("student_id"), Sorts.ascending("score"));


        List<Document> all = collection.find(new Document("type", "homework"))
                            .sort(sort)
                            .into(new ArrayList<Document>());

        Integer currentStudentID = -1;

        for(Document cur : all) {
            Integer studentID = (Integer)cur.get("student_id");

            if(!studentID.equals(currentStudentID) && cur.get("type").equals("homework")) {
                collection.deleteOne(cur);
                currentStudentID = studentID;
            }
        }



    }
}



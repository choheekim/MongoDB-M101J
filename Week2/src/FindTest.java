import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chohee on 3/23/16.
 */
public class FindTest {

    public static void main(String[] args) {

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("findTest");

        collection.drop();

        List<String> studentName = new ArrayList<String>
                                    (Arrays.asList("Chohee", "Andrew", "Jessica", "Stephanie",
                                            "Marin", "John", "Mike", "Dwight", "Jake", "Sarah"));
        String[] name = {"exam", "quiz", "essay"};

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 3; j++) {
            collection.insertOne(new Document( "Student Name", studentName.get(i))
                                    .append("type" , name[j])
                                    .append("score" , Math.round(Math.random()*100)));
            }
        }

        /*
        System.out.println("Find one : ");
        Document first = collection.find().first();
        printJson(first);

        System.out.println("Find all with into : ");
        List<Document> all = collection.find().into(new ArrayList<Document>());
        for( Document cur : all) {
            printJson(cur);
        }


        System.out.println("Find all with iterations : ");
        MongoCursor<Document> cursor = collection.find().iterator();

        try {
            while(cursor.hasNext()) {
                Document cur = cursor.next();
                printJson(cur);
            }
        }finally {
            cursor.close();
        }

        */

        System.out.println("Count : ");
        long count = collection.count();
        System.out.println(count);




    }

    public static void printJson(Document document) {

        JsonWriter jsonWriter = new JsonWriter(new StringWriter(),
                new JsonWriterSettings(JsonMode.SHELL,true));
        new DocumentCodec().encode(jsonWriter, document,
                EncoderContext.builder()
                        .isEncodingCollectibleDocument(true)
                        .build());

        System.out.println(jsonWriter.getWriter());
        System.out.flush();
    }


}

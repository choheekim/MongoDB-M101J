import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import java.io.StringWriter;
import java.util.Arrays;

/**
 * Created by chohee on 3/23/16.
 */
public class InsertTest {

    public static void main(String[] args) {

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("insertTest");

        coll.drop();

        Document smith = new Document("name" , "Smith")
                .append("age" , 30)
                .append("profession" , "programmer")
                .append("interests" , Arrays.asList("Skiing", "Reading"));

        Document jones = new Document("name" , "Jones")
                .append("age" , 25)
                .append("profession" , "hacker");

        printJson(smith);
        printJson(jones);

        coll.insertMany(Arrays.asList(smith,jones));

        printJson(smith);
        printJson(jones);

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

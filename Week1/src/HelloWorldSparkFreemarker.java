import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;

import static spark.Spark.halt;

/**
 * Created by chohee on 3/22/16.
 */
public class HelloWorldSparkFreemarker {

    public static void main(String[] args) {

        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldSparkFreemarker.class, "/");


        MongoClient client = new MongoClient();

        MongoDatabase db = client.getDatabase("course");
        final MongoCollection<Document> col = db.getCollection("hello");

        col.drop();

        col.insertOne(new Document("name", "MongoDB"));

        Spark.get(("/"), new Route() {
            @Override
            public Object handle(final Request request, final Response response) throws Exception {

                StringWriter writer = new StringWriter();

                try {
                    Template helloTemplate = configuration.getTemplate("hello.ftl");

                    Document document = col.find().first();

                    helloTemplate.process(document, writer);

                } catch (Exception e) {
                    Spark.halt(500);
                    e.printStackTrace();
                }

                return writer;
            }
        });

    }
}

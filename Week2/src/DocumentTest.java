import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by chohee on 3/23/16.
 */
public class DocumentTest {

    public static void main(String[] args) {

        Document document = new Document()
        .append("str", "MongoDB, Hello!")
                .append("int", 42)
                .append("l", 1L)
                .append("double", 1.1)
                .append("b", false)
                .append("date" , new Date())
                .append("objectID", new ObjectId())
                .append("null", null)
                .append("embeddedDoc", new Document("x", 0))
                .append("list", Arrays.asList(1,2,3));


        printJson(document);
        String str = (String)document.get("str");
        int i = document.getInteger("int");
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


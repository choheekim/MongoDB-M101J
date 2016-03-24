import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.sun.xml.internal.ws.api.message.Packet.Status.Request;
import static com.sun.xml.internal.ws.api.message.Packet.Status.Response;
import static spark.Spark.halt;

/**
 * Created by chohee on 3/22/16.
 */
public class SparkFormHandling {

    public static void main(String[] args) {

        final Configuration config = new Configuration();
        config.setClassForTemplateLoading(SparkFormHandling.class , "/");

        Spark.get("/" , new Route() {
            @Override
            public Object handle(final Request request, final Response response) throws Exception {
                try {

                    Map<String, Object> fruitMap = new HashMap<String, Object>();
                    fruitMap.put("fruits", Arrays.asList("apple", "orange", "banana", "peach"));

                    Template fruitPickerTemplate = config.getTemplate("fruitPicker.ftl");
                    StringWriter writer = new StringWriter();
                    fruitPickerTemplate.process(fruitMap, writer);
                    return writer;

                }catch(Exception io) {
                    Spark.halt(500);
                    return null;
                }
            }
        });

        Spark.post("/favorite_fruit", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                final String fruit = request.queryParams("fruit");
                if(fruit == null) {
                    return "Why don't you pick one?";
                }else {
                    return "Your favorite fruit is " + fruit;
                }
            }
        });
    }
}

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by chohee on 3/22/16.
 */
public class HelloWorldSparkStyle {
    public static void main(String[] args) {
        Spark.get(("/"), new Route() {
            @Override
            public Object handle(final Request request,final Response response) throws Exception {
                return "This is from Spark!";
            }
            });

    }
}

package dynamic.broadcast.mongo;

import com.mongodb.MongoClientURI;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-25
 */
public class MongoConfig {

    private static String ip = "mini1";
    private static String port = "27017";

    public static MongoClientURI getMongouri() {
        String uri = "mongodb://";
        uri = uri + ip + ":" + port;
        return new MongoClientURI(uri);
    }
}

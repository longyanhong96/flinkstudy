package dynamic.broadcast.mongo.impl;

import com.mongodb.MongoClient;
import dynamic.broadcast.mongo.MongoConfig;
import dynamic.broadcast.mongo.MongoDBSource;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-05
 */
abstract public class MongoDBSourceImpl<OUT> extends RichSourceFunction<OUT> implements MongoDBSource {

    protected Boolean isRunning = true;
    protected MongoClient mongoClient;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        mongoClient = new MongoClient(MongoConfig.getMongouri());
    }

    @Override
    public void close() throws Exception {
        if (mongoClient != null){
            mongoClient.close();
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}

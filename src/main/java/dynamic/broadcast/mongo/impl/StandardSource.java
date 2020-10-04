package dynamic.broadcast.mongo.impl;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * Description : 在mongodb中读取清晰标准化的配置文件
 *
 * @author lyh
 * @date 2020-08-05
 */
public class StandardSource extends MongoDBSourceImpl<String> {
    @Override
    public void run(SourceFunction.SourceContext<String> ctx) throws Exception {

    }

}

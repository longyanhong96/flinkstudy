package dynamic.broadcast.mongo;

import dynamic.broadcast.mongo.impl.StandardSource;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-24
 */
public class MongoBroadcastStreamDemo {

    private static MapStateDescriptor<String,String> STANDARD = new MapStateDescriptor<String, String>(
            "standardBroadcast",
            BasicTypeInfo.STRING_TYPE_INFO,
            BasicTypeInfo.STRING_TYPE_INFO
    );

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();



        BroadcastStream<String> broadcastStream = env.addSource(new StandardSource()).broadcast(STANDARD);
    }
}

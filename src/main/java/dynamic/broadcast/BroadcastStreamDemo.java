package dynamic.broadcast;

import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Description : 使用广播流实现配置的动态更新
 *
 * @author lyh
 * @date 2020-08-02
 */
public class BroadcastStreamDemo {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        MapStateDescriptor<String, String> CONFIG_KEYWORDS = new MapStateDescriptor<>(
                "config-keywords",
                BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.STRING_TYPE_INFO
        );

        BroadcastStream<String> broadcastStream = env.addSource(new MyBroadcastStream()).broadcast(CONFIG_KEYWORDS);

        DataStreamSource<String> dataStream = env.addSource(new MyDataStream());

        dataStream.connect(broadcastStream).process(new MyBroadcastProcessFunction())
                .print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

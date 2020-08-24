package sink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-19
 */
public class KafkaValueSink {

    private static String brokerList = "mini1:9092,mini2:9092,mini3:9092";
    private static String topic = "flinkouttest";

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> sourceStream = env.socketTextStream("mini1", 8888);
        SingleOutputStreamOperator<String> flatMapStream = sourceStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String[] split = s.split("\\s+");
                for (int i = 0; i < split.length; i++) {
                    collector.collect(split[i]);
                }
            }
        });

        flatMapStream.addSink(new FlinkKafkaProducer<String>(brokerList,topic,new SimpleStringSchema()));

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

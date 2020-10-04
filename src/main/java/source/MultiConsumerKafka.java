package source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import utils.PropertyRead;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Description : 订阅多个同一个kafka的集群的多个topic
 *
 * @author lyh
 * @date 2020-09-02
 */
public class MultiConsumerKafka {

    public static void main(String[] args) {
        List<String> topics = Arrays.asList("nlp", "nlp_rcs");
        Properties props = PropertyRead.getProperties("connect-kafka.properties");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> multiKafkaSource = env.addSource(new FlinkKafkaConsumer<>(topics, new MyMultiKafkaSource(), props));
        multiKafkaSource.print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
